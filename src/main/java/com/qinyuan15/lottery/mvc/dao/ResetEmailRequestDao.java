package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.contact.mail.MailSerialKeyDao;

public class ResetEmailRequestDao extends MailSerialKeyDao {
    public ResetEmailRequestDao() {
        super("resetEmail", 30 * 60/*30 minutes*/);
    }
}
