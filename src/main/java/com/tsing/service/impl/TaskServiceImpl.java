package com.tsing.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tsing.entity.Task;
import com.tsing.entity.TaskLog;
import com.tsing.entity.bindata.*;
import com.tsing.entity.detect.Data;
import com.tsing.entity.detect.NonMotorVehicles;
import com.tsing.entity.feign.DeviceDto;
import com.tsing.entity.kafka.OriginImg;
import com.tsing.kafka.KafkaConsumer;
import com.tsing.mapper.TaskLogMapper;
import com.tsing.mapper.TaskMapper;
import com.tsing.service.DeviceCacheService;
import com.tsing.service.ITaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import com.tsing.client.DetectClient;
import com.tsing.entity.LibraryDetail;
import com.tsing.service.ILibraryDetailService;
import com.tsing.entity.detect.DetectResp;
import com.alibaba.fastjson2.JSON;
import org.springframework.util.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.format.DateTimeFormatter;

import java.util.List;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledFuture;

import com.alibaba.fastjson2.JSONObject;
import java.util.Queue;
import java.util.LinkedList;

/**
 * <p>
 * 推流任务表 服务实现类
 * </p>
 *
 * @author tsing
 * @since 2025-05-28
 */
@Service
@Slf4j
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements ITaskService {

	@Autowired
	private TaskLogMapper taskLogMapper;
	private ThreadPoolTaskScheduler scheduler;
	@Autowired
	KafkaConsumer kafkaConsumer;

	private final ConcurrentMap<Long, ScheduledTaskRef> scheduledTasks = new ConcurrentHashMap<>();
	private final ConcurrentMap<Long, Queue<Object>> taskQueues = new ConcurrentHashMap<>();
	private final ConcurrentMap<Long, Boolean> taskStopping = new ConcurrentHashMap<>();

	public static final String STATUS_RUNNING = "1";
	public static final String STATUS_STOPPED = "2";

	@Autowired
	private DetectClient detectClient;
	@Autowired
	private ILibraryDetailService libraryDetailService;
	@Autowired
	private DeviceCacheService deviceCacheService;

	@PostConstruct
	public void initScheduler() {
		scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize(10);
		scheduler.setThreadNamePrefix("task-scheduler-");
		scheduler.initialize();
	}


	@Override
	public synchronized boolean startTask(Long id, String cron) {
		// 1. 获取任务
		Task task = this.getById(id);
		if (task == null) {
			return false;
		}

		// 2. 校验 cron 表达式
		if (!isValidCron(cron)) {
			logTask(id, "error", "无效的 Cron 表达式：" + cron);
			return false;
		}

		// 3. 停止旧任务
		stopTask(id);

		// 4. 初始化队列
		Queue<Object> queue = new LinkedList<>();
		if (task.getPushType() == 0) {
			// 单组图片
			if (task.getContentRef() != null) {
				queue.offer(task.getContentRef());
			}
		} else if (task.getPushType() == 1) {
			// 库类型
			Long libraryId = null;
			try {
				libraryId = Long.valueOf(task.getContentRef());
			} catch (Exception ignore) {}
			if (libraryId != null) {
				List<LibraryDetail> details = libraryDetailService.list(new QueryWrapper<LibraryDetail>().eq("library_id", libraryId));
				for (LibraryDetail detail : details) {
					queue.offer(detail);
				}
			}
		}
		taskQueues.put(id, queue);

		// 5. 定义调度逻辑
		Runnable runnable = () -> {
			try {
				logTask(id, "execute", "任务执行");
				doTaskWithQueue(task);
			} catch (Exception e) {
				logTask(id, "error", "任务执行异常：" + e.getMessage());
				e.printStackTrace();
			}
		};

		// 6. 调度任务
		CronTrigger trigger = new CronTrigger(cron);
		ScheduledFuture<?> future = scheduler.schedule(runnable, trigger);
		scheduledTasks.put(id, new ScheduledTaskRef(future, cron));

		// 7. 更新状态
		task.setStatus(STATUS_RUNNING);
		this.updateById(task);

		logTask(id, "start", "任务启动，cron=" + cron);
		return true;
	}

	@Override
	public synchronized boolean stopTask(Long id) {
		ScheduledTaskRef ref = scheduledTasks.remove(id);
		// 标记为stopping
		taskStopping.put(id, true);
		if (ref != null && ref.future != null) {
			ref.future.cancel(false); // 不中断线程
			logTask(id, "stop", "任务停止");
		}
		taskQueues.remove(id);
		Task task = this.getById(id);
		if (task != null) {
			task.setStatus(STATUS_STOPPED);
			this.updateById(task);
		}
		// 延迟一会儿移除stopping标志，确保当前调度线程能检测到
		scheduler.schedule(() -> taskStopping.remove(id), new java.util.Date(System.currentTimeMillis() + 10000));
		return true;
	}

	// 简单 Cron 表达式校验器（也可以用更强的库）
	private boolean isValidCron(String cron) {
		try {
			new CronTrigger(cron); // Spring 自带的 CronTrigger 可抛异常
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public IPage<TaskLog> pageTaskLogs(Long taskId, String name, String action, String startTime, String endTime, int page, int size) {
		Page<TaskLog> p = new Page<>(page, size);
		QueryWrapper<TaskLog> wrapper = new QueryWrapper<>();
		if (taskId != null) {
			wrapper.eq("task_id", taskId);
		}
		if (name != null && !name.isEmpty()) {
			wrapper.inSql("task_id", "select id from task where task_name like '%" + name + "%'");
		}
		if (action != null && !action.isEmpty()) {
			wrapper.eq("action", action);
		}
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		if (StringUtils.hasText(startTime)) {
			wrapper.ge("create_time", LocalDateTime.parse(startTime, dtf));
		}
		if (StringUtils.hasText(endTime)) {
			wrapper.le("create_time", LocalDateTime.parse(endTime, dtf));
		}
		wrapper.orderByDesc("create_time");
		return taskLogMapper.selectPage(p, wrapper);
	}

	public void logTask(Long taskId, String action, String message) {
		// 只保留start、stop、pushKafka三种操作
		if (!"start".equals(action) && !"stop".equals(action) && !"pushKafka".equals(action)) {
			return;
		}
		TaskLog log = new TaskLog();
		log.setTaskId(taskId);
		Task task = this.getById(taskId);
		log.setTaskName(task != null ? task.getTaskName() : null);
		log.setAction(action);
		// 推送Kafka日志内容不截断
		log.setMessage(message);
		log.setCreateTime(LocalDateTime.now());
		taskLogMapper.insert(log);
	}

	/**
	 * 队列消费逻辑
	 */
	private void doTaskWithQueue(Task task) {
		if (Boolean.TRUE.equals(taskStopping.get(task.getId()))) {
			return;
		}
		Queue<Object> queue = taskQueues.get(task.getId());
		if (queue == null || queue.isEmpty()) {
			// 队列空，重新入队
			if (task.getPushType() == 0) {
				if (task.getContentRef() != null) {
					queue = new LinkedList<>();
					queue.offer(task.getContentRef());
					taskQueues.put(task.getId(), queue);
				}
			} else if (task.getPushType() == 1) {
				Long libraryId = null;
				try {
					libraryId = Long.valueOf(task.getContentRef());
				} catch (Exception ignore) {}
				if (libraryId != null) {
					List<LibraryDetail> details = libraryDetailService.list(new QueryWrapper<LibraryDetail>().eq("library_id", libraryId));
					queue = new LinkedList<>();
					for (LibraryDetail detail : details) {
						queue.offer(detail);
					}
					taskQueues.put(task.getId(), queue);
				}
			}
		}
		if (queue == null || queue.isEmpty()) return;
		Object item = queue.poll();
		if (item == null) return;
		if (task.getPushType() == 0) {
			// 单组图片
			JSONObject obj = JSON.parseObject((String)item);
			String faceImageUrl = obj.getString("faceImageUrl");
			String senceImageUrl = obj.getString("senceImageUrl");
			DetectResp resp = detectClient.detectImg(faceImageUrl, senceImageUrl, task.getDeviceId());
			if (resp != null && resp.getResult() != null) {
				Bindata bindata = wrapToBindata(resp.getResult(), task.getDeviceId(), faceImageUrl);
				handleBindata(bindata, task.getId());
			}
		} else if (task.getPushType() == 1) {
			// 库类型
			LibraryDetail detail = (LibraryDetail)item;
			String faceImageUrl = detail.getFaceImageUrl();
			String senceImageUrl = detail.getSenceImageUrl();
			DetectResp resp = detectClient.detectImg(faceImageUrl, senceImageUrl, task.getDeviceId());
			if (resp != null && resp.getResult() != null) {
				Bindata bindata = wrapToBindata(resp.getResult(), task.getDeviceId(), faceImageUrl);
				handleBindata(bindata, task.getId());
			}
		}
	}

	/**
	 * 将DetectResp.Result封装成Bindata对象
	 */
	private Bindata wrapToBindata(com.tsing.entity.detect.Result result, String deviceId, String faceImageUrl) {
		Bindata bindata = new Bindata();
		int objType = 0;
		// 1. Img
		if (result.getImage() != null && result.getImage().getData() != null) {
			com.tsing.entity.detect.Data data = result.getImage().getData();
			com.tsing.entity.bindata.Img img = new com.tsing.entity.bindata.Img();
			img.setWidth(data.getWidth());
			img.setHeight(data.getHeight());
			img.setURI(data.getURI());
			img.setBinData(data.getBinData());
			img.setSn((long) data.getSn());
			bindata.setImg(img);
		}
		// 2. Faces
		if (result.getFaces() != null) {
			List<Face> faces = result.getFaces();
			faces.forEach(f -> {
				Data data = result.getImage().getData();
				f.setOriginImg(JSONObject.from(data));
				JSONObject img = f.getImg();
				JSONObject img1 = img.getJSONObject("Img");
				img1.put("URI", faceImageUrl);
				img.put("Img", img1);
			});
			bindata.setFaces(faces);
			objType = 1024;
		}
		// 3. Vehicle
		if (result.getVehicles() != null) {
			List<RecVehicle> vehicles = result.getVehicles();
			vehicles.forEach(f -> {
				Data data = result.getImage().getData();
				f.setOriginImg(JSONObject.from(data));
				JSONObject img = f.getImg();
				JSONObject img1 = img.getJSONObject("Img");
				img1.put("URI", faceImageUrl);
				img.put("Img", img1);
			});
			bindata.setVehicle(vehicles);
			objType = 1;
		}
		// 4. Pedestrian
		if (result.getPedestrian() != null) {
			List<Pedestrian> pedestrian = result.getPedestrian();
			pedestrian.forEach(p -> {
				Data data = result.getImage().getData();
				p.setOriginImg(JSONObject.from(data));
				JSONObject img = p.getImg();
				JSONObject img1 = img.getJSONObject("Img");
				img1.put("URI", faceImageUrl);
				img.put("Img", img1);
			});
			bindata.setPedestrian(pedestrian);
			// face 不处理5556类型 跳过
			objType = 4;
		}
		// 5. NonMotorVehicles
		if (result.getNonMotorVehicles() != null) {
//			// 类型转换 List<NonMotorVehicles> -> List<NonMotorVehicle>
//			List<NonMotorVehicles> nonMotorVehicles = result.getNonMotorVehicles();
//			List<com.tsing.entity.bindata.NonMotorVehicle> nmvList = new java.util.ArrayList<>();
//			for (com.tsing.entity.detect.NonMotorVehicles n : nonMotorVehicles) {
//				// 简单字段映射，复杂字段可补充
//				com.tsing.entity.bindata.NonMotorVehicle nmv = new com.tsing.entity.bindata.NonMotorVehicle();
//				nmv.setId(n.getId());
//				nmv.setImg(JSONObject.from(n.getImg()));
//				nmv.setFeatures(n.getFeatures());
//				nmv.setOriginImg(JSONObject.from(n.getOriginImg()));
//				com.tsing.entity.detect.Img img = n.getImg();
//				OriginImg img1 = img.getImg();
//				img1.setURI(faceImageUrl);
//				img.setImg(img1);
//				nmvList.add(nmv);
//			}
//			bindata.setNonMotorVehicles(nmvList);
			List<NonMotorVehicle> nonMotorVehicles = result.getNonMotorVehicles();
			nonMotorVehicles.forEach(p -> {
				Data data = result.getImage().getData();
				p.setOriginImg(JSONObject.from(data));
				JSONObject img = p.getImg();
				JSONObject img1 = img.getJSONObject("Img");
				img1.put("URI", faceImageUrl);
				img.put("Img", img1);
			});
			bindata.setNonMotorVehicles(nonMotorVehicles);
			objType = 2;
		}
		// 6. Metadata
		Metadata metadata = new Metadata();
		metadata.setTimestamp(parseTime(LocalDateTime.now()));
		Metadata.AdditionalInfos additionalInfos = deviceInfo2AdditionalInfos(deviceId);
		metadata.setAdditionalInfos(additionalInfos);
		metadata.setObjType(objType);
		bindata.setMetadata(metadata);
		return bindata;
	}

	public Metadata.AdditionalInfos deviceInfo2AdditionalInfos(String deviceId) {
		DeviceDto data = deviceCacheService.getDeviceList().stream()
				.filter(d -> d.getDeviceId().equals(deviceId))
				.findFirst().orElse(null);
		if (data == null) {
			log.warn("设备不存在：{}", deviceId);
			return null;
		}
		Metadata.AdditionalInfos additionalInfos = new Metadata.AdditionalInfos();
		additionalInfos.setDevice_id(data.getDeviceId());
		additionalInfos.setDevice_name(data.getName());
		additionalInfos.setInternational_code(data.getInternationalCode());
		additionalInfos.setDevice_location(JSONUtil.toJsonStr(data.getDeviceLocation()));
		additionalInfos.setFootprints(UUID.randomUUID().toString());
		return additionalInfos;
	}

	public long parseTime(LocalDateTime localDateTime) {
		String formatStr = "yyyy-MM-dd HH:mm:ss";
		String format;
		if (localDateTime != null) {
			format = LocalDateTimeUtil.format(localDateTime, formatStr);
		} else {
			format = LocalDateTimeUtil.format(LocalDateTimeUtil.now(), formatStr);
		}
		return DateUtil.parse(format, formatStr).getTime();
	}

	/**
	 * 预留处理Bindata的方法
	 */
	private void handleBindata(Bindata bindata, Long taskId) {
		if (bindata == null) {
			return;
		}
		String binDataJson = JSON.toJSONString(bindata);
		kafkaConsumer.processBinData(String.valueOf(System.currentTimeMillis()), bindata.getMetadata().getObjType(), binDataJson,taskId);
	}

	private static class ScheduledTaskRef {
		final java.util.concurrent.ScheduledFuture<?> future;
		final String cron;

		ScheduledTaskRef(java.util.concurrent.ScheduledFuture<?> future, String cron) {
			this.future = future;
			this.cron = cron;
		}
	}

	// 定时清理3天前的任务日志
	@Scheduled(cron = "0 0 0 * * ?") // 每天0点
	public void cleanOldTaskLogs() {
		LocalDateTime expireTime = LocalDateTime.now().minusDays(3);
		int deleted = taskLogMapper.deleteOldLogs(expireTime);
		log.info("定时清理任务日志，删除 {} 条3天前日志", deleted);
	}
}
