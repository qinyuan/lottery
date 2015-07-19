package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.hibernate.PersistObject;

public class LotteryLiveness extends PersistObject {
    private Integer activityId;
    private Integer spreadUserId;
    private Integer receiveUserId;
    private Integer liveness;
    private String spreadWay;
    private Boolean registerBefore;

    public Integer getActivityId() {
        return activityId;
    }

    public Integer getSpreadUserId() {
        return spreadUserId;
    }

    public Integer getReceiveUserId() {
        return receiveUserId;
    }

    public Integer getLiveness() {
        return liveness;
    }

    public String getSpreadWay() {
        return spreadWay;
    }

    public Boolean getRegisterBefore() {
        return registerBefore;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public void setSpreadUserId(Integer spreadUserId) {
        this.spreadUserId = spreadUserId;
    }

    public void setReceiveUserId(Integer receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public void setLiveness(Integer liveness) {
        this.liveness = liveness;
    }

    public void setSpreadWay(String spreadWay) {
        this.spreadWay = spreadWay;
    }

    public void setRegisterBefore(Boolean registerBefore) {
        this.registerBefore = registerBefore;
    }
}
