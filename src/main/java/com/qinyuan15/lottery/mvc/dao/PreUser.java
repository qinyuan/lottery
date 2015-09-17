package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.PersistObject;

public class PreUser extends PersistObject {
    private String email;
    private Integer spreadUserId;
    private String spreadWay;
    private Integer activityId;
    private String serialKey;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSpreadUserId() {
        return spreadUserId;
    }

    public void setSpreadUserId(Integer spreadUserId) {
        this.spreadUserId = spreadUserId;
    }

    public String getSpreadWay() {
        return spreadWay;
    }

    public void setSpreadWay(String spreadWay) {
        this.spreadWay = spreadWay;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getSerialKey() {
        return serialKey;
    }

    public void setSerialKey(String serialKey) {
        this.serialKey = serialKey;
    }
}
