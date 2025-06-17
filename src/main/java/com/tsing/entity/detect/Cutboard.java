/**
  * Copyright 2023 json.cn 
  */
package com.tsing.entity.detect;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * Auto-generated: 2023-07-25 13:31:45
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Cutboard {

    @JSONField(name = "X")
    private int X;
    @JSONField(name = "Y")
    private int Y;
    @JSONField(name = "Width")
    private int Width;
    @JSONField(name = "Height")
    private int Height;
    @JSONField(name = "ResWidth")
    private int ResWidth;
    @JSONField(name = "ResHeight")
    private int ResHeight;
    @JSONField(name = "Confidence")
    private double Confidence;

}