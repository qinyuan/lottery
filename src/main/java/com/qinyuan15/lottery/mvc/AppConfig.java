package com.qinyuan15.lottery.mvc;

import com.qinyuan15.utils.config.AppConfigDao;

/**
 * Application Configuration
 * Created by qinyuan on 15-6-16.
 */
public class AppConfig {
    private final static AppConfigDao dao = new AppConfigDao();

    private final static String INDEX_HEADER_LEFT_LOGO_KEY = "indexHeaderLeftLogo";

    public static String getIndexHeaderLeftLogo() {
        return dao.get(INDEX_HEADER_LEFT_LOGO_KEY);
    }

    public static void updateIndexHeaderLeftLogo(String indexHeaderLeftLogo) {
        dao.save(INDEX_HEADER_LEFT_LOGO_KEY, indexHeaderLeftLogo);
    }

    private final static String INDEX_HEADER_RIGHT_LOGO_KEY = "indexHeaderRightLogo";

    public static String getIndexHeaderRightLogo() {
        return dao.get(INDEX_HEADER_RIGHT_LOGO_KEY);
    }

    public static void updateIndexHeaderRightLogo(String indexHeaderRightLogo) {
        dao.save(INDEX_HEADER_RIGHT_LOGO_KEY, indexHeaderRightLogo);
    }

    private final static String INDEX_HEADER_SLOGAN = "indexHeaderSlogan";

    public static String getIndexHeaderSlogan() {
        return dao.get(INDEX_HEADER_SLOGAN);
    }

    public static void updateIndexHeaderSlogan(String indexHeaderSlogan) {
        dao.save(INDEX_HEADER_SLOGAN, indexHeaderSlogan);
    }
}
