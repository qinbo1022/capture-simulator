/**
  * Copyright 2023 json.cn 
  */
package com.tsing.entity.detect;

/**
 * Auto-generated: 2023-08-19 21:31:38
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class Color {

    private int ColorId;
    private double Confidence;
    private String ColorName;
    public void setColorId(int ColorId) {
         this.ColorId = ColorId;
     }
     public int getColorId() {
         return ColorId;
     }

    public void setConfidence(double Confidence) {
         this.Confidence = Confidence;
     }
     public double getConfidence() {
         return Confidence;
     }

    public void setColorName(String ColorName) {
         this.ColorName = ColorName;
     }
     public String getColorName() {
         return ColorName;
     }

}