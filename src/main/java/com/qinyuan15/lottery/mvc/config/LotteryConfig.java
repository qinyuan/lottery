package com.qinyuan15.lottery.mvc.config;

public class LotteryConfig extends DatabaseConfig {
    ////////////////////////////// lottery sina weibo share link start /////////////////////////////
    private final static String LOTTERY_SINA_WEIBO_TITLE_KEY = "lotterySinaWeiboTitle";

    public String getSinaWeiboTitle() {
        return getValue(LOTTERY_SINA_WEIBO_TITLE_KEY);
    }

    public void updateSinaWeiboTitle(String title) {
        saveToDatabase(LOTTERY_SINA_WEIBO_TITLE_KEY, title);
    }

    private final static String LOTTERY_SINA_WEIBO_INCLUDE_PICTURE_KEY = "lotterySinaWeiboIncludePicture";

    public Boolean getSinaWeiboIncludePicture() {
        return parseBoolean(getValue(LOTTERY_SINA_WEIBO_INCLUDE_PICTURE_KEY));
    }

    public void updateSinaWeiboIncludePicture(Boolean includePicture) {
        saveToDatabase(LOTTERY_SINA_WEIBO_INCLUDE_PICTURE_KEY, includePicture);
    }
    ///////////////////////////// lottery sina weibo share link end ////////////////////////////////

    ///////////////////////////// lottery qq share link start //////////////////////////////
    private final static String LOTTERY_QQ_TITLE_KEY = "lotteryQQTitle";

    public String getQQTitle() {
        return getValue(LOTTERY_QQ_TITLE_KEY);
    }

    public void updateQQTitle(String title) {
        saveToDatabase(LOTTERY_QQ_TITLE_KEY, title);
    }

    private final static String LOTTERY_QQ_SUMMARY_KEY = "lotteryQQSummary";

    public String getQQSummary() {
        return getValue(LOTTERY_QQ_SUMMARY_KEY);
    }

    public void updateQQSummary(String summary) {
        saveToDatabase(LOTTERY_QQ_SUMMARY_KEY, summary);
    }

    private final static String LOTTERY_QQ_INCLUDE_PICTURE_KEY = "lotteryQQIncludePicture";

    public Boolean getQQIncludePicture() {
        return parseBoolean(getValue(LOTTERY_QQ_INCLUDE_PICTURE_KEY));
    }

    public void updateQQIncludePicture(Boolean includePicture) {
        saveToDatabase(LOTTERY_QQ_INCLUDE_PICTURE_KEY, includePicture);
    }
    ////////////////////////// lottery qq share link end /////////////////////////////////

    ////////////////////////// lottery qzone share link start //////////////////////////////
    private final static String LOTTERY_QZONE_TITLE_KEY = "lotteryQzoneTitle";

    public String getQzoneTitle() {
        return getValue(LOTTERY_QZONE_TITLE_KEY);
    }

    public void updateQzoneTitle(String title) {
        saveToDatabase(LOTTERY_QZONE_TITLE_KEY, title);
    }

    private final static String LOTTERY_QZONE_SUMMARY_KEY = "lotteryQzoneSummary";

    public String getQzoneSummary() {
        return getValue(LOTTERY_QZONE_SUMMARY_KEY);
    }

    public void updateQzoneSummary(String summary) {
        saveToDatabase(LOTTERY_QZONE_SUMMARY_KEY, summary);
    }

    private final static String LOTTERY_QZONE_INCLUDE_PICTURE_KEY = "lotteryQzoneIncludePicture";

    public Boolean getQzoneIncludePicture() {
        return parseBoolean(getValue(LOTTERY_QZONE_INCLUDE_PICTURE_KEY));
    }

    public void updateQzoneIncludePicture(Boolean includePicture) {
        saveToDatabase(LOTTERY_QZONE_INCLUDE_PICTURE_KEY, includePicture);
    }
    ///////////////////////// lottery qzone share link end ///////////////////////////////

