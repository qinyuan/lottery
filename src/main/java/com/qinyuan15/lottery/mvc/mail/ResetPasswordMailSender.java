package com.qinyuan15.lottery.mvc.mail;

import com.qinyuan.lib.contact.mail.MailSerialKeyDao;
import com.qinyuan15.lottery.mvc.config.AppConfig;
import com.qinyuan15.lottery.mvc.dao.ResetPasswordRequestDao;

/**
 * Class to send reset passsword mail
 * Created by qinyuan on 15-7-1.
 */
public class ResetPasswordMailSender extends SerialKeyMailSender {
    private final static String SERIAL_KEY_URL = AppConfig.props.getAppHost() + "reset-password.html";

    public ResetPasswordMailSender() {
        super(SERIAL_KEY_URL);
    }

    @Override
    protected int getMailAccountId() {
        return AppConfig.mail.getResetPasswordMailAccountId();
    }

    @Override
    protected MailSerialKeyDao getMailSerialKeyDao() {
        return new ResetPasswordRequestDao();
    }

    @Override
    protected String getSubjectTemplate() {
        return AppConfig.mail.getResetPasswordMailSubjectTemplate();
    }

    @Override
    protected String getContentTemplate() {
        return AppConfig.mail.getResetPasswordMailContentTemplate();
    }
}
