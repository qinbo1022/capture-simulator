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
 * @create: 2022-08-18 22:51
 **/
@Data
public class Pedestrian {
    @JSONField(name = "Id")
    private Integer Id;
    @JSONField(name = "Img")
    private com.tsing.entity.detect.Img Img;
    @JSONField(name = "Confidence")
    private Float Confidence;
    @JSONField(name = "PedesAttr")
    private PedesAttr PedesAttr;
    @JSONField(name = "Face")
    private JSONObject Face;
    @JSONField(name = "Features")
    private String Features;
    @JSONField(name = "HasFace")
    private Boolean HasFace;
    @JSONField(name = "UId")
    private String UId;
    @JSONField(name = "ReId")
    private String ReId;
    @JSONField(name = "MatchedFace")
    private Face MatchedFace;
    @JSONField(name = "OriginImg")
    private Img OriginImg;

    @Data
    public static class PedesAttr{
        @JSONField(name = "sex")
        private Items sex;
        @JSONField(name = "Age")
        private Items Age;
        @JSONField(name = "National")
        private Items National;
        @JSONField(name = "Category")
        private List<Category> Category;
    }
    @Data
    public static class Category{
        @JSONField(name = "Id")
        private Integer Id;
        @JSONField(name = "CategoryName")
        private String CategoryName;
        @JSONField(name = "Items")
        private List<Items> Items;
    }
    @Data
    public static class Items {
        @JSONField(name = "Id")
        private Integer Id;
        @JSONField(name = "Name")
        private String Name;
        @JSONField(name = "Confidence")
        private Float Confidence;
    }
}
