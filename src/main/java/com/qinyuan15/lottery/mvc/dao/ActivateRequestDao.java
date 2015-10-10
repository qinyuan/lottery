package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.contact.mail.MailSerialKeyDao;

public class ActivateRequestDao extends MailSerialKeyDao {
    public final static String MAIL_TYPE = "activateAccount";

    public ActivateRequestDao() {
        super(MAIL_TYPE, 24 * 3600/*24 hours*/);
    }
}
