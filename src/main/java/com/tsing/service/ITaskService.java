package com.tsing.service;

import com.tsing.entity.Task;
import com.tsing.entity.TaskLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * <p>
 * 推流任务表 服务类
 * </p>
 *
 * @author tsing
 * @since 2025-05-28
 */
public interface ITaskService extends IService<Task> {

    /**
     * 启动单个任务，按cron表达式调度
     */
    boolean startTask(Long id, String cron);

    /**
     * 停止单个任务
     */
    boolean stopTask(Long id);

    /**
     * 分页查询任务日志，支持按任务ID、任务名称、操作类型、时间区间模糊查询，返回带任务名称
     */
    IPage<TaskLog> pageTaskLogs(Long taskId, String name, String action, String startTime, String endTime, int page, int size);

}
