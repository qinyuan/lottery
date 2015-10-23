package com.qinyuan15.lottery.mvc;

import com.qinyuan.lib.config.AppConfigDao;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.lang.file.ClasspathFileUtils;

import java.util.Properties;

/**
 * Application Configuration
 * Created by qinyuan on 15-6-16.
 */
public class AppConfig {
    public final static String GLOBAL_CONFIG_PROPS_FILE = "global-config.properties";

    /**
     * @return url such as http://192.168.8.1:8080/lotery/, which must end with '/'
     */
    public static String getAppHost() {
        String appHost = ClasspathFileUtils.getProperties(GLOBAL_CONFIG_PROPS_FILE).getProperty("appHost");
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

    ////////////////////////////// lottery sina weibo share link start /////////////////////////////
    private final static String LOTTERY_SINA_WEIBO_TITLE_KEY = "lotterySinaWeiboTitle";

    public static String getLotterySinaWeiboTitle() {
        return dao.get(LOTTERY_SINA_WEIBO_TITLE_KEY);
    }

    public static void updateLotterySinaWeiboTitle(String title) {
        dao.save(LOTTERY_SINA_WEIBO_TITLE_KEY, title);
    }

    private final static String LOTTERY_SINA_WEIBO_INCLUDE_PICTURE_KEY = "lotterySinaWeiboIncludePicture";

    public static Boolean getLotterySinaWeiboIncludePicture() {
        return dao.getBoolean(LOTTERY_SINA_WEIBO_INCLUDE_PICTURE_KEY);
    }

    public static void updateLotterySinaWeiboIncludePicture(Boolean includePicture) {
        dao.saveBoolean(LOTTERY_SINA_WEIBO_INCLUDE_PICTURE_KEY, includePicture);
    }
    ///////////////////////////// lottery sina weibo share link end ////////////////////////////////

    ///////////////////////////// seckill sina weibo share link start //////////////////////////////
    private final static String SECKILL_SINA_WEIBO_TITLE_KEY = "seckillSinaWeiboTitle";

    public static String getSeckillSinaWeiboTitle() {
        return dao.get(SECKILL_SINA_WEIBO_TITLE_KEY);
    }

    public static void updateSeckillSinaWeiboTitle(String title) {
        dao.save(SECKILL_SINA_WEIBO_TITLE_KEY, title);
    }

    private final static String SECKILL_SINA_WEIBO_INCLUDE_PICTURE_KEY = "seckillSinaWeiboIncludePicture";

    public static Boolean getSeckillSinaWeiboIncludePicture() {
        return dao.getBoolean(SECKILL_SINA_WEIBO_INCLUDE_PICTURE_KEY);
    }

    public static void updateSeckillSinaWeiboIncludePicture(Boolean includePicture) {
        dao.saveBoolean(SECKILL_SINA_WEIBO_INCLUDE_PICTURE_KEY, includePicture);
    }
    ///////////////////////////// seckill sina weibo share link end ////////////////////////////////

    ///////////////////////////// lottery qq share link start //////////////////////////////
    private final static String LOTTERY_QQ_TITLE_KEY = "lotteryQQTitle";

    public static String getLotteryQQTitle() {
        return dao.get(LOTTERY_QQ_TITLE_KEY);
    }

    public static void updateLotteryQQTitle(String title) {
        dao.save(LOTTERY_QQ_TITLE_KEY, title);
    }

    private final static String LOTTERY_QQ_SUMMARY_KEY = "lotteryQQSummary";

    public static String getLotteryQQSummary() {
        return dao.get(LOTTERY_QQ_SUMMARY_KEY);
    }

    public static void updateLotteryQQSummary(String summary) {
        dao.save(LOTTERY_QQ_SUMMARY_KEY, summary);
    }

    private final static String LOTTERY_QQ_INCLUDE_PICTURE_KEY = "lotteryQQIncludePicture";

    public static Boolean getLotteryQQIncludePicture() {
        return dao.getBoolean(LOTTERY_QQ_INCLUDE_PICTURE_KEY);
    }

    public static void updateLotteryQQIncludePicture(Boolean includePicture) {
        dao.saveBoolean(LOTTERY_QQ_INCLUDE_PICTURE_KEY, includePicture);
    }
    ////////////////////////// lottery qq share link end /////////////////////////////////

    ////////////////////////// seckill qq share link start ///////////////////////////////
    private final static String SECKILL_QQ_TITLE_KEY = "seckillQQTitle";

    public static String getSeckillQQTitle() {
        return dao.get(SECKILL_QQ_TITLE_KEY);
    }

    public static void updateSeckillQQTitle(String title) {
        dao.save(SECKILL_QQ_TITLE_KEY, title);
    }

    private final static String SECKILL_QQ_SUMMARY_KEY = "seckillQQSummary";

    public static String getSeckillQQSummary() {
        return dao.get(SECKILL_QQ_SUMMARY_KEY);
    }

    public static void updateSeckillQQSummary(String summary) {
        dao.save(SECKILL_QQ_SUMMARY_KEY, summary);
    }

    private final static String SECKILL_QQ_INCLUDE_PICTURE_KEY = "seckillQQIncludePicture";

    public static Boolean getSeckillQQIncludePicture() {
        return dao.getBoolean(SECKILL_QQ_INCLUDE_PICTURE_KEY);
    }

    public static void updateSeckillQQIncludePicture(Boolean includePicture) {
        dao.saveBoolean(SECKILL_QQ_INCLUDE_PICTURE_KEY, includePicture);
    }
    ////////////////////////// seckill qq share link end /////////////////////////////////

    ////////////////////////// lottery qzone share link start //////////////////////////////
    private final static String LOTTERY_QZONE_TITLE_KEY = "lotteryQzoneTitle";

    public static String getLotteryQzoneTitle() {
        return dao.get(LOTTERY_QZONE_TITLE_KEY);
    }

    public static void updateLotteryQzoneTitle(String title) {
        dao.save(LOTTERY_QZONE_TITLE_KEY, title);
    }

    private final static String LOTTERY_QZONE_SUMMARY_KEY = "lotteryQzoneSummary";

    public static String getLotteryQzoneSummary() {
        return dao.get(LOTTERY_QZONE_SUMMARY_KEY);
    }

    public static void updateLotteryQzoneSummary(String summary) {
        dao.save(LOTTERY_QZONE_SUMMARY_KEY, summary);
    }

    private final static String LOTTERY_QZONE_INCLUDE_PICTURE_KEY = "lotteryQzoneIncludePicture";

    public static Boolean getLotteryQzoneIncludePicture() {
        return dao.getBoolean(LOTTERY_QZONE_INCLUDE_PICTURE_KEY);
    }

    public static void updateLotteryQzoneIncludePicture(Boolean includePicture) {
        dao.saveBoolean(LOTTERY_QZONE_INCLUDE_PICTURE_KEY, includePicture);
    }
    ///////////////////////// lottery qzone share link end ///////////////////////////////

    ///////////////////////// seckill qzone share link start //////////////////////////////
    private final static String SECKILL_QZONE_TITLE_KEY = "seckillQzoneTitle";

    public static String getSeckillQzoneTitle() {
        return dao.get(SECKILL_QZONE_TITLE_KEY);
    }

    public static void updateSeckillQzoneTitle(String title) {
        dao.save(SECKILL_QZONE_TITLE_KEY, title);
    }

    private final static String SECKILL_QZONE_SUMMARY_KEY = "seckillQzoneSummary";

    public static String getSeckillQzoneSummary() {
        return dao.get(SECKILL_QZONE_SUMMARY_KEY);
    }

    public static void updateSeckillQzoneSummary(String summary) {
        dao.save(SECKILL_QZONE_SUMMARY_KEY, summary);
    }

    private final static String SECKILL_QZONE_INCLUDE_PICTURE_KEY = "seckillQzoneIncludePicture";

    public static Boolean getSeckillQzoneIncludePicture() {
        return dao.getBoolean(SECKILL_QZONE_INCLUDE_PICTURE_KEY);
    }

    public static void updateSeckillQzoneIncludePicture(Boolean includePicture) {
        dao.saveBoolean(SECKILL_QZONE_INCLUDE_PICTURE_KEY, includePicture);
    }
    ///////////////////////// seckill qzone share link end ////////////////////////////////

    ///////////////////////// share succeed liveness start /////////////////////
    private final static String SHARE_SUCCEED_LIVENESS_KEY = "shareSucceedLiveness";

    public static Integer getShareSucceedLiveness() {
        return dao.getInteger(SHARE_SUCCEED_LIVENESS_KEY);
    }

    public static void updateShareSucceedLiveness(Integer shareSucceedLiveness) {
        dao.saveInteger(SHARE_SUCCEED_LIVENESS_KEY, shareSucceedLiveness);
    }
    ///////////////////////// share succeed liveness end /////////////////////

    ///////////////////////// seckill poker site start ///////////////////////////////
    private final static String SECKILL_POKER_FRONT_SIDE_KEY = "seckillPokerFrontSide";

    public static String getSeckillPokerFrontSide() {
        return dao.get(SECKILL_POKER_FRONT_SIDE_KEY);
    }

    public static void updateSeckillPokerFrontSide(String path) {
        dao.save(SECKILL_POKER_FRONT_SIDE_KEY, path);
    }

    private final static String SECKILL_POKER_BACK_SIDE_KEY = "seckillPokerBackSide";

    public static String getSeckillPokerBackSide() {
        return dao.get(SECKILL_POKER_BACK_SIDE_KEY);
    }

    public static void updateSeckillPokerBackSide(String path) {
        dao.save(SECKILL_POKER_BACK_SIDE_KEY, path);
    }
    ///////////////////////// seckill poker site end ////////////////////////////////

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
        return dao.getPositiveInteger(ACTIVATE_MAIL_ACCOUNT_ID_KEY);
    }

