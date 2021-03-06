package com.qinyuan15.lottery.mvc.mail;

import com.qinyuan.lib.contact.mail.MailSerialKeyDao;
import com.qinyuan15.lottery.mvc.config.AppConfig;
import com.qinyuan15.lottery.mvc.dao.ActivateRequestDao;

/**
 * Class to send activate mail after registering
 * Created by qinyuan on 15-7-1.
 */
public class ActivateMailSender extends SerialKeyMailSender {
    private final static String SERIAL_KEY_URL = AppConfig.props.getAppHost() + "activate-account.html";

    public ActivateMailSender() {
        super(SERIAL_KEY_URL);
    }

    @Override
    protected int getMailAccountId() {
        return AppConfig.mail.getActivateMailAccountId();
    }

    @Override
    protected MailSerialKeyDao getMailSerialKeyDao() {
        return new ActivateRequestDao();
    }

    @Override
    protected String getSubjectTemplate() {
        return AppConfig.mail.getActivateMailSubjectTemplate();
    }

    @Override
    protected String getContentTemplate() {
        return AppConfig.mail.getActivateMailContentTemplate();
    }
}
