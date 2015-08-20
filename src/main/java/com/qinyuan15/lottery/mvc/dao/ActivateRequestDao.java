package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.contact.mail.MailSerialKeyDao;

public class ActivateRequestDao extends MailSerialKeyDao {
    public ActivateRequestDao() {
        super("activateAccount", 24 * 3600/*24 hours*/);
    }
}
