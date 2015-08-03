package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.mail.MailSerialKeyDao;

public class ResetPasswordRequestDao extends MailSerialKeyDao {
    @Override
    protected String getMailType() {
        return "resetPassword";
    }

    @Override
    protected int getExpireSeconds() {
        return 30 * 60;// 30 minutes
    }
}
