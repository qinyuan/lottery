package com.qinyuan15.lottery.mvc;

import com.qinyuan15.utils.IntegerUtils;
import com.qinyuan15.utils.config.AppConfigDao;
import com.qinyuan15.utils.file.ClasspathFileUtils;

/**
 * Application Configuration
 * Created by qinyuan on 15-6-16.
 */
public class AppConfig {
    public static String getAppHost() {
        String appHost = ClasspathFileUtils.getProperties("global-config.properties").getProperty("appHost");
        if (!appHost.endsWith("/")) {
            appHost += "/";
        }
        return appHost;
    }

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

    private final static String INDEX_HEADER_SLOGAN_KEY = "indexHeaderSlogan";

    public static String getIndexHeaderSlogan() {
        return dao.get(INDEX_HEADER_SLOGAN_KEY);
    }

    public static void updateIndexHeaderSlogan(String indexHeaderSlogan) {
        dao.save(INDEX_HEADER_SLOGAN_KEY, indexHeaderSlogan);
    }

    private final static String INDEX_IMAGE_CYCLE_INTERVAL_KEY = "indexImageCycleInterval";
    private final static int DEFAULT_INDEX_IMAGE_CYCLE_INTERVAL = 10;

    public static int getIndexImageCycleInterval() {
        String string = dao.get(INDEX_IMAGE_CYCLE_INTERVAL_KEY);
        return IntegerUtils.isPositive(string) ? Integer.parseInt(string) : DEFAULT_INDEX_IMAGE_CYCLE_INTERVAL;
    }

    public static void updateIndexImageCycleInterval(int indexImageCycleInterval) {
        dao.save(INDEX_IMAGE_CYCLE_INTERVAL_KEY, String.valueOf(indexImageCycleInterval));
    }

    private final static String FOOTER_POSTER_KEY = "footerPoster";

    public static String getFooterPoster() {
        return dao.get(FOOTER_POSTER_KEY);
    }

    public static void updateFooterPoster(String footerPoster) {
        dao.save(FOOTER_POSTER_KEY, footerPoster);
    }

    private final static String FOOTER_TEXT_KEY = "footerText";

    public static String getFooterText() {
        return dao.get(FOOTER_TEXT_KEY);
    }

    public static void updateFooterText(String footerText) {
        dao.save(FOOTER_TEXT_KEY, footerText);
    }

    private final static String COMMODITY_HEADER_LEFT_LOGO_KEY = "commodityHeaderLeftLogo";

    public static String getCommodityHeaderLeftLogo() {
        return dao.get(COMMODITY_HEADER_LEFT_LOGO_KEY);
    }

    public static void updateCommodityHeaderLeftLogo(String commodityHeaderLeftLogo) {
        dao.save(COMMODITY_HEADER_LEFT_LOGO_KEY, commodityHeaderLeftLogo);
    }

    private final static String FAVICON_KEY = "favicon";

    public static String getFavicon() {
        return dao.get(FAVICON_KEY);
    }

    public static void updateFavicon(String favicon) {
        dao.save(FAVICON_KEY, favicon);
    }

    private final static String SINA_WEIBO_TITLE_KEY = "sinaWeiboTitle";

    public static String getSinaWeiboTitle() {
        return dao.get(SINA_WEIBO_TITLE_KEY);
    }

    public static void updateSinaWeiboTitle(String title) {
        dao.save(SINA_WEIBO_TITLE_KEY, title);
    }

    private final static String SINA_WEIBO_INCLUDE_PICTURE_KEY = "sinaWeiboIncludePicture";

    public static Boolean getSinaWeiboIncludePicture() {
        return dao.getBoolean(SINA_WEIBO_INCLUDE_PICTURE_KEY);
    }

    public static void updateSinaWeiboIncludePicture(Boolean includePicture) {
        dao.saveBoolean(SINA_WEIBO_INCLUDE_PICTURE_KEY, includePicture);
    }

    private final static String QQ_TITLE_KEY = "qqTitle";

    public static String getQQTitle() {
        return dao.get(QQ_TITLE_KEY);
    }

    public static void updateQQTitle(String title) {
        dao.save(QQ_TITLE_KEY, title);
    }

    private final static String QQ_SUMMARY_KEY = "qqSummary";

