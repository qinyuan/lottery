package com.qinyuan15.lottery.mvc.mail;

import com.qinyuan.lib.contact.mail.MailSenderBuilder;
import com.qinyuan.lib.contact.mail.MailSerialKey;
import com.qinyuan.lib.contact.mail.MailSerialKeyDao;
import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.lang.time.DateUtils;
import com.qinyuan15.lottery.mvc.dao.User;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import org.apache.commons.lang3.StringUtils;

/**
 * class to send email with serial key
 */
abstract class SerialKeyMailSender implements MailSender {
    private final String serialKeyUrl;
    private final String serialKeyPrefix;

    public SerialKeyMailSender(String serialKeyUrl, String serialKeyPrefix) {
        this.serialKeyUrl = new SerialKeyUrlAdapter().adapt(serialKeyUrl);
        this.serialKeyPrefix = serialKeyPrefix;
    }

    public SerialKeyMailSender(String serialKeyUrl) {
        this(serialKeyUrl, "");
    }

    public void send(Integer userId) {
        // validate user
        if (userId == null) {
            throw new IllegalArgumentException("user id can't be null");
        }
        User user = new UserDao().getInstance(userId);
        if (user == null) {
            throw new IllegalArgumentException("no User with id " + userId);
        }

        // query and validate serial key
        MailSerialKeyDao mailSerialKeyDao = getMailSerialKeyDao();
        MailSerialKey mailSerialKey = mailSerialKeyDao.getInstanceByUserId(userId);
        if (isOldSerialKeyUsable(mailSerialKey)) {
            mailSerialKey.setSendTime(DateUtils.nowString());
            HibernateUtils.update(mailSerialKey);
        } else {
            Integer requestId = mailSerialKeyDao.add(userId, serialKeyPrefix);
            mailSerialKey = mailSerialKeyDao.getInstance(requestId);
        }

        // build email subject and content
        SerialKeyMailPlaceholderConverter placeholderConverter = new SerialKeyMailPlaceholderConverter(
                user.getUsername(), serialKeyUrl, mailSerialKey.getSerialKey());
        String subject = placeholderConverter.convert(getSubjectTemplate());
        String content = placeholderConverter.convert(getContentTemplate());

        // get email
        new MailSenderBuilder().build(getMailAccountId()).send(getEmail(user), subject, content);
    }

    private boolean isOldSerialKeyUsable(MailSerialKey mailSerialKey) {
        if (mailSerialKey == null) {
            return false;
        }

        String serialKeyString = mailSerialKey.getSerialKey();
        return StringUtils.isNotBlank(serialKeyString) &&
                (StringUtils.isBlank(serialKeyPrefix) || serialKeyString.startsWith(serialKeyPrefix));
    }

    protected String getEmail(User user) {
        return user.getEmail();
    }

    protected abstract int getMailAccountId();

    protected abstract MailSerialKeyDao getMailSerialKeyDao();

    protected abstract String getSubjectTemplate();

    protected abstract String getContentTemplate();
}
