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

    /////////////////////// index header left logo start //////////////////////////
    private final static String INDEX_HEADER_LEFT_LOGO_KEY = "indexHeaderLeftLogo";

    public static String getIndexHeaderLeftLogo() {
        return dao.get(INDEX_HEADER_LEFT_LOGO_KEY);
    }

    public static void updateIndexHeaderLeftLogo(String indexHeaderLeftLogo) {
        dao.save(INDEX_HEADER_LEFT_LOGO_KEY, indexHeaderLeftLogo);
    }
    /////////////////////// index header left logo end //////////////////////////

    /////////////////////// index header right logo start //////////////////////////
    private final static String INDEX_HEADER_RIGHT_LOGO_KEY = "indexHeaderRightLogo";

    public static String getIndexHeaderRightLogo() {
        return dao.get(INDEX_HEADER_RIGHT_LOGO_KEY);
    }

    public static void updateIndexHeaderRightLogo(String indexHeaderRightLogo) {
        dao.save(INDEX_HEADER_RIGHT_LOGO_KEY, indexHeaderRightLogo);
    }
    /////////////////////// index header right logo end //////////////////////////

    /////////////////////// index header slogan start ////////////////////////////
    private final static String INDEX_HEADER_SLOGAN_KEY = "indexHeaderSlogan";

    public static String getIndexHeaderSlogan() {
        return dao.get(INDEX_HEADER_SLOGAN_KEY);
    }

    public static void updateIndexHeaderSlogan(String indexHeaderSlogan) {
        dao.save(INDEX_HEADER_SLOGAN_KEY, indexHeaderSlogan);
    }
    /////////////////////// index header slogan end ////////////////////////////

    /////////////////////// index image cycle interval start ///////////////////
    private final static String INDEX_IMAGE_CYCLE_INTERVAL_KEY = "indexImageCycleInterval";
    private final static int DEFAULT_INDEX_IMAGE_CYCLE_INTERVAL = 10;

    public static int getIndexImageCycleInterval() {
        String string = dao.get(INDEX_IMAGE_CYCLE_INTERVAL_KEY);
        return IntegerUtils.isPositive(string) ? Integer.parseInt(string) : DEFAULT_INDEX_IMAGE_CYCLE_INTERVAL;
    }

    public static void updateIndexImageCycleInterval(int indexImageCycleInterval) {
        dao.save(INDEX_IMAGE_CYCLE_INTERVAL_KEY, String.valueOf(indexImageCycleInterval));
    }
    ////////////////////// index image cycle interval end ///////////////////////

    ////////////////////// footer poster start //////////////////////////////
    private final static String FOOTER_POSTER_KEY = "footerPoster";

    public static String getFooterPoster() {
        return dao.get(FOOTER_POSTER_KEY);
    }

    public static void updateFooterPoster(String footerPoster) {
        dao.save(FOOTER_POSTER_KEY, footerPoster);
    }
    ////////////////////// footer poster end ///////////////////////////////

    ////////////////////// footer text start /////////////////////////////////
    private final static String FOOTER_TEXT_KEY = "footerText";

    public static String getFooterText() {
        return dao.get(FOOTER_TEXT_KEY);
    }

    public static void updateFooterText(String footerText) {
        dao.save(FOOTER_TEXT_KEY, footerText);
    }
    ////////////////////// footer text end /////////////////////////////////

    ////////////////////// commodity header left logo start ///////////////////////////
    private final static String COMMODITY_HEADER_LEFT_LOGO_KEY = "commodityHeaderLeftLogo";

    public static String getCommodityHeaderLeftLogo() {
        return dao.get(COMMODITY_HEADER_LEFT_LOGO_KEY);
    }

    public static void updateCommodityHeaderLeftLogo(String commodityHeaderLeftLogo) {
        dao.save(COMMODITY_HEADER_LEFT_LOGO_KEY, commodityHeaderLeftLogo);
    }
    ////////////////////// commodity header left logo end ///////////////////////////

    /////////////////////////////// favicon start ////////////////////////////////////
    private final static String FAVICON_KEY = "favicon";

    public static String getFavicon() {
        return dao.get(FAVICON_KEY);
    }

    public static void updateFavicon(String favicon) {
        dao.save(FAVICON_KEY, favicon);
    }
    ////////////////////////////// favicon end /////////////////////////////////////

    ////////////////////////////// sina weibo share link start /////////////////////////////
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
    ///////////////////////////// sina weibo share link end ////////////////////////////////

    ///////////////////////////// qq share link start //////////////////////////////
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
    ////////////////////////// qq share link end /////////////////////////////////

    ////////////////////////// qzone share link start //////////////////////////////
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
    ///////////////////////// qzone share link end ///////////////////////////////

    ///////////////////////// new lot liveness start ///////////////////////////////
    private final static String NEW_LOT_LIVENESS_KEY = "newLotLiveness";

    public static Integer getNewLotLiveness() {
        return dao.getInteger(NEW_LOT_LIVENESS_KEY);
    }

    public static void updateNewLotLiveness(Integer newLotLiveness) {
        dao.saveInteger(NEW_LOT_LIVENESS_KEY, newLotLiveness);
    }
    ///////////////////////// new lot liveness end ///////////////////////////////

    ///////////////////////// share succeed liveness start /////////////////////
    private final static String SHARE_SUCCEED_LIVENESS_KEY = "shareSucceedLiveness";

    public static Integer getShareSucceedLiveness() {
        return dao.getInteger(SHARE_SUCCEED_LIVENESS_KEY);
    }

    public static void updateShareSucceedLiveness(Integer shareSucceedLiveness) {
        dao.saveInteger(SHARE_SUCCEED_LIVENESS_KEY, shareSucceedLiveness);
    }
    ///////////////////////// share succeed liveness end /////////////////////

    ///////////////////////////////////// activate mail start ///////////////////////////////////
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

    private final static String ACTIVATE_MAIL_ACCOUNT_ID_KEY = "activateMailAccountId";

    public static Integer getActivateMailAccountId() {
        return dao.getInteger(ACTIVATE_MAIL_ACCOUNT_ID_KEY);
    }

    public static void updateActivateMailAccountId(Integer mailAccountId) {
        dao.saveInteger(ACTIVATE_MAIL_ACCOUNT_ID_KEY, mailAccountId);
    }
    ///////////////////////////////////// activate mail end /////////////////////////////////////

    //////////////////////////////// reset password mail start //////////////////////////
    private final static String RESET_PASSWORD_MAIL_SUBJECT_TEMPLATE_KEY = "resetPasswordMailSubjectTemplate";

    public static String getResetPasswordMailSubjectTemplate() {
        return dao.get(RESET_PASSWORD_MAIL_SUBJECT_TEMPLATE_KEY);
    }

    public static void updateResetPasswordMailSubjectTemplate(String template) {
        dao.save(RESET_PASSWORD_MAIL_SUBJECT_TEMPLATE_KEY, template);
    }

    private final static String RESET_PASSWORD_MAIL_CONTENT_TEMPLATE_KEY = "resetPasswordMailContentTemplate";

    public static String getResetPasswordMailContentTemplate() {
        return dao.get(RESET_PASSWORD_MAIL_CONTENT_TEMPLATE_KEY);
    }

    public static void updateResetPasswordMailContentTemplate(String template) {
        dao.save(RESET_PASSWORD_MAIL_CONTENT_TEMPLATE_KEY, template);
    }

    private final static String RESET_PASSWORD_MAIL_ACCOUNT_ID_KEY = "resetPasswordMailAccountId";

    public static Integer getResetPasswordMailAccountId() {
        return dao.getInteger(RESET_PASSWORD_MAIL_ACCOUNT_ID_KEY);
    }

    public static void updateResetPasswordMailAccountId(Integer mailAccountId) {
        dao.saveInteger(RESET_PASSWORD_MAIL_ACCOUNT_ID_KEY, mailAccountId);
    }
    //////////////////////////////// reset password mail end /////////////////////////////

    //////////////////////////////// reset email mail start /////////////////////////////
    private final static String RESET_EMAIL_MAIL_SUBJECT_TEMPLATE_KEY = "resetEmailMailSubjectTemplate";

    public static String getResetEmailMailSubjectTemplate() {
        return dao.get(RESET_EMAIL_MAIL_SUBJECT_TEMPLATE_KEY);
    }

    public static void updateResetEmailMailSubjectTemplate(String template) {
        dao.save(RESET_EMAIL_MAIL_SUBJECT_TEMPLATE_KEY, template);
    }

    private final static String RESET_EMAIL_MAIL_CONTENT_TEMPLATE_KEY = "resetEmailMailContentTemplate";

    public static String getResetEmailMailContentTemplate() {
        return dao.get(RESET_EMAIL_MAIL_CONTENT_TEMPLATE_KEY);
    }

    public static void updateResetEmailMailContentTemplate(String template) {
        dao.save(RESET_EMAIL_MAIL_CONTENT_TEMPLATE_KEY, template);
    }

    private final static String RESET_EMAIL_MAIL_ACCOUNT_ID_KEY = "resetEmailMailAccountId";

    public static Integer getResetEmailMailAccountId() {
        return dao.getInteger(RESET_EMAIL_MAIL_ACCOUNT_ID_KEY);
    }

    public static void updateResetEmailMailAccountId(Integer mailAccountId) {
        dao.saveInteger(RESET_EMAIL_MAIL_ACCOUNT_ID_KEY, mailAccountId);
    }
    ///////////////////////////// reset email mail end /////////////////////////////

    ///////////////////////////// new lottery chance start //////////////////////////////
    private final static String NEW_LOTTERY_CHANCE_MAIL_SUBJECT_TEMPLATE_KEY = "newLotteryChanceMailSubjectTemplate";

    public static String getNewLotteryChanceMailSubjectTemplate() {
        return dao.get(NEW_LOTTERY_CHANCE_MAIL_SUBJECT_TEMPLATE_KEY);
    }

    public static void updateNewLotteryChanceMailSubjectTemplate(String template) {
        dao.save(NEW_LOTTERY_CHANCE_MAIL_SUBJECT_TEMPLATE_KEY, template);
    }

    private final static String NEW_LOTTERY_CHANCE_MAIL_CONTENT_TEMPLATE_KEY = "newLotteryChanceMailContentTemplate";

    public static String getNewLotteryChanceMailContentTemplate() {
        return dao.get(NEW_LOTTERY_CHANCE_MAIL_CONTENT_TEMPLATE_KEY);
    }

    public static void updateNewLotteryChanceMailContentTemplate(String template) {
        dao.save(NEW_LOTTERY_CHANCE_MAIL_CONTENT_TEMPLATE_KEY, template);
    }

    private final static String NEW_LOTTERY_CHANCE_MAIL_ACCOUNT_ID_KEY = "newLotteryChanceMailAccountId";

    public static Integer getNewLotteryChanceMailAccountId() {
        return dao.getInteger(NEW_LOTTERY_CHANCE_MAIL_ACCOUNT_ID_KEY);
    }

    public static void updateNewLotteryChanceMailAccountId(String template) {
        dao.save(NEW_LOTTERY_CHANCE_MAIL_ACCOUNT_ID_KEY, template);
    }
    ///////////////////////////// new lottery chance end //////////////////////////////

    ///////////////////////////// lottery rule start ///////////////////////////////
    private final static String LOTTERY_RULE_KEY = "lotteryRule";

    public static String getLotteryRule() {
        return dao.get(LOTTERY_RULE_KEY);
    }

    public static void updateLotteryRule(String lotteryRule) {
        dao.save(LOTTERY_RULE_KEY, lotteryRule);
    }
    ///////////////////////////// lottery rule end ///////////////////////////////
}
