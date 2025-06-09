package com.tsing.entity.bindata;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * com.bohua.face.kafka
 *
 * @program: face-service
 * @description: bindata数据class
 * @author: jiacunxu
 * @create: 2022-08-18 20:52
 **/
@Data
public class Bindata {
    @JSONField(name = "Metadata")
    private Metadata Metadata;

    @JSONField(name = "Img")
    private Img Img;

    @JSONField(name = "Vehicle")
    private List<RecVehicle> Vehicle;

    @JSONField(name = "Faces")
    private List<Face> Faces;

    @JSONField(name = "Pedestrian")
    private List<Pedestrian> Pedestrian;

    @JSONField(name = "NonMotorVehicles")
    private List<NonMotorVehicle> NonMotorVehicles;
}
