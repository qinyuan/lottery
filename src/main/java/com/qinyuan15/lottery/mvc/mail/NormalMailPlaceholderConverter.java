package com.qinyuan15.lottery.mvc.mail;

public class NormalMailPlaceholderConverter implements MailPlaceholderConverter {
    public final static String USER_PLACEHOLDER = "{{user}}";
    private final String username;

    NormalMailPlaceholderConverter(String username) {
        this.username = username;
    }

    public String convert(String content) {
        content = content.replace(USER_PLACEHOLDER, username);
        return content;
    }
}
