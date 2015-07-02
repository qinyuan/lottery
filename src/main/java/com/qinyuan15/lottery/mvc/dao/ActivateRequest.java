package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.hibernate.PersistObject;

/**
 * Class to record information about activate request.
 * <p>
 * Activate request is sent when new user is registering
 * </p>
 * Created by qinyuan on 15-7-1.
 */
public class ActivateRequest extends PersistObject {
    private Integer userId;
    private String serialKey;
    private String sendTime;
    private String responseTime;

    public Integer getUserId() {
        return userId;
    }

    public String getSerialKey() {
        return serialKey;
    }

    public String getSendTime() {
        return sendTime;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setSerialKey(String serialKey) {
        this.serialKey = serialKey;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }
}
