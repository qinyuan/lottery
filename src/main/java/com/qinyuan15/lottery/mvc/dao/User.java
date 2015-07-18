package com.qinyuan15.lottery.mvc.dao;

/**
 * User with email and tel
 * Created by qinyuan on 15-6-29.
 */
public class User extends com.qinyuan15.utils.security.User {
    public final static String ADMIN = "ROLE_ADMIN";
    public final static String NORMAL = "ROLE_NORMAL";

    private String email;
    private String tel;
    private Boolean active;
    private Integer liveness;
    private String serialKey;
    private Integer spreadUserId;
    private String spreadWay;

    public String getEmail() {
        return email;
    }

    public String getTel() {
        return tel;
    }

    public Boolean getActive() {
        return active;
    }

    public String getSerialKey() {
        return serialKey;
    }

    public Integer getLiveness() {
        return liveness;
    }

    public Integer getSpreadUserId() {
        return spreadUserId;
    }

    public String getSpreadWay() {
        return spreadWay;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setLiveness(Integer liveness) {
        this.liveness = liveness;
    }

    public void setSerialKey(String serialKey) {
        this.serialKey = serialKey;
    }

    public void setSpreadUserId(Integer spreadUserId) {
        this.spreadUserId = spreadUserId;
    }

    public void setSpreadWay(String spreadWay) {
        this.spreadWay = spreadWay;
    }
}
