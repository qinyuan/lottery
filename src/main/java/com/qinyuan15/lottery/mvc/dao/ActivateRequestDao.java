package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.mail.MailSerialKeyDao;

public class ActivateRequestDao extends MailSerialKeyDao {
    public ActivateRequestDao() {
        super("activateAccount", 24 * 3600/*24 hours*/);
    }
}
