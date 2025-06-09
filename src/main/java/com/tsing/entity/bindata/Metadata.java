package com.tsing.entity.bindata;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * com.bohua.face.kafka
 *
 * @program: face-service
 * @description: kafka metadta
 * @author: jiacunxu
 * @create: 2022-08-17 21:45
 **/
@Data
public class Metadata {
    @JSONField(name = "Timestamp")
    private Long Timestamp;

    @JSONField(name = "Metadata")
    private Integer Duration;

    @JSONField(name = "SensorId")
    private Integer SensorId;

    @JSONField(name = "SensorName")
    private String SensorName;

    @JSONField(name = "SensorUrl")
    private String SensorUrl;

    @JSONField(name = "RepoId")
    private Integer RepoId;

    @JSONField(name = "RepoInfo")
    private String RepoInfo;

    @JSONField(name = "ObjType")
    private Integer ObjType;

    @JSONField(name = "SensorIdStr")
    private String SensorIdStr;
    //设备id
    @JSONField(name = "UniqueSensorId")
    private String UniqueSensorId;

    @JSONField(name = "InnerTaskId")
    private String InnerTaskId;

    @JSONField(name = "AdditionalInfos")
    private AdditionalInfos AdditionalInfos;

    @Data
    public static class AdditionalInfos{
        private String device_id;
        private String footprints;
        private String device_location;
        private String device_name;
        private String international_code;
    }
    @Data
    public static class DeviceLocation {
        private String address_type;
        private String administrative_division;
        //经度
        private String longitude;
        //纬度
        private String latitude;
        private String predecessor_ids;
        private String sub_address_type;
    }

    @Data
    public static class Division {
        private String city;
        private String cityName;
        private String county;
        private String countyName;
        private String custom_region_id;
        private String custom_region_name;
        private String province;
        private String provinceName;
        private String town;
        private String townName;
        private String village;
        private String villageName;
    }

}
