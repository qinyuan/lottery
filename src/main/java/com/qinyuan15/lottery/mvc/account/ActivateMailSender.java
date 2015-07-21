package com.qinyuan15.lottery.mvc.account;

import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.dao.*;
import com.qinyuan15.utils.mail.SimpleMailSender;

/**
 * Class to send activate mail after registering
 * Created by qinyuan on 15-7-1.
 */
public class ActivateMailSender {
    private final String activateUrl;

    public ActivateMailSender(String activateUrl) {
        if (activateUrl.contains("?")) {
            if (activateUrl.endsWith("?")) {
                this.activateUrl = activateUrl;
            } else {
                this.activateUrl = activateUrl + "&";
            }
        } else {
            this.activateUrl = activateUrl + "?";
        }
    }

    public void send(Integer userId) {
        User user = new UserDao().getInstance(userId);
        if (user == null) {
            throw new RuntimeException("User is not exists");
        }

        ActivateRequestDao requestDao = new ActivateRequestDao();
        MailSerialKey mailSerialKey = requestDao.getInstanceByUserId(userId);
        if (mailSerialKey == null) { // if request is not added, just add it
            Integer requestId = requestDao.add(userId);
            mailSerialKey = requestDao.getInstance(requestId);
        }

        MailAccount mailAccount = new MailAccountDao().getInstance(AppConfig.getActivateMailAccountId());
        if (mailAccount == null) {
            throw new RuntimeException("No activate mail account configured");
        }
        SimpleMailSender mailSender = new SimpleMailSender(mailAccount.getHost(), mailAccount.getUsername(), mailAccount.getPassword());

        mailSender.send(user.getEmail(), getSubject(), getContent(user.getUsername(), mailSerialKey.getSerialKey()));
    }

    private String getSubject() {
        return AppConfig.getActivateMailSubjectTemplate();
    }

    private String getContent(String username, String serialKey) {
        String template = AppConfig.getActivateMailContentTemplate();
        template = template.replace("{{user}}", username);

        String url = activateUrl + "serial=" + serialKey;
        url = "<a href='" + url + "' target='_blank'>" + url + "</a>";
        template = template.replace("{{url}}", url);
        return template;
    }
}
