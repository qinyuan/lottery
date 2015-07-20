package com.qinyuan15.lottery.mvc.dao;

public class ActivateRequestDao extends MailSerialKeyDao {
    @Override
    protected String getMailType() {
        return "activateAccount";
    }
}
