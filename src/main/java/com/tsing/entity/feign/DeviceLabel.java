package com.tsing.entity.feign;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author qingsizhineng
 * @version 1.0
 * @project tactic-touch-service
 * @description 设备标签信息
 * @date 2025/3/20 11:59:15
 */
@Data
@Schema(description = "设备标签信息")
public class DeviceLabel {

    @Schema(description = "设备标签ID")
    private Long id;

    @Schema(description = "设备标签名称")
    private String labelName;

}
