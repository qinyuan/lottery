package com.qinyuan15.lottery.mvc.mail;

import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.dao.ResetPasswordRequestDao;
import com.qinyuan15.utils.mail.MailSerialKeyDao;

/**
 * Class to send reset passsword mail
 * Created by qinyuan on 15-7-1.
 */
public class ResetPasswordMailSender extends SerialKeyMailSender {
    private final static String SERIAL_KEY_URL = AppConfig.getAppHost() + "reset-password.html";

    public ResetPasswordMailSender() {
        super(SERIAL_KEY_URL);
    }

    @Override
    protected int getMailAccountId() {
        return AppConfig.getResetPasswordMailAccountId();
    }

    @Override
    protected MailSerialKeyDao getMailSerialKeyDao() {
        return new ResetPasswordRequestDao();
    }

    @Override
    protected String getSubjectTemplate() {
        return AppConfig.getResetPasswordMailSubjectTemplate();
    }

    @Override
    protected String getContentTemplate() {
        return AppConfig.getResetPasswordMailContentTemplate();
    }
}
