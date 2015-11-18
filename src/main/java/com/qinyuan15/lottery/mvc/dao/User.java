package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.lang.time.DateUtils;

/**
 * User with email and tel
 * Created by qinyuan on 15-6-29.
 */
public class User extends com.qinyuan.lib.mvc.security.User {
    private String email;
    private String tel;
    private Boolean active;
    private String serialKey;
    private Integer spreadUserId;
    private String spreadWay;
    private String realName;
    private String gender;
    private String birthday;
    private String constellation;
    private String hometown;
    private String residence;
    private Boolean lunarBirthday;
    private Boolean receiveMail;
    private String qqOpenId;

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
        return DateUtils.trimMilliSecond(birthday);
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
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

    public Boolean getLunarBirthday() {
        return lunarBirthday;
    }

    public void setLunarBirthday(Boolean lunarBirthday) {
        this.lunarBirthday = lunarBirthday;
    }

    public Boolean getReceiveMail() {
        return receiveMail;
    }

    public void setReceiveMail(Boolean receiveMail) {
        this.receiveMail = receiveMail;
    }

    public String getQqOpenId() {
        return qqOpenId;
    }

    public void setQqOpenId(String qqOpenId) {
        this.qqOpenId = qqOpenId;
    }
}
