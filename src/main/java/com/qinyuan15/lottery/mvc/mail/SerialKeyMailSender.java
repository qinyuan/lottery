package com.qinyuan15.lottery.mvc.mail;

import com.qinyuan15.lottery.mvc.dao.User;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import com.qinyuan15.utils.mail.MailSenderBuilder;
import com.qinyuan15.utils.mail.MailSerialKey;
import com.qinyuan15.utils.mail.MailSerialKeyDao;

abstract class SerialKeyMailSender {
    private final String serialKeyUrl;
    private final String serialKeyPrefix;

    public SerialKeyMailSender(String serialKeyUrl, String serialKeyPrefix) {
        this.serialKeyPrefix = serialKeyPrefix;
        if (serialKeyUrl.contains("?")) {
            if (serialKeyUrl.endsWith("?")) {
                this.serialKeyUrl = serialKeyUrl;
            } else {
                this.serialKeyUrl = serialKeyUrl + "&";
            }
        } else {
            this.serialKeyUrl = serialKeyUrl + "?";
        }
    }

    public SerialKeyMailSender(String serialKeyUrl) {
        this(serialKeyUrl, "");
    }

    public void send(Integer userId) {
        User user = new UserDao().getInstance(userId);
        if (user == null) {
            throw new RuntimeException("User is not exists");
        }

        MailSerialKeyDao mailSerialKeyDao = getMailSerialKeyDao();
        MailSerialKey mailSerialKey = mailSerialKeyDao.getInstanceByUserId(userId);
        if (mailSerialKey == null) { // if request is not added, just add it
            Integer requestId = mailSerialKeyDao.add(userId, serialKeyPrefix);
            mailSerialKey = mailSerialKeyDao.getInstance(requestId);
        }

        SerialKeyMailPlaceholderConverter placeholderConverter = new SerialKeyMailPlaceholderConverter(
                user.getUsername(), serialKeyUrl, mailSerialKey.getSerialKey());
        String subject = placeholderConverter.convert(getSubjectTemplate());
        String content = placeholderConverter.convert(getContentTemplate());

        new MailSenderBuilder().build(getMailAccountId()).send(getEmail(user), subject, content);
    }

    protected String getEmail(User user) {
        return user.getEmail();
    }

    protected abstract int getMailAccountId();

    protected abstract MailSerialKeyDao getMailSerialKeyDao();

    protected abstract String getSubjectTemplate();

    protected abstract String getContentTemplate();
}
