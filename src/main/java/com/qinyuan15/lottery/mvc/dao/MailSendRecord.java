package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.hibernate.PersistObject;

public class MailSendRecord extends PersistObject {

    private Integer mailAccountId;
    private Integer userId;
    private Integer mailId;
    private String sendTime;

    public Integer getMailAccountId() {
        return mailAccountId;
    }

    public void setMailAccountId(Integer mailAccountId) {
        this.mailAccountId = mailAccountId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMailId() {
        return mailId;
    }

    public void setMailId(Integer mailId) {
        this.mailId = mailId;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

}
