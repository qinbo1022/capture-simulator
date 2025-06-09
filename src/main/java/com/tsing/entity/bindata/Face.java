package com.tsing.entity.bindata;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * com.bohua.face.kafka
 *
 * @program: face-service
 * @description:
 * @author: jiacunxu
 * @create: 2022-08-18 21:47
 **/
@Data
public class Face {
    @JSONField(name = "id")
    private Integer Id;

    @JSONField(name = "Confidence")
    private Float Confidence;

    @JSONField(name = "Img")
    private JSONObject Img;

    @JSONField(name = "AlignResult")
    private JSONObject AlignResult;

    @JSONField(name = "Qualities")
    private JSONObject Qualities;

    @JSONField(name = "Features")
    private String Features;

    @JSONField(name = "Attributes")
    private List<FaceAttribute> Attributes;

    @JSONField(name = "AlignedImage")
    private Img AlignedImage;

    @JSONField(name = "TransformedAlignResult")
    private JSONObject TransformedAlignResult;

    @JSONField(name = "FaceId")
    private String FaceId;

    @JSONField(name = "Owner")
    private JSONObject Owner;

    @JSONField(name = "OriginImg")
    private JSONObject OriginImg;

    @Data
    public static class FaceAttribute {
        @JSONField(name = "AttributeId")
        private Integer AttributeId;

        @JSONField(name = "Name")
        private String Name;

        @JSONField(name = "ValueId")
        private Integer ValueId;

        @JSONField(name = "Confidence")
        private Float Confidence;

        @JSONField(name = "ValueStr")
        private String ValueStr;

        @JSONField(name = "ValueType")
        private Integer ValueType;

    }

}
