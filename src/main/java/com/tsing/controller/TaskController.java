package com.tsing.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tsing.entity.Task;
import com.tsing.entity.TaskLog;
import com.tsing.mapper.TaskMapper;
import com.tsing.service.ITaskService;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 推流任务表 前端控制器
 * </p>
 *
 * @author tsing
 * @since 2025-05-28
 */
@Slf4j
@RestController
@RequestMapping("/task")
public class TaskController {

	private final TaskMapper taskMapper;
	private final ITaskService taskService;

	public TaskController(TaskMapper taskMapper, ITaskService taskService) {
		this.taskMapper = taskMapper;
		this.taskService = taskService;
	}

	@GetMapping("/list")
	public List<Task> listTasks() {
		return taskMapper.selectList(null);
	}

	@GetMapping("/page")
	public IPage<Task> pageTasks(@RequestParam(defaultValue = "1") int page,
								 @RequestParam(defaultValue = "10") int size) {
		log.info("分页查询任务: page={}, size={}", page, size);
		Page<Task> p = new Page<>(page, size);
		return taskMapper.selectPage(p, null);
	}

	@PostMapping("/add")
	public boolean addTask(@RequestBody Task task) {
		log.info("新增任务: {}", task);
		// 任务名称唯一性校验
		if (task.getTaskName() != null) {
			com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<Task> qw = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
			qw.eq("task_name", task.getTaskName());
			Long count = taskMapper.selectCount(qw);
			if (count != null && count > 0) {
				return false;
			}
		}
		if (task.getStatus() == null) {
			// 默认待启动
			task.setStatus("0");
		}
		return taskMapper.insert(task) > 0;
	}

	@PostMapping("/edit")
	public boolean editTask(@RequestBody Task task) {
		log.info("编辑任务: {}", task);
		Task old = taskMapper.selectById(task.getId());
		if (old != null && "1".equals(old.getStatus())) {
			// 运行中禁止编辑
			return false;
		}
		// 任务名称唯一性校验（排除自己）
		if (task.getTaskName() != null) {
			com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<Task> qw = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
			qw.eq("task_name", task.getTaskName());
			qw.ne("id", task.getId());
			Long count = taskMapper.selectCount(qw);
			if (count != null && count > 0) {
				return false;
			}
		}
		return taskMapper.updateById(task) > 0;
	}

	@PostMapping("/delete")
	public boolean deleteTask(@RequestParam Long id) {
		log.info("删除任务: id={}", id);
		Task old = taskMapper.selectById(id);
		if (old != null && "1".equals(old.getStatus())) {
			// 运行中禁止删除
			return false;
		}
		return taskMapper.deleteById(id) > 0;
	}

	@PostMapping("/batchDelete")
	public boolean batchDelete(@RequestBody Map<String, Object> body) {
		Object idsObj = body.get("ids");
		if (!(idsObj instanceof java.util.List)) {
			return false;
		}
		@SuppressWarnings("unchecked")
		java.util.List<Object> ids = (java.util.List<Object>) idsObj;
		for (Object id : ids) {
			Task old = taskMapper.selectById(Long.valueOf(id.toString()));
			if (old != null && "1".equals(old.getStatus())) {
				// 运行中禁止删除
				return false;
			}
		}
		for (Object id : ids) {
			taskMapper.deleteById(Long.valueOf(id.toString()));
		}
		return true;
	}

	@PostMapping("/batchStart")
	public boolean batchStart(@RequestBody Map<String, Object> body) {
		log.info("批量启动任务: {}", body);
		Object idsObj = body.get("ids");
		String cron = body.get("cron") != null ? body.get("cron").toString() : null;
		if (!(idsObj instanceof java.util.List)) {
			return false;
		}
		@SuppressWarnings("unchecked")
		java.util.List<Object> ids = (java.util.List<Object>) idsObj;
		for (Object id : ids) {
			taskService.startTask(Long.valueOf(id.toString()), cron);
		}
		return true;
	}

	@PostMapping("/batchPause")
	public boolean batchPause(@RequestBody Map<String, Object> body) {
		Object idsObj = body.get("ids");
		if (!(idsObj instanceof java.util.List)) {
			return false;
		}
		@SuppressWarnings("unchecked")
		java.util.List<Object> ids = (java.util.List<Object>) idsObj;
		for (Object id : ids) {
			Task t = new Task();
			t.setId(Long.valueOf(id.toString()));
			// 1=暂停中
			t.setStatus("1");
			taskMapper.updateById(t);
		}
		return true;
	}

	@PostMapping("/batchStop")
	public boolean batchStop(@RequestBody Map<String, Object> body) {
		log.info("批量停止任务: {}", body);
		Object idsObj = body.get("ids");
		if (!(idsObj instanceof java.util.List)) {
			return false;
		}
		@SuppressWarnings("unchecked")
		java.util.List<Object> ids = (java.util.List<Object>) idsObj;
		for (Object id : ids) {
			taskService.stopTask(Long.valueOf(id.toString()));
		}
		return true;
	}

	@GetMapping("/logPage")
	public IPage<TaskLog> pageTaskLogs(@RequestParam(required = false) Long taskId,
									   @RequestParam(required = false) String name,
									   @RequestParam(required = false) String action,
									   @RequestParam(required = false) String startTime,
									   @RequestParam(required = false) String endTime,
									   @RequestParam(defaultValue = "1") int page,
									   @RequestParam(defaultValue = "10") int size) {
		return taskService.pageTaskLogs(taskId, name, action, startTime, endTime, page, size);
	}

	@PostMapping("/start")
	public boolean startTask(@RequestParam Long id) {
		// 取任务cron
		Task task = taskService.getById(id);
		if (task == null) return false;
		String cron = task.getPushRate();
		return taskService.startTask(id, cron);
	}

	@PostMapping("/stop")
	public boolean stopTask(@RequestParam Long id) {
		return taskService.stopTask(id);
	}

}

