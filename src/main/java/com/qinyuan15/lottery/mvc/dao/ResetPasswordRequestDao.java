package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.mail.MailSerialKeyDao;

public class ResetPasswordRequestDao extends MailSerialKeyDao {
    public ResetPasswordRequestDao() {
        super("resetPassword", 30 * 60/*30 minutes*/);
    }
}
