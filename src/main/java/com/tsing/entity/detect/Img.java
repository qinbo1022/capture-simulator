/**
  * Copyright 2023 json.cn 
  */
package com.tsing.entity.detect;

import com.alibaba.fastjson2.annotation.JSONField;
import com.tsing.entity.kafka.OriginImg;
import lombok.Data;

/**
 * Auto-generated: 2023-07-25 13:31:45
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Img {

    @JSONField(name = "Cutboard")
    private Cutboard Cutboard;
    @JSONField(name = "DetectedBox")
    private DetectedBox DetectedBox;
    @JSONField(name = "SnapBox")
    private SnapBox SnapBox;
    @JSONField(name = "Img")
    private OriginImg Img;
    @JSONField(name = "Rect")
    private Rect Rect;
    @JSONField(name = "CutboardSpeed")
    private int CutboardSpeed;
    @JSONField(name = "CutboardDirection")
    private int CutboardDirection;
    @JSONField(name = "Trajectory")
    private String Trajectory;

}