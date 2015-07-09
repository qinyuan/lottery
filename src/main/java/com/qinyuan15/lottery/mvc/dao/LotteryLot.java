package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.hibernate.PersistObject;

public class LotteryLot extends PersistObject {
    private Integer activityId;
    private Integer userId;
    private String lotTime;
    private Integer serialNumber;
    private Boolean win;

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

    public String getLotTime() {
        return lotTime;
    }

    public void setLotTime(String lotTime) {
        this.lotTime = lotTime;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Boolean getWin() {
        return win;
    }

    public void setWin(Boolean win) {
        this.win = win;
    }

    private User userCache;

    public User getUser() {
        if (userCache == null) {
            userCache = new UserDao().getInstance(userId);
        }
        return userCache;
    }
}
