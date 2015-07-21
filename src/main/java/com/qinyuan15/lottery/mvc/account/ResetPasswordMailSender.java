package com.qinyuan15.lottery.mvc.account;

import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.dao.MailAccount;
import com.qinyuan15.lottery.mvc.dao.MailAccountDao;
import com.qinyuan15.lottery.mvc.dao.MailSerialKeyDao;
import com.qinyuan15.lottery.mvc.dao.ResetPasswordRequestDao;

/**
 * Class to send reset passsword mail
 * Created by qinyuan on 15-7-1.
 */
public class ResetPasswordMailSender extends SerialKeyMailSender {
    public ResetPasswordMailSender(String resetPasswordUrl) {
        super(resetPasswordUrl);
    }

    @Override
    protected MailAccount getMailAccount() {
        MailAccount mailAccount = new MailAccountDao().getInstance(AppConfig.getResetPasswordMailAccountId());
        if (mailAccount == null) {
            throw new RuntimeException("No reset password mail account configured");
        }
        return mailAccount;
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
