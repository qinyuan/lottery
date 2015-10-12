package com.qinyuan15.lottery.mvc.mail;

/**
 * adapt serial key url to standard format, which must end with '?' or '&'
 * Created by qinyuan on 15-10-13.
 */
class SerialKeyUrlAdapter {
    String adapt(String serialKeyUrl) {
        if (serialKeyUrl.contains("?")) {
            if (serialKeyUrl.endsWith("?") || serialKeyUrl.endsWith("&")) {
                return serialKeyUrl;
            } else {
                return serialKeyUrl + "&";
            }
        } else {
            return serialKeyUrl + "?";
        }
    }
}
