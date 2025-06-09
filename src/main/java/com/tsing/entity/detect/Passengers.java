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
public class Passengers {

    private int Id;
    private boolean Driver;
    private Img Img;
    private Face Face;
    private List<Attributes> Attributes;
    private int PhoneFlag;
    private int PhoneConfidence;
    private int BeltFlag;
    private int BeltConfidence;
    private int FacecoverFlag;
    private double FacecoverConfidence;
    private int SmokingFlag;
    private int SmokingConfidence;

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getId() {
        return Id;
    }

    public void setDriver(boolean Driver) {
        this.Driver = Driver;
    }

    public boolean getDriver() {
        return Driver;
    }

    public void setImg(Img Img) {
        this.Img = Img;
    }

    public Img getImg() {
        return Img;
    }

    public void setAttributes(List<Attributes> Attributes) {
        this.Attributes = Attributes;
    }

    public List<Attributes> getAttributes() {
        return Attributes;
    }

    public void setPhoneFlag(int PhoneFlag) {
        this.PhoneFlag = PhoneFlag;
    }

    public int getPhoneFlag() {
        return PhoneFlag;
    }

    public void setPhoneConfidence(int PhoneConfidence) {
        this.PhoneConfidence = PhoneConfidence;
    }

    public int getPhoneConfidence() {
        return PhoneConfidence;
    }

    public void setBeltFlag(int BeltFlag) {
        this.BeltFlag = BeltFlag;
    }

    public int getBeltFlag() {
        return BeltFlag;
    }

    public void setBeltConfidence(int BeltConfidence) {
        this.BeltConfidence = BeltConfidence;
    }

    public int getBeltConfidence() {
        return BeltConfidence;
    }

    public void setFacecoverFlag(int FacecoverFlag) {
        this.FacecoverFlag = FacecoverFlag;
    }

    public int getFacecoverFlag() {
        return FacecoverFlag;
    }

    public void setFacecoverConfidence(double FacecoverConfidence) {
        this.FacecoverConfidence = FacecoverConfidence;
    }

    public double getFacecoverConfidence() {
        return FacecoverConfidence;
    }

    public void setSmokingFlag(int SmokingFlag) {
        this.SmokingFlag = SmokingFlag;
    }

    public int getSmokingFlag() {
        return SmokingFlag;
    }

    public void setSmokingConfidence(int SmokingConfidence) {
        this.SmokingConfidence = SmokingConfidence;
    }

    public int getSmokingConfidence() {
        return SmokingConfidence;
    }

    public boolean isDriver() {
        return Driver;
    }

    public Face getFace() {
        return Face;
    }

    public void setFace(Face face) {
        Face = face;
    }
}