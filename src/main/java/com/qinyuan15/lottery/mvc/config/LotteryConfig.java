package com.qinyuan15.lottery.mvc.config;

public class LotteryConfig extends DatabaseConfig {
    ////////////////////////////// lottery sina weibo share link start /////////////////////////////
    private final static String LOTTERY_SINA_WEIBO_TITLE_KEY = "lotterySinaWeiboTitle";

    public String getSinaWeiboTitle() {
        return dao.get(LOTTERY_SINA_WEIBO_TITLE_KEY);
    }

    public void updateSinaWeiboTitle(String title) {
        dao.save(LOTTERY_SINA_WEIBO_TITLE_KEY, title);
    }

    private final static String LOTTERY_SINA_WEIBO_INCLUDE_PICTURE_KEY = "lotterySinaWeiboIncludePicture";

    public Boolean getSinaWeiboIncludePicture() {
        return dao.getBoolean(LOTTERY_SINA_WEIBO_INCLUDE_PICTURE_KEY);
    }

    public void updateSinaWeiboIncludePicture(Boolean includePicture) {
        dao.saveBoolean(LOTTERY_SINA_WEIBO_INCLUDE_PICTURE_KEY, includePicture);
    }
    ///////////////////////////// lottery sina weibo share link end ////////////////////////////////

    ///////////////////////////// lottery qq share link start //////////////////////////////
    private final static String LOTTERY_QQ_TITLE_KEY = "lotteryQQTitle";

    public String getQQTitle() {
        return dao.get(LOTTERY_QQ_TITLE_KEY);
    }

    public void updateQQTitle(String title) {
        dao.save(LOTTERY_QQ_TITLE_KEY, title);
    }

    private final static String LOTTERY_QQ_SUMMARY_KEY = "lotteryQQSummary";

    public String getQQSummary() {
        return dao.get(LOTTERY_QQ_SUMMARY_KEY);
    }

    public void updateQQSummary(String summary) {
        dao.save(LOTTERY_QQ_SUMMARY_KEY, summary);
    }

    private final static String LOTTERY_QQ_INCLUDE_PICTURE_KEY = "lotteryQQIncludePicture";

    public Boolean getQQIncludePicture() {
        return dao.getBoolean(LOTTERY_QQ_INCLUDE_PICTURE_KEY);
    }

    public void updateQQIncludePicture(Boolean includePicture) {
        dao.saveBoolean(LOTTERY_QQ_INCLUDE_PICTURE_KEY, includePicture);
    }
    ////////////////////////// lottery qq share link end /////////////////////////////////

    ////////////////////////// lottery qzone share link start //////////////////////////////
    private final static String LOTTERY_QZONE_TITLE_KEY = "lotteryQzoneTitle";

    public String getQzoneTitle() {
        return dao.get(LOTTERY_QZONE_TITLE_KEY);
    }

    public void updateQzoneTitle(String title) {
        dao.save(LOTTERY_QZONE_TITLE_KEY, title);
    }

    private final static String LOTTERY_QZONE_SUMMARY_KEY = "lotteryQzoneSummary";

    public String getQzoneSummary() {
        return dao.get(LOTTERY_QZONE_SUMMARY_KEY);
    }

    public void updateQzoneSummary(String summary) {
        dao.save(LOTTERY_QZONE_SUMMARY_KEY, summary);
    }

    private final static String LOTTERY_QZONE_INCLUDE_PICTURE_KEY = "lotteryQzoneIncludePicture";

    public Boolean getQzoneIncludePicture() {
        return dao.getBoolean(LOTTERY_QZONE_INCLUDE_PICTURE_KEY);
    }

    public void updateQzoneIncludePicture(Boolean includePicture) {
        dao.saveBoolean(LOTTERY_QZONE_INCLUDE_PICTURE_KEY, includePicture);
    }
    ///////////////////////// lottery qzone share link end ///////////////////////////////

    ///////////////////////////// new lottery chance start //////////////////////////////
    private final static String REMIND_NEW_LOTTERY_CHANCE_BY_MAIL_KEY = "remindNewLotteryChanceByMail";

    public Boolean getRemindNewChanceByMail() {
        return dao.getBoolean(REMIND_NEW_LOTTERY_CHANCE_BY_MAIL_KEY);
    }

    public void updateRemindNewChanceByMail(Boolean bool) {
        dao.saveBoolean(REMIND_NEW_LOTTERY_CHANCE_BY_MAIL_KEY, bool);
    }

    private final static String NEW_LOTTERY_CHANCE_MAIL_SUBJECT_TEMPLATE_KEY = "newLotteryChanceMailSubjectTemplate";

    public String getNewChanceMailSubjectTemplate() {
        return dao.get(NEW_LOTTERY_CHANCE_MAIL_SUBJECT_TEMPLATE_KEY);
    }

    public void updateNewChanceMailSubjectTemplate(String template) {
        dao.save(NEW_LOTTERY_CHANCE_MAIL_SUBJECT_TEMPLATE_KEY, template);
    }

    private final static String NEW_LOTTERY_CHANCE_MAIL_CONTENT_TEMPLATE_KEY = "newLotteryChanceMailContentTemplate";

    public String getNewChanceMailContentTemplate() {
        return dao.get(NEW_LOTTERY_CHANCE_MAIL_CONTENT_TEMPLATE_KEY);
    }

    public void updateNewChanceMailContentTemplate(String template) {
        dao.save(NEW_LOTTERY_CHANCE_MAIL_CONTENT_TEMPLATE_KEY, template);
    }

    private final static String NEW_LOTTERY_CHANCE_MAIL_ACCOUNT_ID_KEY = "newLotteryChanceMailAccountId";

    public Integer getNewChanceMailAccountId() {
        return dao.getPositiveInteger(NEW_LOTTERY_CHANCE_MAIL_ACCOUNT_ID_KEY);
    }

    public void updateNewChanceMailAccountId(Integer accountId) {
        dao.saveInteger(NEW_LOTTERY_CHANCE_MAIL_ACCOUNT_ID_KEY, accountId);
    }

    private final static String REMIND_NEW_LOTTERY_CHANCE_BY_SYSTEM_INFO_KEY = "remindNewLotteryChanceBySystemInfo";

    public Boolean getRemindNewChanceBySystemInfo() {
        return dao.getBoolean(REMIND_NEW_LOTTERY_CHANCE_BY_SYSTEM_INFO_KEY);
    }

    public void updateRemindNewChanceBySystemInfo(Boolean bool) {
        dao.saveBoolean(REMIND_NEW_LOTTERY_CHANCE_BY_SYSTEM_INFO_KEY, bool);
    }

    private final static String NEW_LOTTERY_CHANCE_SYSTEM_INFO_TEMPLATE_KEY = "newLotteryChanceSystemInfoTemplate";

    public String getNewChanceSystemInfoTemplate() {
        return dao.get(NEW_LOTTERY_CHANCE_SYSTEM_INFO_TEMPLATE_KEY);
    }

    public void updateNewChanceSystemInfoTemplate(String template) {
        dao.save(NEW_LOTTERY_CHANCE_SYSTEM_INFO_TEMPLATE_KEY, template);
    }
    ///////////////////////////// new lottery chance end //////////////////////////////
}