    public static void updateActivateMailAccountId(Integer mailAccountId) {
        dao.saveInteger(ACTIVATE_MAIL_ACCOUNT_ID_KEY, mailAccountId);
    }
    ///////////////////////////////////// activate mail end /////////////////////////////////////

    ///////////////////////////////////// register mail start ////////////////////////////////////
    private final static String REGISTER_MAIL_SUBJECT_TEMPLATE_KEY = "registerMailSubjectTemplate";

    public static String getRegisterMailSubjectTemplate() {
        return dao.get(REGISTER_MAIL_SUBJECT_TEMPLATE_KEY);
    }

    public static void updateRegisterMailSubjectTemplate(String template) {
        dao.save(REGISTER_MAIL_SUBJECT_TEMPLATE_KEY, template);
    }

    private final static String REGISTER_MAIL_CONTENT_TEMPLATE_KEY = "registerMailContentTemplate";

    public static String getRegisterMailContentTemplate() {
        return dao.get(REGISTER_MAIL_CONTENT_TEMPLATE_KEY);
    }

    public static void updateRegisterMailContentTemplate(String template) {
        dao.save(REGISTER_MAIL_CONTENT_TEMPLATE_KEY, template);
    }

    private final static String REGISTER_MAIL_ACCOUNT_ID_KEY = "registerMailAccountId";

