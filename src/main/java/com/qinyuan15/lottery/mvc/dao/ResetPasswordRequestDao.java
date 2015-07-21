package com.qinyuan15.lottery.mvc.dao;

public class ResetPasswordRequestDao extends MailSerialKeyDao {
    @Override
    protected String getMailType() {
        return "resetPassword";
    }
}