    ///////////////////////////// new lottery chance start //////////////////////////////
    private final static String REMIND_NEW_LOTTERY_CHANCE_BY_MAIL_KEY = "remindNewLotteryChanceByMail";

    public Boolean getRemindNewChanceByMail() {
        return parseBoolean(getValue(REMIND_NEW_LOTTERY_CHANCE_BY_MAIL_KEY));
    }

    public void updateRemindNewChanceByMail(Boolean bool) {
        saveToDatabase(REMIND_NEW_LOTTERY_CHANCE_BY_MAIL_KEY, bool);
    }

    private final static String NEW_LOTTERY_CHANCE_MAIL_SUBJECT_TEMPLATE_KEY = "newLotteryChanceMailSubjectTemplate";

    public String getNewChanceMailSubjectTemplate() {
        return getValue(NEW_LOTTERY_CHANCE_MAIL_SUBJECT_TEMPLATE_KEY);
    }

    public void updateNewChanceMailSubjectTemplate(String template) {
        saveToDatabase(NEW_LOTTERY_CHANCE_MAIL_SUBJECT_TEMPLATE_KEY, template);
    }

    private final static String NEW_LOTTERY_CHANCE_MAIL_CONTENT_TEMPLATE_KEY = "newLotteryChanceMailContentTemplate";

    public String getNewChanceMailContentTemplate() {
        return getValue(NEW_LOTTERY_CHANCE_MAIL_CONTENT_TEMPLATE_KEY);
    }

    public void updateNewChanceMailContentTemplate(String template) {
        saveToDatabase(NEW_LOTTERY_CHANCE_MAIL_CONTENT_TEMPLATE_KEY, template);
    }

    private final static String NEW_LOTTERY_CHANCE_MAIL_ACCOUNT_ID_KEY = "newLotteryChanceMailAccountId";

    public Integer getNewChanceMailAccountId() {
        return parsePositiveInteger(getValue(NEW_LOTTERY_CHANCE_MAIL_ACCOUNT_ID_KEY));
    }

    public void updateNewChanceMailAccountId(Integer accountId) {
        saveToDatabase(NEW_LOTTERY_CHANCE_MAIL_ACCOUNT_ID_KEY, accountId);
    }

    private final static String REMIND_NEW_LOTTERY_CHANCE_BY_SYSTEM_INFO_KEY = "remindNewLotteryChanceBySystemInfo";

    public Boolean getRemindNewChanceBySystemInfo() {
        return parseBoolean(getValue(REMIND_NEW_LOTTERY_CHANCE_BY_SYSTEM_INFO_KEY));
    }

    public void updateRemindNewChanceBySystemInfo(Boolean bool) {
        saveToDatabase(REMIND_NEW_LOTTERY_CHANCE_BY_SYSTEM_INFO_KEY, bool);
    }

    private final static String NEW_LOTTERY_CHANCE_SYSTEM_INFO_TEMPLATE_KEY = "newLotteryChanceSystemInfoTemplate";

    public String getNewChanceSystemInfoTemplate() {
        return getValue(NEW_LOTTERY_CHANCE_SYSTEM_INFO_TEMPLATE_KEY);
    }

    public void updateNewChanceSystemInfoTemplate(String template) {
        saveToDatabase(NEW_LOTTERY_CHANCE_SYSTEM_INFO_TEMPLATE_KEY, template);
    }
    ///////////////////////////// new lottery chance end //////////////////////////////

    ///////////////////////////// lottery announcement template start ////////////////////////////
    private final static String LOTTERY_ANNOUNCEMENT_TEMPLATE_KEY = "lotteryAnnouncementTemplate";

    public String getAnnouncementTemplate() {
        return getValue(LOTTERY_ANNOUNCEMENT_TEMPLATE_KEY);
    }

