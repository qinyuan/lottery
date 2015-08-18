package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.database.hibernate.PersistObject;

public class VirtualUser extends PersistObject {
    private String username;
    private String telPrefix;
    private String telSuffix;
    private String mailPrefix;
    private String mailSuffix;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelPrefix() {
        return telPrefix;
    }

    public void setTelPrefix(String telPrefix) {
        this.telPrefix = telPrefix;
    }

    public String getTelSuffix() {
        return telSuffix;
    }

    public void setTelSuffix(String telSuffix) {
        this.telSuffix = telSuffix;
    }

    public String getMailPrefix() {
        return mailPrefix;
    }

    public void setMailPrefix(String mailPrefix) {
        this.mailPrefix = mailPrefix;
    }

    public String getMailSuffix() {
        return mailSuffix;
    }

    public void setMailSuffix(String mailSuffix) {
        this.mailSuffix = mailSuffix;
    }

    // derivative fields
    public String getTel() {
        return this.telPrefix + "**" + this.telSuffix;
    }

    public String getMail() {
        return this.mailPrefix + "**" + this.mailSuffix;
    }
}
