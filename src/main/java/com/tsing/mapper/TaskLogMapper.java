package com.tsing.mapper;

import com.tsing.entity.TaskLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.time.LocalDateTime;

@Mapper
public interface TaskLogMapper extends BaseMapper<TaskLog> {
    int deleteOldLogs(@Param("expireTime") LocalDateTime expireTime);
} 