package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.PersistObject;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan15.lottery.mvc.activity.share.ShareMedium;

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


    // derive fields
    private String spreadUsernameCache;

    public String getSpreadUsername() {
        if (!IntegerUtils.isPositive(spreadUserId)) {
            return null;
        }

        if (spreadUsernameCache == null) {
            spreadUsernameCache = new UserDao().getNameById(spreadUserId);
        }
        return spreadUsernameCache;
    }

    private String receiveUsernameCache;

    public String getReceiveUsername() {
        if (!IntegerUtils.isPositive(receiveUserId)) {
            return null;
        }

        if (receiveUsernameCache == null) {
            receiveUsernameCache = new UserDao().getNameById(receiveUserId);
        }
        return receiveUsernameCache;
    }

    public String getChineseSpreadWay(){
        return ShareMedium.getCnByEn(spreadWay);
    }
}
