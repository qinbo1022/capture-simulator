/**
  * Copyright 2023 json.cn 
  */
package com.tsing.entity.detect;
import java.util.List;

/**
 * Auto-generated: 2023-08-19 21:31:38
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class Vehicles {

    private int Id;
    private Img Img;
    private String Features;
    private ModelType ModelType;
    private Color Color;
    private List<Symbols> Symbols;
    private List<Plates> Plates;
    private List<Passengers> Passengers;
    private List<Attributes> Attributes;
    private List<Articles> Articles;
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

    public void setFeatures(String Features) {
         this.Features = Features;
     }
     public String getFeatures() {
         return Features;
     }

    public void setModelType(ModelType ModelType) {
         this.ModelType = ModelType;
     }
     public ModelType getModelType() {
         return ModelType;
     }

    public void setColor(Color Color) {
         this.Color = Color;
     }
     public Color getColor() {
         return Color;
     }

    public void setSymbols(List<Symbols> Symbols) {
         this.Symbols = Symbols;
     }
     public List<Symbols> getSymbols() {
         return Symbols;
     }

    public void setPlates(List<Plates> Plates) {
         this.Plates = Plates;
     }
     public List<Plates> getPlates() {
         return Plates;
     }

    public void setPassengers(List<Passengers> Passengers) {
         this.Passengers = Passengers;
     }
     public List<Passengers> getPassengers() {
         return Passengers;
     }

    public void setAttributes(List<Attributes> Attributes) {
         this.Attributes = Attributes;
     }
     public List<Attributes> getAttributes() {
         return Attributes;
     }

    public void setArticles(List<Articles> Articles) {
         this.Articles = Articles;
     }
     public List<Articles> getArticles() {
         return Articles;
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