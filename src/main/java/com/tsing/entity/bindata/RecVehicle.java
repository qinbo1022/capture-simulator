package com.tsing.entity.bindata;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * com.bohua.face.kafka
 *
 * @program: face-service
 * @description: 机动车
 * @author: jiacunxu
 * @create: 2022-08-17 23:58
 **/
@Data
public class RecVehicle {
    //-机动车对象的id，由matrix分配，同一次调用中唯一
    @JSONField(name = "id")
    private Long id;

    //-机动车的区域坐标和裁剪图
    @JSONField(name = "Img")
    private JSONObject Img;
    //-机动车的特征数据
    @JSONField(name = "Features")
    private String Features;

    @JSONField(name = "ModelType")
    private VehicleModelType ModelType;

    @JSONField(name = "Color")
    private Color Color;

    @JSONField(name = "Symbols")
    private List<VehicleSymbol> Symbols;

    @JSONField(name = "Plates")
    private List<LicensePlate> Plates;

    @JSONField(name = "Passengers")
    private Passenger Passengers;

    @JSONField(name = "Attributes")
    private List<Attribute> Attributes;

    @JSONField(name = "HasFace")
    private Boolean HasFace;

    @JSONField(name = "UId")
    private String UId;

    @JSONField(name = "ReId")
    private String ReId;

    @JSONField(name = "OriginImg")
    private JSONObject OriginImg;



    //-属性
    @Data
    public static class Attribute {
        //-属性id
        @JSONField(name = "AttributeId")
        private Integer AttributeId;
        //-属性的简体中文表示
        @JSONField(name = "AttributeName")
        private String AttributeName;
        //-属性值的id
        @JSONField(name = "ValueId")
        private Integer ValueId;
        //-属性值的简体中文表示
        @JSONField(name = "ValueString")
        private String ValueString;
        //-属性值的置信度
        @JSONField(name = "Confidence")
        private Float Confidence;
    }


    @Data
    public static class Passenger {
        @JSONField(name = "Id")
        private Long Id;
        @JSONField(name = "Face")
        private String Face;
        @JSONField(name = "Driver")
        private Boolean Driver;
        @JSONField(name = "Img")
        private String Img;
        @JSONField(name = "PhoneFlag")
        private Integer PhoneFlag;
        @JSONField(name = "BeltFlag")
        private Integer BeltFlag;
        @JSONField(name = "PhoneConfidence")
        private Float PhoneConfidence;
        @JSONField(name = "BeltConfidence")
        private Float BeltConfidence;
        @JSONField(name = "Attributes")
        private List<Attribute> Attributes;
        @JSONField(name = "FacecoverFlag")
        private Integer FacecoverFlag;
        @JSONField(name = "FacecoverConfidence")
        private Float FacecoverConfidence;
        @JSONField(name = "SmokingFlag")
        private Integer SmokingFlag;
        @JSONField(name = "SmokingConfidence")
        private Float SmokingConfidence;
    }

    @Data
    public static class LicensePlate {
        @JSONField(name = "PlateText")
        private String PlateText;
        @JSONField(name = "Color")
        private Color Color;
        @JSONField(name = "StyleId")
        private Integer StyleId;
        @JSONField(name = "StyleName")
        private String StyleName;
        @JSONField(name = "Confidence")
        private Float Confidence;
        @JSONField(name = "Cutboard")
        private JSONObject Cutboard;
        @JSONField(name = "LocalProvinceConfidence")
        private Float LocalProvinceConfidence;
    }

    @Data
    public static class VehicleSymbol {
        @JSONField(name = "SymbolId")
        private Integer SymbolId;
        @JSONField(name = "SymbolName")
        private String SymbolName;
        @JSONField(name = "Cutboard")
        private String Cutboard;
    }

    @Data
    public static class Color {
        @JSONField(name = "ColorId")
        private Integer ColorId;
        @JSONField(name = "Confidence")
        private Float Confidence;
        @JSONField(name = "ColorName")
        private String ColorName;
    }

    @Data
    public static class VehicleModelType {
        //-车辆的类型，比如轿车，卡车，公交车等
        @JSONField(name = "StyleId")
        private Integer StyleId;
        @JSONField(name = "Style")
        private String Style;
        @JSONField(name = "StyleConfidence")
        private Float StyleConfidence;

        //-车辆的主品牌，比如宝马，奥迪，大众等
        @JSONField(name = "BrandId")
        private Integer BrandId;
        @JSONField(name = "Brand")
        private String Brand;
        @JSONField(name = "BrandConfidence")
        private Float BrandConfidence;

        //-车辆的子品牌，比如X5，Q7，途观等
        @JSONField(name = "SubBrandId")
        private Integer SubBrandId;
        @JSONField(name = "SubBrand")
        private String SubBrand;
        @JSONField(name = "SubBrandConfidence")
        private Float SubBrandConfidence;

        //-车辆的年款
        @JSONField(name = "ModelYearId")
        private Integer ModelYearId;
        @JSONField(name = "ModelYear")
        private String ModelYear;
        @JSONField(name = "ModelYearConfidence")
        private Float ModelYearConfidence;

        //-车头或车尾
        @JSONField(name = "PoseId")
        private Integer PoseId;
        @JSONField(name = "Pose")
        private String Pose;
        @JSONField(name = "PoseConfidence")
        private Float PoseConfidence;
    }
}
