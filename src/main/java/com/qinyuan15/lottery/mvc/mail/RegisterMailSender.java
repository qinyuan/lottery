package com.qinyuan15.lottery.mvc.mail;

import com.qinyuan.lib.contact.mail.MailSenderBuilder;
import com.qinyuan15.lottery.mvc.config.AppConfig;

public class RegisterMailSender {
    private final static String SERIAL_KEY_URL = AppConfig.properties.getAppHost() + "register.html?";

    public void send(String email, String serialKey) {
        SerialKeyMailPlaceholderConverter placeholderConverter = new SerialKeyMailPlaceholderConverter(
                SerialKeyMailPlaceholderConverter.USER_PLACEHOLDER/* do not replace {{user}} */, SERIAL_KEY_URL, serialKey);

        String subject = placeholderConverter.convert(AppConfig.getRegisterMailSubjectTemplate());
        String content = placeholderConverter.convert(AppConfig.getRegisterMailContentTemplate());

        new MailSenderBuilder().build(AppConfig.getRegisterMailAccountId()).send(email, subject, content);
    }
}
