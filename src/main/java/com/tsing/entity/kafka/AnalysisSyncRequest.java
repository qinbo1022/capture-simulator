package com.tsing.entity.kafka;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.annotation.JSONField;
import com.tsing.entity.bindata.RecVehicle;
import lombok.Data;

import java.util.List;

/**
 * com.bohua.face.kafka
 *
 * @program: face-service
 * @description:
 * @author: jiacunxu
 * @create: 2022-08-10 22:22
 **/
@Data
public class AnalysisSyncRequest {
    //元数据对象
    @JSONField(name = "AnalysisSyncResponseMetaDataObject")
    private AnalysisResponseMetaData AnalysisSyncResponseMetaDataObject;

    //解析结果
    @JSONField(name = "AnalysisSyncResultObjectList")
    private List<AnalysisSyncResult> AnalysisSyncResultObjectList;

    //错误信息
    @JSONField(name = "ErrorObject")
    private Error ErrorObject;

    //binData
    @JSONField(name = "AdditionalAttribute")
    private AdditionalAttribute AdditionalAttribute;

    @Data
    public static class AdditionalAttribute {
        @JSONField(name = "AlgorithmVersionCode")
        private String AlgorithmVersionCode;
        @JSONField(name = "BinData")
        private JSONObject BinData;
    }

    @Data
    public static class AnalysisResponseMetaData {
        //请求ID
        @JSONField(name = "RequestId")
        private String RequestId;
        //厂商代码
        @JSONField(name = "Vendor")
        private String Vendor;
        //请求时间戳
        @JSONField(name = "RequestTime")
        private long RequestTime;
        //完成时间戳
        @JSONField(name = "ResponseTime")
        private long ResponseTime;
        //要素解析编码
        @JSONField(name = "ElementType")
        private List<String> ElementType;
        //视频抽帧间隔
        @JSONField(name = "VideoProcessInterval")
        private Integer VideoProcessInterval;

    }

    @Data
    public static class AnalysisSyncResult {
        //图片ID
        @JSONField(name = "MediaResourceId")
        private String MediaResourceId;
        //资源类型
        @JSONField(name = "MediaResourceType")
        private Integer MediaResourceType;
        //图片url
        @JSONField(name = "MediaResourceUrl")
        private String MediaResourceUrl;
        //要素解析结果集
        @JSONField(name = "ElementResultObjectList")
        private List<ElementResult> ElementResultObjectList;
        //错误信息
        @JSONField(name = "ErrorObject")
        private Error ErrorObject;
    }

    @Data
    public static class ElementResult {
        @JSONField(name = "ElementType")
        private String ElementType;

        @JSONField(name = "AlgorithmVersion")
        private String AlgorithmVersion;

        @JSONField(name = "ElementDataObjectList")
        private List<ElementData> ElementDataObjectList;

        @JSONField(name = "ErrorObject")
        private Error ErrorObject;
    }

    @Data
    public static class ElementData {
        @JSONField(name = "MediaResourceId")
        private String MediaResourceId;

        @JSONField(name = "MediaResourceUrl")
        private String MediaResourceUrl;

        @JSONField(name = "Features")
        private String Features;
    }

    @Data
    public class Error {
        @JSONField(name = "Code")
        private int Code;
        @JSONField(name = "Message")
        private String Message;

    }

    @Data
    public static class Face {
        private JSONObject AlignResult;
        private List<FaceAttribute> Attributes;
        private Float Confidence;
        private String Features;
        private JSONObject Img;
        private JSONObject Qualities;
        private com.tsing.entity.bindata.Img AlignedImage;
        private Metadata Metadata;

        @Data
        public static class FaceAttribute {
            private Integer AttributeId;
            private String Name;
            private Integer ValueId;
            private Float Confidence;
            private String ValueStr;
            private Integer ValueType;
        }

    }

