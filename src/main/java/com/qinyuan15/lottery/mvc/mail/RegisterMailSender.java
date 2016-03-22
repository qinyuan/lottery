package com.qinyuan15.lottery.mvc.mail;

import com.qinyuan.lib.contact.mail.MailSenderBuilder;
import com.qinyuan15.lottery.mvc.config.AppConfig;

public class RegisterMailSender {
    private final static String SERIAL_KEY_URL = AppConfig.props.getAppHost() + "register.html?";

    public void send(String email, String serialKey) {
        SerialKeyMailPlaceholderConverter placeholderConverter = new SerialKeyMailPlaceholderConverter(
                SerialKeyMailPlaceholderConverter.USER_PLACEHOLDER/* do not replace {{user}} */, SERIAL_KEY_URL, serialKey);

        String subject = placeholderConverter.convert(AppConfig.mail.getRegisterMailSubjectTemplate());
        String content = placeholderConverter.convert(AppConfig.mail.getRegisterMailContentTemplate());

        new MailSenderBuilder().build(AppConfig.mail.getRegisterMailAccountId()).send(email, subject, content);
    }
}
