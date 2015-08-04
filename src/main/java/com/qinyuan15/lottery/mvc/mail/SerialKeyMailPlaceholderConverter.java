package com.qinyuan15.lottery.mvc.mail;

class SerialKeyMailPlaceholderConverter implements MailPlaceholderConverter {
    public final static String USER_PLACEHOLDER = "{{user}}";
    public final static String URL_PLACEHOLDER = "{{url}}";

    private final String username;
    private final String serialKey;
    private final String serialKeyUrl;

    SerialKeyMailPlaceholderConverter(String username, String serialKeyUrl, String serialKey) {
        this.username = username;
        this.serialKeyUrl = serialKeyUrl;
        this.serialKey = serialKey;
    }

    public String convert(String content) {
        content = content.replace(USER_PLACEHOLDER, username);
        String url = serialKeyUrl + "serial=" + serialKey;
        url = "<a href='" + url + "' target='_blank'>" + url + "</a>";
        content = content.replace(URL_PLACEHOLDER, url);
        return content;
    }
}
