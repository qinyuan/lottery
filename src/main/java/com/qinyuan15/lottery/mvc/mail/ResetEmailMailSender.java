package com.qinyuan15.lottery.mvc.mail;

import com.qinyuan.lib.contact.mail.MailSerialKeyDao;
import com.qinyuan.lib.network.url.UrlUtils;
import com.qinyuan15.lottery.mvc.config.AppConfig;
import com.qinyuan15.lottery.mvc.dao.ResetEmailRequestDao;
import com.qinyuan15.lottery.mvc.dao.User;
import org.apache.commons.lang3.StringUtils;

/**
 * Class to send reset password mail
 * Created by qinyuan on 15-7-1.
 */
public class ResetEmailMailSender extends SerialKeyMailSender {
    public final static String PREFIX_END = ",,,";
    private final static String SERIAL_KEY_URL = AppConfig.props.getAppHost() + "reset-email.html";
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
        return AppConfig.mail.getResetEmailMailAccountId();
    }

    @Override
    protected MailSerialKeyDao getMailSerialKeyDao() {
        return new ResetEmailRequestDao();
    }

    @Override
    protected String getSubjectTemplate() {
        return AppConfig.mail.getResetEmailMailSubjectTemplate();
    }

    @Override
    protected String getContentTemplate() {
        return AppConfig.mail.getResetEmailMailContentTemplate();
    }

    /**
     * parse new email from serial key
     *
     * @param serialKey serial key
     * @return email
     */
    public static String parseNewEmail(String serialKey) {
        if (StringUtils.isBlank(serialKey)) {
            return null;
        }
        serialKey = UrlUtils.decode(serialKey);
        int endIndex = serialKey.indexOf(ResetEmailMailSender.PREFIX_END);
        return endIndex > 0 ? serialKey.substring(0, endIndex) : null;
    }
}
