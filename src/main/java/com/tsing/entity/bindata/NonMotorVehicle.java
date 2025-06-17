package com.tsing.entity.bindata;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * com.bohua.face.kafka
 *
 * @program: face-service
 * @description: 非机动车
 * @author: jiacunxu
 * @create: 2022-08-19 08:32
 **/
@Data
public class NonMotorVehicle {
    //-机动车的特征数据
    @JSONField(name = "Features")
    private String Features;

    @JSONField(name = "Id")
    private Integer Id;

    @JSONField(name = "Img")
    private com.tsing.entity.detect.Img Img;

    @JSONField(name = "Attributes")
    private List<RecVehicle.Attribute> Attributes;

    @JSONField(name = "Plates")
    private RecVehicle.LicensePlate Plates;

    @JSONField(name = "Passengers")
    private List<RecVehicle.Passenger> Passengers;

    @JSONField(name = "OriginImg")
    private Img OriginImg;
}
