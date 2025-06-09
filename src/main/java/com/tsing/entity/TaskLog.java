package com.tsing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TaskLog implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long taskId;
    private String taskName;
    private String action; // start/stop/execute
    private String message;
    private LocalDateTime createTime;
} 