    public void updateAnnouncementTemplate(String template) {
        saveToDatabase(LOTTERY_ANNOUNCEMENT_TEMPLATE_KEY, template);
    }
    ///////////////////////////// lottery announcement template end //////////////////////////////

    ///////////////////////////// lottery rule start ///////////////////////////////
    private final static String LOTTERY_RULE_LINK = "lotteryRuleLink";

    public String getRuleLink() {
        return getValue(LOTTERY_RULE_LINK);
    }

    public void updateRuleLink(String lotteryRuleLink) {
        saveToDatabase(LOTTERY_RULE_LINK, lotteryRuleLink);
    }
    ///////////////////////////// lottery rule end ///////////////////////////////

    //////////////////////////// invalid lot system info template ////////////////////////////
    private final static String NO_TEL_INVALID_LOT_SYSTEM_INFO_TEMPLATE_KEY = "noTelInvalidLotSystemInfoTemplate";

    public String getNoTelInvalidLotSystemInfoTemplate() {
        return getValue(NO_TEL_INVALID_LOT_SYSTEM_INFO_TEMPLATE_KEY);
    }

    public void updateNoTelInvalidLotSystemInfoTemplate(String template) {
        saveToDatabase(NO_TEL_INVALID_LOT_SYSTEM_INFO_TEMPLATE_KEY, template);
    }

    private final static String INSUFFICIENT_LIVENESS_INVALID_LOT_SYSTEM_INFO_TEMPLATE_KEY =
            "insufficientLivenessInvalidLotSystemInfoTemplate";

    public String getInsufficientLivenessInvalidLotSystemInfoTemplate() {
        return getValue(INSUFFICIENT_LIVENESS_INVALID_LOT_SYSTEM_INFO_TEMPLATE_KEY);
    }

    public void updateInsufficientLivenessInvalidLotSystemInfoTemplate(String template) {
        saveToDatabase(INSUFFICIENT_LIVENESS_INVALID_LOT_SYSTEM_INFO_TEMPLATE_KEY, template);
    }
    //////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////// no tel lottery lot start //////////////////////////////////////
    private final static String NO_TEL_LOTTERY_LOT_COUNT_KEY = "noTelLotteryLotCount";

    public Integer getNoTelLotCount() {
        return parseInteger(getValue(NO_TEL_LOTTERY_LOT_COUNT_KEY));
    }

    public int getNoTelLotCountValue() {
        Integer noTelLotteryLotCount = getNoTelLotCount();
        return noTelLotteryLotCount == null ? 0 : noTelLotteryLotCount;
    }

    public void updateNoTelLotCount(Integer noTelLotteryLotCount) {
        saveToDatabase(NO_TEL_LOTTERY_LOT_COUNT_KEY, noTelLotteryLotCount);
    }

    private final static String NO_TEL_LOTTERY_LOT_PRICE_KEY = "noTelLotteryLotPrice";

    public Double getNoTelLotPrice() {
        return parseDouble(getValue(NO_TEL_LOTTERY_LOT_PRICE_KEY));
    }

    public double getNoTelLotPriceValue() {
        Double noTelLotteryLotPrice = getNoTelLotPrice();
        return noTelLotteryLotPrice == null ? 0.0 : noTelLotteryLotPrice;
    }

    public void updateNoTelLotPrice(Double noTelLotteryLotPrice) {
        saveToDatabase(NO_TEL_LOTTERY_LOT_PRICE_KEY, noTelLotteryLotPrice);
    }

    private final static String NO_TEL_LIVENESS_KEY = "noTelLiveness";

    public Integer getNoTelLiveness() {
        return parseInteger(getValue(NO_TEL_LIVENESS_KEY));
    }

    public void updateNoTelLiveness(Integer noTelLiveness) {
        saveToDatabase(NO_TEL_LIVENESS_KEY, noTelLiveness);
    }
    //////////////////////////////////// no tel lot end ////////////////////////////////////////
}