    @Data
    public static class Metadata {
        private Long Timestamp;
        private Integer ObjType;
        private String InnerTaskId;
        private AdditionalInfos AdditionalInfos;

        @Data
        public static class AdditionalInfos {
            private String device_id;
            private String footprints;
            private String device_location;
        }

        @Data
        public static class DeviceLocation {
            private String address_type;
            private String administrative_division;
            //经度
            private float longitude;
            //纬度
            private float latitude;
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

    @Data
    public static class Vehicle {
        //-机动车的区域坐标和裁剪图
        private Color Color;

        private String Features;

        private JSONObject Img;
        //-机动车的特征数据

        private VehicleModelType ModelType;

        private Metadata Metadata;

        private List<Attribute> Attributes;

        private List<VehicleSymbol> Symbols;


        private LicensePlate Plates;

        private Passenger Passengers;

        private Boolean HasFace;


        //-属性
        @Data
        public static class Attribute {
            //-属性id
            private Integer AttributeId;
            //-属性的简体中文表示
            private String AttributeName;
            //-属性值的id
            private Integer ValueId;
            //-属性值的简体中文表示
            private String ValueString;
            //-属性值的置信度
            private Float Confidence;
        }

        @Data
        public static class VehicleModelType {
            //-车辆的类型，比如轿车，卡车，公交车等
            private Integer StyleId;
            private String Style;
            private Float StyleConfidence;

            //-车辆的主品牌，比如宝马，奥迪，大众等
            private Integer BrandId;
            private String Brand;
            private Float BrandConfidence;

            //-车辆的子品牌，比如X5，Q7，途观等
            private Integer SubBrandId;
            private String SubBrand;
            private Float SubBrandConfidence;

            //-车辆的年款
            private Integer ModelYearId;
            private String ModelYear;
            private Float ModelYearConfidence;

            //-车头或车尾
            private Integer PoseId;
            private String Pose;
            private Float PoseConfidence;
        }

        @Data
        public static class Color {
            private Integer ColorId;
            private Float Confidence;
            private String ColorName;
        }

        @Data
        public static class VehicleSymbol {
            private Integer SymbolId;
            private String SymbolName;
            private String Cutboard;
        }

        @Data
        public static class LicensePlate {
            private String PlateText;
            private RecVehicle.Color Color;
            private Integer StyleId;
            private String StyleName;
            private Float Confidence;
            private String Cutboard;
            private Float LocalProvinceConfidence;
        }

        @Data
        public static class Passenger {
            private Long Id;
            private String Face;
            private Boolean Driver;
            private String Img;
            private Integer PhoneFlag;
            private Integer BeltFlag;
            private Float PhoneConfidence;
            private Float BeltConfidence;
            private List<RecVehicle.Attribute> Attributes;
            private Integer FacecoverFlag;
            private Float FacecoverConfidence;
            private Integer SmokingFlag;
            private Float SmokingConfidence;
        }

    }

    @Data
    public class Pedestrian {
        private Integer Id;
        private JSONObject Img;
        private Float Confidence;
        private List<PedesAttr> PedesAttr;
        private JSONObject Face;
        private String Features;
        private Boolean HasFace;
        private Metadata Metadata;

        private String UId;
        private String ReId;
    }

    @Data
    public static class PedesAttr {
        private Items sex;
        private Items Age;
        private Items National;
        private List<Category> Category;
    }

    @Data
    public static class Category {
        private Integer Id;
        private String CategoryName;
        private List<Items> Items;
    }

    @Data
    public static class Items {
        private Integer Id;
        private String Name;
        private Float Confidence;
    }

    @Data
    public class NonMotorVehicle {
        private Integer Id;

        private JSONObject Img;

        private List<Vehicle.Attribute> Attributes;

        private RecVehicle.LicensePlate Plates;

        private RecVehicle.Passenger Passengers;

        private Boolean HasFace;

        private String UId;

        private String ReId;

        private Metadata Metadata;

        private String Features;
    }
}
