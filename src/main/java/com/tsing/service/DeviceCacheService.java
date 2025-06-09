package com.tsing.service;

import cn.hutool.core.date.StopWatch;
import com.tsing.entity.feign.DeviceDto;
import com.tsing.entity.feign.DeviceServiceBaseResponse;
import com.tsing.feign.DeviceServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * DeviceCacheService 用于缓存设备信息，定期从远程服务刷新数据。
 * 提供线程安全的读写操作，通过 ReentrantReadWriteLock 控制并发访问。
 */
@Service
public class DeviceCacheService {

    private static final Logger logger = LoggerFactory.getLogger(DeviceCacheService.class);

    @Autowired
    private DeviceServiceClient deviceServiceClient;

    // 缓存设备信息列表，默认为空列表以避免空指针异常
    private List<DeviceDto> deviceCache = Collections.emptyList();

    // 使用可重入读写锁保证线程安全
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * 初始化方法，在 Bean 构造完成后调用，用于首次加载设备缓存。
     */
    @PostConstruct
    public void init() {
        logger.info("初始化设备缓存...");
        refreshCache();
    }

    /**
     * 定时任务，每小时刷新一次设备缓存。
     */
    @Scheduled(fixedRate = 60 * 60 * 1000) // 1小时刷新
    public void scheduledRefresh() {
        logger.info("定时刷新设备缓存...");
        refreshCache();
    }

    /**
     * 刷新设备缓存，从远程服务获取最新数据并更新本地缓存。
     * 如果请求成功且返回有效数据，则替换当前缓存。
     */
    public void refreshCache() {
        try {
            DeviceServiceBaseResponse<List<DeviceDto>> resp = deviceServiceClient.getDeviceInfo();
            if (resp != null && resp.getCode() == 0 && resp.getData() != null) {
                lock.writeLock().lock();
                try {
                    logger.info("更新设备缓存，新数据大小: {}", resp.getData().size());
                    deviceCache = resp.getData();
                } finally {
                    lock.writeLock().unlock();
                }
            } else {
                logger.error("无法刷新设备缓存，远程服务返回无效数据或错误码: {}", resp != null ? resp.getCode() : "null");
            }
        } catch (Exception e) {
            logger.error("刷新设备缓存过程中发生异常", e);
        }
    }

    /**
     * 获取当前缓存的设备列表。
     *
     * @return 设备 DTO 列表
     */
    public List<DeviceDto> getDeviceList() {
        StopWatch stopWatch = new StopWatch("获取缓存");
        stopWatch.start();
        lock.readLock().lock();
        try {
            logger.info("读取设备缓存，当前数据大小: {}", deviceCache.size());
            return deviceCache;
        } finally {
            lock.readLock().unlock();
            stopWatch.stop();
            logger.info("获取缓存完成，耗时: {}", stopWatch.shortSummary(TimeUnit.MILLISECONDS));
        }
	}
}
