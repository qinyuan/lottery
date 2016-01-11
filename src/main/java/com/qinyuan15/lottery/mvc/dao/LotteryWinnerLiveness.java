package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.PersistObject;

public class LotteryWinnerLiveness extends PersistObject {
    private Integer activityId;
    private Integer userId;
    private Boolean virtual;
    private Integer liveness;
    private String recordTime;

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getVirtual() {
        return virtual;
    }

    public void setVirtual(Boolean virtual) {
        this.virtual = virtual;
    }

    public Integer getLiveness() {
        return liveness;
    }

    public void setLiveness(Integer liveness) {
        this.liveness = liveness;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }
}