    public static String getQQSummary() {
        return dao.get(QQ_SUMMARY_KEY);
    }

    public static void updateQQSummary(String summary) {
        dao.save(QQ_SUMMARY_KEY, summary);
    }

    private final static String QQ_INCLUDE_PICTURE_KEY = "qqIncludePicture";

    public static Boolean getQQIncludePicture() {
        return dao.getBoolean(QQ_INCLUDE_PICTURE_KEY);
    }

    public static void updateQQIncludePicture(Boolean includePicture) {
        dao.saveBoolean(QQ_INCLUDE_PICTURE_KEY, includePicture);
    }

    private final static String QZONE_TITLE_KEY = "qzoneTitle";

    public static String getQZoneTitle() {
        return dao.get(QZONE_TITLE_KEY);
    }

    public static void updateQzoneTitle(String title) {
        dao.save(QZONE_TITLE_KEY, title);
    }

    private final static String QZONE_SUMMARY_KEY = "qzoneSummary";

    public static String getQzoneSummary() {
        return dao.get(QZONE_SUMMARY_KEY);
    }

    public static void updateQzoneSummary(String summary) {
        dao.save(QZONE_SUMMARY_KEY, summary);
    }

    private final static String QZONE_INCLUDE_PICTURE_KEY = "qzoneIncludePicture";

    public static Boolean getQzoneIncludePicture() {
        return dao.getBoolean(QZONE_INCLUDE_PICTURE_KEY);
    }

    public static void updateQzoneIncludePicture(Boolean includePicture) {
        dao.saveBoolean(QZONE_INCLUDE_PICTURE_KEY, includePicture);
    }

    private final static String NEW_LOT_LIVENESS_KEY = "newLotLiveness";

    public static Integer getNewLotLiveness() {
        return dao.getInteger(NEW_LOT_LIVENESS_KEY);
    }

    public static void updateNewLotLiveness(Integer newLotLiveness) {
        dao.saveInteger(NEW_LOT_LIVENESS_KEY, newLotLiveness);
    }

    private final static String ACTIVATE_MAIL_SUBJECT_TEMPLATE_KEY = "activateMailSubjectTemplate";

    public static String getActivateMailSubjectTemplate() {
        return dao.get(ACTIVATE_MAIL_SUBJECT_TEMPLATE_KEY);
    }

    public static void updateActivateMailSubjectTemplate(String template) {
        dao.save(ACTIVATE_MAIL_SUBJECT_TEMPLATE_KEY, template);
    }

    private final static String ACTIVATE_MAIL_CONTENT_TEMPLATE_KEY = "activateMailContentTemplate";

    public static String getActivateMailContentTemplate() {
        return dao.get(ACTIVATE_MAIL_CONTENT_TEMPLATE_KEY);
    }

    public static void updateActivateMailContentTemplate(String template) {
        dao.save(ACTIVATE_MAIL_CONTENT_TEMPLATE_KEY, template);
    }

    private final static String ACTIVATE_MAIL_ACCOUNT_KEY = "activateMailAccount";
    private final static String ACTIVATE_MAIL_ACCOUNT_SEPARATOR = ",,,";

    public static ActivateMailAccount getActivateMailAccount() {
        String value = dao.get(ACTIVATE_MAIL_ACCOUNT_KEY);
        if (value == null) {
            return null;
        }
        String[] values = dao.get(ACTIVATE_MAIL_ACCOUNT_KEY).split(ACTIVATE_MAIL_ACCOUNT_SEPARATOR);
        if (values.length < 3) {
            return null;
        }

        return new ActivateMailAccount(values[0], values[1], values[2]);
    }

    public static void updateActivateMailAccount(String host, String username, String password) {
        if (host == null) {
            host = "";
        }
        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }
        dao.save(ACTIVATE_MAIL_ACCOUNT_KEY, host + ACTIVATE_MAIL_ACCOUNT_SEPARATOR +
                username + ACTIVATE_MAIL_ACCOUNT_SEPARATOR + password);
    }

    public static class ActivateMailAccount {
        private final String host;
        private final String username;
        private final String password;

        private ActivateMailAccount(String host, String username, String password) {
            this.host = host;
            this.username = username;
            this.password = password;
        }

        public String getHost() {
            return host;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }
}
