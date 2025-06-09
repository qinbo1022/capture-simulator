package com.tsing.mapper;

import com.tsing.entity.Task;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 推流任务表 Mapper 接口
 * </p>
 *
 * @author tsing
 * @since 2025-05-28
 */
@Mapper
public interface TaskMapper extends BaseMapper<Task> {

}
