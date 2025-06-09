/**
 * Copyright 2023 json.cn
 */
package com.tsing.entity.detect;

import com.tsing.entity.bindata.Face;
import com.tsing.entity.bindata.RecVehicle;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2023-07-25 13:31:45
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Result {

	private String InnerStatus;
	private String InnerMessage;
	private Image Image;
	private List<com.tsing.entity.bindata.Pedestrian> Pedestrian;
	private List<Face> Faces;
	private List<RecVehicle> Vehicles;
	private List<com.tsing.entity.bindata.NonMotorVehicle> NonMotorVehicles;
}