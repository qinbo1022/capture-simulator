package com.tsing.entity.bindata;

/**
 * com.bohua.multialgoservice.common.enums
 *
 * @program: multi-algo-service
 * @description:
 * @author: jiacunxu
 * @create: 2022-12-08 19:28
 **/
public enum MediaResourceTypeEnum {
    photo(1, "图片"),
    viedo(2, "视频");

    private int code;

    private String desc;

    MediaResourceTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
