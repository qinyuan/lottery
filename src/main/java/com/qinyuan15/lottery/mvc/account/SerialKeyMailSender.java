package com.qinyuan15.lottery.mvc.account;

import com.qinyuan15.lottery.mvc.dao.*;
import com.qinyuan15.utils.mail.SimpleMailSender;

abstract class SerialKeyMailSender {
    private final String serialKeyUrl;

    public SerialKeyMailSender(String serialKeyUrl) {
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

    public void send(Integer userId) {
        User user = new UserDao().getInstance(userId);
        if (user == null) {
            throw new RuntimeException("User is not exists");
        }

        MailSerialKeyDao mailSerialKeyDao = getMailSerialKeyDao();
        MailSerialKey mailSerialKey = mailSerialKeyDao.getInstanceByUserId(userId);
        if (mailSerialKey == null) { // if request is not added, just add it
            Integer requestId = mailSerialKeyDao.add(userId);
            mailSerialKey = mailSerialKeyDao.getInstance(requestId);
        }

        MailAccount mailAccount = getMailAccount();
        SimpleMailSender mailSender = new SimpleMailSender(mailAccount.getHost(), mailAccount.getUsername(), mailAccount.getPassword());

        mailSender.send(user.getEmail(), getSubjectTemplate(), getContent(user.getUsername(), mailSerialKey.getSerialKey()));
    }

    protected abstract MailAccount getMailAccount();

    protected abstract MailSerialKeyDao getMailSerialKeyDao();

    protected abstract String getSubjectTemplate();

    protected abstract String getContentTemplate();

    private String getContent(String username, String serialKey) {
        String template = getContentTemplate();
        template = template.replace("{{user}}", username);

        String url = serialKeyUrl + "serial=" + serialKey;
        url = "<a href='" + url + "' target='_blank'>" + url + "</a>";
        template = template.replace("{{url}}", url);
        return template;
    }
}