    public static Integer getRegisterMailAccountId() {
        return dao.getPositiveInteger(REGISTER_MAIL_ACCOUNT_ID_KEY);
    }

    public static void updateRegisterMailAccountId(Integer mailAccountId) {
        dao.saveInteger(REGISTER_MAIL_ACCOUNT_ID_KEY, mailAccountId);
    }
    ///////////////////////////////////// register mail end //////////////////////////////////////

    ////////////////////////////////// register logo start ///////////////////////////////////
    private final static String REGISTER_HEADER_LEFT_LOGO_KEY = "registerHeaderLeftLogo";

    public static String getRegisterHeaderLeftLogo() {
        return dao.get(REGISTER_HEADER_LEFT_LOGO_KEY);
    }

    public static void updateRegisterHeaderLeftLogo(String registerHeaderLeftLogo) {
        dao.save(REGISTER_HEADER_LEFT_LOGO_KEY, registerHeaderLeftLogo);
    }

    private final static String REGISTER_HEADER_RIGHT_LOGO_KEY = "registerHeaderRightLogo";

    public static String getRegisterHeaderRightLogo() {
        return dao.get(REGISTER_HEADER_RIGHT_LOGO_KEY);
    }

    public static void updateRegisterHeaderRightLogo(String registerHeaderRightLogo) {
        dao.save(REGISTER_HEADER_RIGHT_LOGO_KEY, registerHeaderRightLogo);
    }

