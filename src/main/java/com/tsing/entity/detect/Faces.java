package com.tsing.entity.detect;

/**
 * Copyright 2023 json.cn
 */
import java.util.List;

/**
 * Auto-generated: 2023-07-27 16:54:16
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class Faces {

    private int Id;
    private double Confidence;
    private Img Img;
    private AlignResult AlignResult;
    private Qualities Qualities;
    private String Features;
    private List<Attributes> Attributes;
    private String UId;
    private String ReId;
    private double PrimaryObjectScore;
    public void setId(int Id) {
        this.Id = Id;
    }
    public int getId() {
        return Id;
    }

    public void setConfidence(double Confidence) {
        this.Confidence = Confidence;
    }
    public double getConfidence() {
        return Confidence;
    }

    public void setImg(Img Img) {
        this.Img = Img;
    }
    public Img getImg() {
        return Img;
    }

    public void setAlignResult(AlignResult AlignResult) {
        this.AlignResult = AlignResult;
    }
    public AlignResult getAlignResult() {
        return AlignResult;
    }

    public void setQualities(Qualities Qualities) {
        this.Qualities = Qualities;
    }
    public Qualities getQualities() {
        return Qualities;
    }

    public void setFeatures(String Features) {
        this.Features = Features;
    }
    public String getFeatures() {
        return Features;
    }

    public void setAttributes(List<Attributes> Attributes) {
        this.Attributes = Attributes;
    }
    public List<Attributes> getAttributes() {
        return Attributes;
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