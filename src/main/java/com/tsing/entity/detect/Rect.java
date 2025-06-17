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
public class Rect {
    @JSONField(name = "CenterX")
    private int CenterX;
    @JSONField(name = "CenterY")
    private int CenterY;
    @JSONField(name = "Width")
    private int Width;
    @JSONField(name = "Height")
    private int Height;
    @JSONField(name = "Angle")
    private int Angle;

}