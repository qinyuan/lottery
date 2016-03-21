package com.qinyuan15.lottery.mvc.config;

import com.qinyuan.lib.lang.file.ClasspathFileUtils;
import com.qinyuan.lib.network.url.UrlUtils;

import java.util.Map;
import java.util.Properties;

public class PropertiesConfig {
    public final static String GLOBAL_CONFIG_PROPS_FILE = "global-config.properties";
    public final static String QQ_CONNECT_CONFIG_PROPS_FILE = "qqconnectconfig.properties";
    private final static Map<String, String> GLOBAL_CONFIG = ClasspathFileUtils.getPropertyMap(GLOBAL_CONFIG_PROPS_FILE);
    private final static Properties QQ_CONNECT_CONFIG = ClasspathFileUtils.getProperties(QQ_CONNECT_CONFIG_PROPS_FILE);

    /**
     * @return url such as http://192.168.8.1:8080/lotery/, which must end with '/'
     */
    public String getAppHost() {
        String appHost = GLOBAL_CONFIG.get("appHost");
        if (!appHost.endsWith("/")) {
            appHost += "/";
        }
        return appHost;
    }

    public String getIndexPageTitle() {
        return GLOBAL_CONFIG.get("indexPageTitle");
    }

    public String getCommodityPageTitle() {
        return GLOBAL_CONFIG.get("commodityPageTitle");
    }

    public boolean isOffline() {
        String offline = GLOBAL_CONFIG.get("offline");
        return offline != null && offline.toLowerCase().trim().equals("true");
    }

    public String getQQConnectAppId() {
        return QQ_CONNECT_CONFIG.getProperty("app_ID").trim();
    }

    public String getQQConnectAppKey() {
        return QQ_CONNECT_CONFIG.getProperty("app_KEY").trim();
    }

    public String getQQConnectRedirectURI() {
        return UrlUtils.encode(QQ_CONNECT_CONFIG.getProperty("redirect_URI").trim());
    }

    public String getQQConnectScope() {
        return QQ_CONNECT_CONFIG.getProperty("scope").trim();
    }

    public String getQQConnectAuthenticateUrl() {
        return "https://graph.qq.com/oauth2.0/authorize?response_type=token&client_id=" + getQQConnectAppId()
                + "&redirect_uri=" + getQQConnectRedirectURI() + "&scope=" + UrlUtils.encode(getQQConnectScope());
    }
}
