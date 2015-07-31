package com.qinyuan15.lottery.mvc.mail;

import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.dao.ResetEmailRequestDao;
import com.qinyuan15.lottery.mvc.dao.User;
import com.qinyuan15.utils.mail.MailSerialKeyDao;

/**
 * Class to send reset passsword mail
 * Created by qinyuan on 15-7-1.
 */
public class ResetEmailMailSender extends SerialKeyMailSender {
    public final static String PREFIX_END = ",,,";
    private final static String SERIAL_KEY_URL = AppConfig.getAppHost() + "reset-email.html";
    private final String newEmail;

    public ResetEmailMailSender(String newEmail) {
        super(SERIAL_KEY_URL, newEmail + PREFIX_END);
        this.newEmail = newEmail;
    }

    @Override
    protected String getEmail(User user) {
        return newEmail;
    }

    @Override
    protected int getMailAccountId() {
        return AppConfig.getResetEmailMailAccountId();
    }

    @Override
    protected MailSerialKeyDao getMailSerialKeyDao() {
        return new ResetEmailRequestDao();
    }

    @Override
    protected String getSubjectTemplate() {
        return AppConfig.getResetEmailMailSubjectTemplate();
    }

    @Override
    protected String getContentTemplate() {
        return AppConfig.getResetEmailMailContentTemplate();
    }
}
