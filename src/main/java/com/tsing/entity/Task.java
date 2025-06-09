package com.tsing.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 推流任务表
 * </p>
 *
 * @author tsing
 * @since 2025-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Task implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	private String taskName;

	private String deviceId;

	private String contentRef;

	private String pushRate;

	private Integer pushType;

	private String status;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

}
