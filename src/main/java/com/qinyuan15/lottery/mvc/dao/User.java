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

    public String getEmail() {
        return email;
    }

    public String getTel() {
        return tel;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