    ////////////////////////////////// register logo end /////////////////////////////////////

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
        return dao.getPositiveInteger(RESET_PASSWORD_MAIL_ACCOUNT_ID_KEY);
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
        return dao.getPositiveInteger(RESET_EMAIL_MAIL_ACCOUNT_ID_KEY);
    }

    public static void updateResetEmailMailAccountId(Integer mailAccountId) {
        dao.saveInteger(RESET_EMAIL_MAIL_ACCOUNT_ID_KEY, mailAccountId);
    }
    ///////////////////////////// reset email mail end /////////////////////////////

    ///////////////////////////// new lottery chance start //////////////////////////////
    private final static String REMIND_NEW_LOTTERY_CHANCE_BY_MAIL_KEY = "remindNewLotteryChanceByMail";

    public static Boolean getRemindNewLotteryChanceByMail() {
        return dao.getBoolean(REMIND_NEW_LOTTERY_CHANCE_BY_MAIL_KEY);
    }

    public static void updateRemindNewLotteryChanceByMail(Boolean bool) {
        dao.saveBoolean(REMIND_NEW_LOTTERY_CHANCE_BY_MAIL_KEY, bool);
    }

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
        return dao.getPositiveInteger(NEW_LOTTERY_CHANCE_MAIL_ACCOUNT_ID_KEY);
    }

    public static void updateNewLotteryChanceMailAccountId(Integer accountId) {
        dao.saveInteger(NEW_LOTTERY_CHANCE_MAIL_ACCOUNT_ID_KEY, accountId);
    }

    private final static String REMIND_NEW_LOTTERY_CHANCE_BY_SYSTEM_INFO_KEY = "remindNewLotteryChanceBySystemInfo";

    public static Boolean getRemindNewLotteryChanceBySystemInfo() {
        return dao.getBoolean(REMIND_NEW_LOTTERY_CHANCE_BY_SYSTEM_INFO_KEY);
    }

    public static void updateRemindNewLotteryChanceBySystemInfo(Boolean bool) {
        dao.saveBoolean(REMIND_NEW_LOTTERY_CHANCE_BY_SYSTEM_INFO_KEY, bool);
    }

    private final static String NEW_LOTTERY_CHANCE_SYSTEM_INFO_TEMPLATE_KEY = "newLotteryChanceSystemInfoTemplate";

    public static String getNewLotteryChanceSystemInfoTemplate() {
        return dao.get(NEW_LOTTERY_CHANCE_SYSTEM_INFO_TEMPLATE_KEY);
    }

    public static void updateNewLotteryChanceSystemInfoTemplate(String template) {
        dao.save(NEW_LOTTERY_CHANCE_SYSTEM_INFO_TEMPLATE_KEY, template);
    }
    ///////////////////////////// new lottery chance end //////////////////////////////

    ////////////////////////////// liveness increase start ////////////////////////////////
    private final static String REMIND_LIVENESS_INCREASE_BY_SYSTEM_INFO_KEY = "remindLivenessIncreaseBySystemInfo";

    public static Boolean getRemindLivenessIncreaseBySystemInfo() {
        return dao.getBoolean(REMIND_LIVENESS_INCREASE_BY_SYSTEM_INFO_KEY);
    }

    public static void updateRemindLivenessIncreaseBySystemInfo(Boolean remindLivenessIncreaseBySystemInfo) {
        dao.saveBoolean(REMIND_LIVENESS_INCREASE_BY_SYSTEM_INFO_KEY, remindLivenessIncreaseBySystemInfo);
    }

    private final static String LIVENESS_INCREASE_SYSTEM_INFO_TEMPLATE_KEY = "livenessIncreaseSystemInfoTemplate";

    public static String getLivenessIncreaseSystemInfoTemplate() {
        return dao.get(LIVENESS_INCREASE_SYSTEM_INFO_TEMPLATE_KEY);
    }

    public static void updateLivenessIncreaseSystemInfoTemplate(String template) {
        dao.save(LIVENESS_INCREASE_SYSTEM_INFO_TEMPLATE_KEY, template);
    }
    ////////////////////////////// liveness increase end /////////////////////////////////

    ///////////////////////////// lottery announcement template start ////////////////////////////
    private final static String LOTTERY_ANNOUNCEMENT_TEMPLATE_KEY = "lotteryAnnouncementTemplate";

    public static String getLotteryAnnouncementTemplate() {
        return dao.get(LOTTERY_ANNOUNCEMENT_TEMPLATE_KEY);
    }

    public static void updateLotteryAnnouncementTemplate(String template) {
        dao.save(LOTTERY_ANNOUNCEMENT_TEMPLATE_KEY, template);
    }
    ///////////////////////////// lottery announcement template end //////////////////////////////

    //////////////////////////// seckill announcement template start ////////////////////////////
    private final static String SECKILL_ANNOUNCEMENT_TEMPLATE_KEY = "seckillAnnouncementTemplate";

    public static String getSeckillAnnouncementTemplate() {
        return dao.get(SECKILL_ANNOUNCEMENT_TEMPLATE_KEY);
    }

    public static void updateSeckillAnnouncementTemplate(String template) {
        dao.save(SECKILL_ANNOUNCEMENT_TEMPLATE_KEY, template);
    }
    //////////////////////////// seckill announcement template end //////////////////////////////

    ///////////////////////////// lottery rule start ///////////////////////////////
    /*private final static String LOTTERY_RULE_KEY = "lotteryRule";

    public static String getLotteryRule() {
        return dao.get(LOTTERY_RULE_KEY);
    }

    public static void updateLotteryRule(String lotteryRule) {
        dao.save(LOTTERY_RULE_KEY, lotteryRule);
    }*/
    private final static String LOTTERY_RULE_LINK = "lotteryRuleLink";

    public static String getLotteryRuleLink() {
        return dao.get(LOTTERY_RULE_LINK);
    }

    public static void updateLotteryRuleLink(String lotteryRuleLink) {
        dao.save(LOTTERY_RULE_LINK, lotteryRuleLink);
    }
    ///////////////////////////// lottery rule end ///////////////////////////////

    ////////////////////////////// qqlist start ////////////////////////////////////
    private final static String QQ_LIST_ID_KEY = "qqlistId";

    public static String getQQListId() {
        return dao.get(QQ_LIST_ID_KEY);
    }

    public static void updateQQListId(String qqlistId) {
        dao.save(QQ_LIST_ID_KEY, qqlistId);
    }

    private final static String QQ_LIST_DESCRIPTION_KEY = "qqlistDescription";

    public static String getQQListDescription() {
        return dao.get(QQ_LIST_DESCRIPTION_KEY);
    }

    public static void updateQQListDescription(String qqlistDescription) {
        dao.save(QQ_LIST_DESCRIPTION_KEY, qqlistDescription);
    }
    ////////////////////////////// qqlist end //////////////////////////////////////

    ///////////////////////////// tel validate description page start //////////////////////////
    private final static String TEL_VALIDATE_DESCRIPTION_PAGE_KEY = "telValidateDescriptionPage";

    public static String getTelValidateDescriptionPage() {
        return dao.get(TEL_VALIDATE_DESCRIPTION_PAGE_KEY);
    }

    public static void updateTelValidateDescriptionPage(String telValidateDescriptionPage) {
        dao.save(TEL_VALIDATE_DESCRIPTION_PAGE_KEY, telValidateDescriptionPage);
    }
    ///////////////////////////// tel validate description page end //////////////////////////

    ////////////////////////////// allocate lottery serial number ////////////////////////////////
    public static boolean allocateLotterySerialInAdvance() {
        Properties props = ClasspathFileUtils.getProperties("global-config.properties");
        String value = props.getProperty("allocateLotterySerialInAdvance");
        return value != null && (value.trim().equals("1") || value.trim().toLowerCase().equals("true"));
    }
    //////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////// invalid lot system info template ////////////////////////////
    private final static String NO_TEL_INVALID_LOT_SYSTEM_INFO_TEMPLATE_KEY = "noTelInvalidLotSystemInfoTemplate";

    public static String getNoTelInvalidLotSystemInfoTemplate() {
        return dao.get(NO_TEL_INVALID_LOT_SYSTEM_INFO_TEMPLATE_KEY);
    }

    public static void updateNoTelInvalidLotSystemInfoTemplate(String template) {
        dao.save(NO_TEL_INVALID_LOT_SYSTEM_INFO_TEMPLATE_KEY, template);
    }

    private final static String INSUFFICIENT_LIVENESS_INVALID_LOT_SYSTEM_INFO_TEMPLATE_KEY = "insufficientLivenessInvalidLotSystemInfoTemplate";

    public static String getInsufficientLivenessInvalidLotSystemInfoTemplate() {
        return dao.get(INSUFFICIENT_LIVENESS_INVALID_LOT_SYSTEM_INFO_TEMPLATE_KEY);
    }

    public static void updateInsufficientLivenessInvalidLotSystemInfoTemplate(String template) {
        dao.save(INSUFFICIENT_LIVENESS_INVALID_LOT_SYSTEM_INFO_TEMPLATE_KEY, template);
    }
    //////////////////////////////////////////////////////////////////////////////////////////
}
