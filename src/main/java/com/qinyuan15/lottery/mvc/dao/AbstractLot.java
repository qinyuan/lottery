package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.database.hibernate.PersistObject;

public class AbstractLot extends PersistObject {
    private Integer activityId;
    private Integer userId;
    private String lotTime;
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

    public Boolean getWin() {
        return win;
    }

    public void setWin(Boolean win) {
        this.win = win;
    }

    ///////////////////////////////// derivative fields ///////////////////////////////////
    private User userCache;

    public User getUser() {
        if (userCache == null) {
            userCache = new UserDao().getInstance(userId);
        }
        return userCache;
    }
}
