package com.tsing.entity.feign;


import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 设备信息dto
 */
@Data
@Schema(description ="设备信息 DTO")
public class DeviceDto {

    @Schema(description = "设备ID")
    private Long id;

    @Schema(description = "设备ID")
    private String deviceId;

    @Schema(description = "个性应用类型 0: 其他应用,1: 反恐应用,2: 上访应用,3: 社区管理")
    private String applicationType;

    @Schema(description = "卡口类型 0: 其他,1: 人像卡口（可以识别人脸+人体）,2: 治安卡口（只能识别人体）")
    private String checkpointType;

    @Schema(description = "1:启动,0:停用")
    private String enable;

    @Schema(description = "评价信息")
    private String evaluation;

    @Schema(description = "识别内容 '0': 识别所有特征,'1': 仅识别人脸特征,'2':识别人体特征,'3': 识别人脸+人体特征,'3': 车辆识别")
    private String identificationType;

    @Schema(description = "图像信息")
    private String imageParam;

    @Schema(description = "设备国标编码")
    private String internationalCode;

    @Schema(description =  "位置类型")
    private String addressType;

    @Schema(description = "纬度")
    private BigDecimal latitude;

    @Schema(description = "经度")
    private BigDecimal longitude;

    @Schema(description = "所属区域id")
    private Integer predecessorIds;

    @Schema(description = "点位类型细分")
    private String subAddressType;

    @Schema(description = "行政区域编码")
    private String locationCode;

    @Schema(description = "设备名称")
    private String name;

    @Schema(description = "权限表")
    private String permission;

    @Schema(description = "平台国标编码")
    private String platformInternationalCode;

    @Schema(description = "视频流来源,1 取流地址 2国标流")
    private String videoSource;

    @Schema(description = "关联设备信息")
    private String relatedDevices;

    @JsonIgnore
    @Schema(description = "相关方信息")
    private String relater;

    @JsonIgnore
    @Schema(description = "多个感兴趣区域或屏蔽区域")
    private String rois;

    private DeviceLocationDto deviceLocation;

    @Schema(description = "运行情况 0：关闭 1：在线 2：异常")
    private String status;

    @Schema(description = "rtsp底层传输协议 0: UDP,1: TCP,2: UDP_Multicast,3: HTTP")
    private String transportProtocol;

    @Schema(description = "设备类型")
    private String type;

    @Schema(description = "设备url")
    private String url;

    @Schema(description = "最新抓拍照")
    private String snapImgUrl;

    @Schema(description = "设备等级")
    private String deviceLevel;

    @Schema(description = "设备IP")
    private String deviceIp;

    @Schema(description = "解析机ID")
    private Long analyticalMachineId;

    @Schema(description = "解析机名称")
    private String analyticalMachineName;

    @Schema(description = "视频回放地址")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String videoPlaybackAddress;

    @Schema(description = "昨日抓拍质量")
    private BigDecimal yesterdaySnapshotQuality;

    @Schema(description = "昨日抓拍质量百分比")
    private BigDecimal yesterdaySnapshotQualityPer;

    @Schema(description = "昨日抓拍描述")
    private String yesterdaySnapshotQualityDesc;

    @Schema(description = "昨日抓拍数量")
    private Integer yesterdayCapture;

    @Schema(description = "是否自动解析 1:是 0:否")
    private String isAutoAnalysis;

    @Schema(description = "是否开启highQuality 1:是 0:否")
    private String isAutoHq;

    @Schema(description = "今日抓拍质量")
    private BigDecimal todaySnapshotQuality;

    @Schema(description = "当日有质量的人脸抓拍数量")
    private Integer todayFaceQualityCount;

    @Schema(description = "今日抓拍数")
    private Integer todayCapture;

    @Schema(description = "抓拍数")
    private Integer capture;

    @Schema(description = "预警数")
    private Integer alert;

    @Schema(description = "昨日预警数")
    private Integer yesterdayAlert;

    @Schema(description = "当日人脸抓拍")
    private Integer todayFaceCapture;

    @Schema(description = "累计人脸抓拍")
    private Integer faceCapture;

    @Schema(description = "当日车俩抓拍")
    private Integer todayVehicleCapture;

    @Schema(description = "累计车俩抓拍")
    private Integer vehicleCapture;

    @Schema(description = "当日非机动车")
    private Integer todayNonVotorVehicleCapture;

    @Schema(description = "累计非机动车")
    private Integer nonVotorVehicleCapture;

    @Schema(description = "当日人体抓拍量")
    private Integer todayPedestrianCapture;

    @Schema(description = "累计人体抓拍量")
    private Integer pedestrianCapture;

    @Schema(description = "自定义字段")
    private Object extraMeta;

    @Schema(description = "注册时间")
    private String registerTime;

    @Schema(description = "心跳时间")
    private String keepaliveTime;

    @Schema(description = "通道个数")
    private int channelCount;

    @Schema(description = "注册有效期")
    private int expires;

    @Schema(description = "字符集 支持 UTF-8 与 GB2312")
    private String charset;

    @Schema(description ="设备地点描述")
    private String deviceLocationDesc;

    @Schema(description = "设备标签")
    private List<DeviceLabel> deviceLabels;

    @Schema(description = "删除标识 0: 未删除,1: 已删除")
    private Integer delFlag;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "创建人")
    private String createBy;

    @Schema(description = "修改时间")
    private Date updateTime;

    @Schema(description = "修改人")
    private String updateBy;

    @Data
    @Schema(description ="设备地点信息")
    public static class DeviceLocationDto {

        @Schema(description = "设备地点名称")
        private String address_type_name;

        @Schema(description = "点位类型")
        private String address_type;

        @Schema(description = "行政区域信息")
        private DeviceDivisionInfo deviceDivisionInfo;

        @Schema(description = "纬度")
        private BigDecimal latitude;

        @Schema(description = "经度")
        private BigDecimal longitude;

        @Schema(description = "所属区域id")
        private Integer predecessor_ids;

        @Schema(description = "点位类型细分")
        private String sub_address_type;

    }

    @Data
    @Schema(description ="行政区域")
    public static class DeviceDivisionInfo {

        @Schema(description = "行政区划编码")
        private String code;

        @Schema(description = "行政区划名称")
        private String name;

    }

}
