package com.qinyuan15.lottery.mvc.activate;

import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.dao.ActivateRequest;
import com.qinyuan15.lottery.mvc.dao.ActivateRequestDao;
import com.qinyuan15.lottery.mvc.dao.User;
import com.qinyuan15.lottery.mvc.dao.UserDao;
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
        ActivateRequest activateRequest = requestDao.getInstanceByUserId(userId);
        if (activateRequest == null) { // if request is not added, just add it
            Integer requestId = requestDao.add(userId);
            activateRequest = requestDao.getInstance(requestId);
        }

        AppConfig.ActivateMailAccount mailAccount = AppConfig.getActivateMailAccount();
        SimpleMailSender mailSender = new SimpleMailSender(mailAccount.getHost(), mailAccount.getUsername(), mailAccount.getPassword());

        mailSender.send(user.getEmail(), getSubject(), getContent(user.getUsername(), activateRequest.getSerialKey()));
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
