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
public class Plates {

    private String PlateText;
    private Color Color;
    private int StyleId;
    private String StyleName;
    private double Confidence;
    private Cutboard Cutboard;
    private double LocalProvinceConfidence;
    public void setPlateText(String PlateText) {
         this.PlateText = PlateText;
     }
     public String getPlateText() {
         return PlateText;
     }

    public void setColor(Color Color) {
         this.Color = Color;
     }
     public Color getColor() {
         return Color;
     }

    public void setStyleId(int StyleId) {
         this.StyleId = StyleId;
     }
     public int getStyleId() {
         return StyleId;
     }

    public void setStyleName(String StyleName) {
         this.StyleName = StyleName;
     }
     public String getStyleName() {
         return StyleName;
     }

    public void setConfidence(double Confidence) {
         this.Confidence = Confidence;
     }
     public double getConfidence() {
         return Confidence;
     }

    public void setCutboard(Cutboard Cutboard) {
         this.Cutboard = Cutboard;
     }
     public Cutboard getCutboard() {
         return Cutboard;
     }

    public void setLocalProvinceConfidence(double LocalProvinceConfidence) {
         this.LocalProvinceConfidence = LocalProvinceConfidence;
     }
     public double getLocalProvinceConfidence() {
         return LocalProvinceConfidence;
     }

}