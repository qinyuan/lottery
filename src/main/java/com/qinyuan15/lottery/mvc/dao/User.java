package com.qinyuan15.lottery.mvc.dao;

/**
 * User with email and tel
 * Created by qinyuan on 15-6-29.
 */
public class User extends com.qinyuan.lib.mvc.security.User {
    public final static String ADMIN = "ROLE_ADMIN";
    public final static String NORMAL = "ROLE_NORMAL";

    private String email;
    private String tel;
    private Boolean active;
    private String serialKey;
    private Integer spreadUserId;
    private String spreadWay;
    private String realName;
    private String gender;
    private String birthday;
    private String starSign;
    private String hometown;
    private String residence;

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

    public void setSerialKey(String serialKey) {
        this.serialKey = serialKey;
    }

    public void setSpreadUserId(Integer spreadUserId) {
        this.spreadUserId = spreadUserId;
    }

    public void setSpreadWay(String spreadWay) {
        this.spreadWay = spreadWay;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getStarSign() {
        return starSign;
    }

    public void setStarSign(String starSign) {
        this.starSign = starSign;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }
}
