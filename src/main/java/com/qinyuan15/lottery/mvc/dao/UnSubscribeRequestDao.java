package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.contact.mail.MailSerialKeyDao;

public class UnSubscribeRequestDao extends MailSerialKeyDao {
    public UnSubscribeRequestDao() {
        super("unSubscribe", 3600 * 365/*one year*/);
    }
}
