package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.PersistObject;

public class VirtualUser extends PersistObject {
    private String username;
    private String telPrefix;
    private String telSuffix;
    private String mailPrefix;
    private String mailSuffix;
    private Boolean active;

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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    // derivative fields
    public String getTel() {
        return this.telPrefix + "****" + this.telSuffix;
    }

    public String getMail() {
        return this.mailPrefix + "**" + this.mailSuffix;
    }
}
