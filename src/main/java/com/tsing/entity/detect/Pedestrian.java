/**
  * Copyright 2023 json.cn 
  */
package com.tsing.entity.detect;

/**
 * Auto-generated: 2023-07-25 13:31:45
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class Pedestrian {

    private int Id;
    private Img Img;
    private PedesAttr PedesAttr;
    private Face Face;
    private String Features;
    private boolean HasFace;
    private String UId;
    private String ReId;
    private double PrimaryObjectScore;
    public void setId(int Id) {
         this.Id = Id;
     }
     public int getId() {
         return Id;
     }

    public void setImg(Img Img) {
         this.Img = Img;
     }
     public Img getImg() {
         return Img;
     }

    public void setPedesAttr(PedesAttr PedesAttr) {
         this.PedesAttr = PedesAttr;
     }
     public PedesAttr getPedesAttr() {
         return PedesAttr;
     }

    public void setFace(Face Face) {
         this.Face = Face;
     }
     public Face getFace() {
         return Face;
     }

    public void setFeatures(String Features) {
         this.Features = Features;
     }
     public String getFeatures() {
         return Features;
     }

    public void setHasFace(boolean HasFace) {
         this.HasFace = HasFace;
     }
     public boolean getHasFace() {
         return HasFace;
     }

    public void setUId(String UId) {
         this.UId = UId;
     }
     public String getUId() {
         return UId;
     }

    public void setReId(String ReId) {
         this.ReId = ReId;
     }
     public String getReId() {
         return ReId;
     }

    public void setPrimaryObjectScore(double PrimaryObjectScore) {
         this.PrimaryObjectScore = PrimaryObjectScore;
     }
     public double getPrimaryObjectScore() {
         return PrimaryObjectScore;
     }

}