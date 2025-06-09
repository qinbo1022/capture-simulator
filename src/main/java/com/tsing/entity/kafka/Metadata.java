/**
 * Copyright 2023 json.cn
 */
package com.tsing.entity.kafka;

/**
 * Auto-generated: 2023-07-27 11:18:24
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class Metadata {
    public Metadata(long timestamp, int objType, AdditionalInfos additionalInfos) {
        Timestamp = timestamp;
        ObjType = objType;
        AdditionalInfos = additionalInfos;
    }

    public Metadata() {
    }

    private long Timestamp;
    private int Duration;
    private int SensorId;
    private String SensorName = "";
    private String SensorUrl = "";
    private int RepoId;
    private String RepoInfo;
    private int ObjType;
    private String SensorIdStr = "";
    private String UniqueSensorId = "";
    private AdditionalInfos AdditionalInfos;
    private String InnerTaskId = "";

    public void setTimestamp(long Timestamp) {
        this.Timestamp = Timestamp;
    }

    public long getTimestamp() {
        return Timestamp;
    }

    public void setDuration(int Duration) {
        this.Duration = Duration;
    }

    public int getDuration() {
        return Duration;
    }

    public void setSensorId(int SensorId) {
        this.SensorId = SensorId;
    }

    public int getSensorId() {
        return SensorId;
    }

    public void setSensorName(String SensorName) {
        this.SensorName = SensorName;
    }

    public String getSensorName() {
        return SensorName;
    }

    public void setSensorUrl(String SensorUrl) {
        this.SensorUrl = SensorUrl;
    }

    public String getSensorUrl() {
        return SensorUrl;
    }

    public void setRepoId(int RepoId) {
        this.RepoId = RepoId;
    }

    public int getRepoId() {
        return RepoId;
    }

    public void setRepoInfo(String RepoInfo) {
        this.RepoInfo = RepoInfo;
    }

    public String getRepoInfo() {
        return RepoInfo;
    }

    public void setObjType(int ObjType) {
        this.ObjType = ObjType;
    }

    public int getObjType() {
        return ObjType;
    }

    public void setSensorIdStr(String SensorIdStr) {
        this.SensorIdStr = SensorIdStr;
    }

    public String getSensorIdStr() {
        return SensorIdStr;
    }

    public void setUniqueSensorId(String UniqueSensorId) {
        this.UniqueSensorId = UniqueSensorId;
    }

    public String getUniqueSensorId() {
        return UniqueSensorId;
    }

    public void setAdditionalInfos(AdditionalInfos AdditionalInfos) {
        this.AdditionalInfos = AdditionalInfos;
    }

    public AdditionalInfos getAdditionalInfos() {
        return AdditionalInfos;
    }

    public void setInnerTaskId(String InnerTaskId) {
        this.InnerTaskId = InnerTaskId;
    }

    public String getInnerTaskId() {
        return InnerTaskId;
    }

}