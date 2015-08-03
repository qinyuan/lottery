package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.mail.MailSerialKeyDao;

public class ActivateRequestDao extends MailSerialKeyDao {
    @Override
    protected String getMailType() {
        return "activateAccount";
    }

    @Override
    protected int getExpireSeconds() {
        return 24 * 3600; // 24 hours
    }
}
