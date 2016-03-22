package com.qinyuan15.lottery.mvc.config;

import com.qinyuan.lib.config.AppConfigDao;
import com.qinyuan.lib.lang.file.ClasspathFileUtils;

import java.util.Properties;

/**
 * Application Configuration
 * Created by qinyuan on 15-6-16.
 */
public class AppConfig {
    public final static PropertiesConfig props = new PropertiesConfig();
    public final static SystemConfig sys = new SystemConfig();
    public final static MailConfig mail = new MailConfig();
    public final static IndexConfig index = new IndexConfig();

    private final static AppConfigDao dao = new AppConfigDao();

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

    //////////////////////////////////// no tel lottery lot start //////////////////////////////////////
    private final static String NO_TEL_LOTTERY_LOT_COUNT_KEY = "noTelLotteryLotCount";

    public static Integer getNoTelLotteryLotCount() {
        return dao.getInteger(NO_TEL_LOTTERY_LOT_COUNT_KEY);
    }

    public static int getNoTelLotteryLotCountValue() {
        Integer noTelLotteryLotCount = getNoTelLotteryLotCount();
        return noTelLotteryLotCount == null ? 0 : noTelLotteryLotCount;
    }

    public static void updateNoTelLotteryLotCount(Integer noTelLotteryLotCount) {
        dao.saveInteger(NO_TEL_LOTTERY_LOT_COUNT_KEY, noTelLotteryLotCount);
    }

    private final static String NO_TEL_LOTTERY_LOT_PRICE_KEY = "noTelLotteryLotPrice";

    public static Double getNoTelLotteryLotPrice() {
        return dao.getDouble(NO_TEL_LOTTERY_LOT_PRICE_KEY);
    }

    public static double getNoTelLotteryLotPriceValue() {
        Double noTelLotteryLotPrice = getNoTelLotteryLotPrice();
        return noTelLotteryLotPrice == null ? 0.0 : noTelLotteryLotPrice;
    }

    public static void updateNoTelLotteryLotPrice(Double noTelLotteryLotPrice) {
        dao.saveDouble(NO_TEL_LOTTERY_LOT_PRICE_KEY, noTelLotteryLotPrice);
    }

    private final static String NO_TEL_LIVENESS_KEY = "noTelLiveness";

    public static Integer getNoTelLiveness() {
        return dao.getInteger(NO_TEL_LIVENESS_KEY);
    }

    public static void updateNoTelLiveness(Integer noTelLiveness) {
        dao.saveInteger(NO_TEL_LIVENESS_KEY, noTelLiveness);
    }
    //////////////////////////////////// no tel lot end ////////////////////////////////////////

    //////////////////////////////////// tel modification limit start ////////////////////////////

    //////////////////////////////////// tel modification limit end //////////////////////////////
    private final static String MAX_TEL_MODIFICATION_TIMES_KEY = "maxTelModificationTimes";

    public static Integer getMaxTelModificationTimes() {
        return dao.getInteger(MAX_TEL_MODIFICATION_TIMES_KEY);
    }

    public static void updateMaxTelModificationTimes(Integer maxTelModificationTimes) {
        dao.saveInteger(MAX_TEL_MODIFICATION_TIMES_KEY, maxTelModificationTimes);
    }

    //////////////////////////////////// support page start /////////////////////////////////
    private final static String SUPPORT_PAGE_IMAGE_KEY = "supportPageImage";

    public static String getSupportPageImage() {
        return dao.get(SUPPORT_PAGE_IMAGE_KEY);
    }

    public static void updateSupportPageImage(String supportPageImage) {
        dao.save(SUPPORT_PAGE_IMAGE_KEY, supportPageImage);
    }

    private final static String SUPPORT_PAGE_TEXT_KEY = "supportPageText";

    public static String getSupportPageText() {
        return dao.get(SUPPORT_PAGE_TEXT_KEY);
    }

    public static void updateSupportPageText(String supportPageText) {
        dao.save(SUPPORT_PAGE_TEXT_KEY, supportPageText);
    }
    //////////////////////////////////// support page end ///////////////////////////////////
}
