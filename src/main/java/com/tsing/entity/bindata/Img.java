package com.tsing.entity.bindata;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * com.bohua.face.kafka
 *
 * @program: face-service
 * @description:
 * @author: jiacunxu
 * @create: 2022-08-17 21:50
 **/
@Data
public class Img {
    @JSONField(name = "Width")
    private Integer Width;

    @JSONField(name = "Height")
    private Integer Height;
    //图片URL
    @JSONField(name = "URI")
    private String URI;

    @JSONField(name = "BinData")
    private String BinData;

    @JSONField(name = "Sn")
    private Long Sn;
}
