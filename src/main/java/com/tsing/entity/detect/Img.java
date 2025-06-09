/**
  * Copyright 2023 json.cn 
  */
package com.tsing.entity.detect;

import com.tsing.entity.kafka.OriginImg;

/**
 * Auto-generated: 2023-07-25 13:31:45
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class Img {

    private Cutboard Cutboard;
    private DetectedBox DetectedBox;
    private SnapBox SnapBox;
    private OriginImg Img;
    private Rect Rect;
    private int CutboardSpeed;
    private int CutboardDirection;
    private String Trajectory;
    public void setCutboard(Cutboard Cutboard) {
         this.Cutboard = Cutboard;
     }
     public Cutboard getCutboard() {
         return Cutboard;
     }

    public void setDetectedBox(DetectedBox DetectedBox) {
         this.DetectedBox = DetectedBox;
     }
     public DetectedBox getDetectedBox() {
         return DetectedBox;
     }

    public void setSnapBox(SnapBox SnapBox) {
         this.SnapBox = SnapBox;
     }
     public SnapBox getSnapBox() {
         return SnapBox;
     }

    public void setImg(OriginImg Img) {
         this.Img = Img;
     }
     public OriginImg getImg() {
         return Img;
     }

    public void setRect(Rect Rect) {
         this.Rect = Rect;
     }
     public Rect getRect() {
         return Rect;
     }

    public void setCutboardSpeed(int CutboardSpeed) {
         this.CutboardSpeed = CutboardSpeed;
     }
     public int getCutboardSpeed() {
         return CutboardSpeed;
     }

    public void setCutboardDirection(int CutboardDirection) {
         this.CutboardDirection = CutboardDirection;
     }
     public int getCutboardDirection() {
         return CutboardDirection;
     }

    public void setTrajectory(String Trajectory) {
         this.Trajectory = Trajectory;
     }
     public String getTrajectory() {
         return Trajectory;
     }

}