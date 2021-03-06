package com.qinyuan15.lottery.mvc.config;

public class SeckillConfig extends DatabaseConfig {
    ///////////////////////////// seckill sina weibo share link start //////////////////////////////
    private final static String SECKILL_SINA_WEIBO_TITLE_KEY = "seckillSinaWeiboTitle";

    public String getSinaWeiboTitle() {
        return getValue(SECKILL_SINA_WEIBO_TITLE_KEY);
    }

    public void updateSinaWeiboTitle(String title) {
        saveToDatabase(SECKILL_SINA_WEIBO_TITLE_KEY, title);
    }

    private final static String SECKILL_SINA_WEIBO_INCLUDE_PICTURE_KEY = "seckillSinaWeiboIncludePicture";

    public Boolean getSinaWeiboIncludePicture() {
        return parseBoolean(getValue(SECKILL_SINA_WEIBO_INCLUDE_PICTURE_KEY));
    }

    public void updateSinaWeiboIncludePicture(Boolean includePicture) {
        saveToDatabase(SECKILL_SINA_WEIBO_INCLUDE_PICTURE_KEY, includePicture);
    }
    ///////////////////////////// seckill sina weibo share link end ////////////////////////////////

    ////////////////////////// seckill qq share link start ///////////////////////////////
    private final static String SECKILL_QQ_TITLE_KEY = "seckillQQTitle";

    public String getQQTitle() {
        return getValue(SECKILL_QQ_TITLE_KEY);
    }

    public void updateQQTitle(String title) {
        saveToDatabase(SECKILL_QQ_TITLE_KEY, title);
    }

    private final static String SECKILL_QQ_SUMMARY_KEY = "seckillQQSummary";

    public String getQQSummary() {
        return getValue(SECKILL_QQ_SUMMARY_KEY);
    }

    public void updateQQSummary(String summary) {
        saveToDatabase(SECKILL_QQ_SUMMARY_KEY, summary);
    }

    private final static String SECKILL_QQ_INCLUDE_PICTURE_KEY = "seckillQQIncludePicture";

    public Boolean getQQIncludePicture() {
        return parseBoolean(getValue(SECKILL_QQ_INCLUDE_PICTURE_KEY));
    }

    public void updateQQIncludePicture(Boolean includePicture) {
        saveToDatabase(SECKILL_QQ_INCLUDE_PICTURE_KEY, includePicture);
    }
    ////////////////////////// seckill qq share link end /////////////////////////////////

    ///////////////////////// seckill qzone share link start //////////////////////////////
    private final static String SECKILL_QZONE_TITLE_KEY = "seckillQzoneTitle";

    public String getQzoneTitle() {
        return getValue(SECKILL_QZONE_TITLE_KEY);
    }

    public void updateQzoneTitle(String title) {
        saveToDatabase(SECKILL_QZONE_TITLE_KEY, title);
    }

    private final static String SECKILL_QZONE_SUMMARY_KEY = "seckillQzoneSummary";

    public String getQzoneSummary() {
        return getValue(SECKILL_QZONE_SUMMARY_KEY);
    }

    public void updateQzoneSummary(String summary) {
        saveToDatabase(SECKILL_QZONE_SUMMARY_KEY, summary);
    }

    private final static String SECKILL_QZONE_INCLUDE_PICTURE_KEY = "seckillQzoneIncludePicture";

    public Boolean getQzoneIncludePicture() {
        return parseBoolean(getValue(SECKILL_QZONE_INCLUDE_PICTURE_KEY));
    }

    public void updateQzoneIncludePicture(Boolean includePicture) {
        saveToDatabase(SECKILL_QZONE_INCLUDE_PICTURE_KEY, includePicture);
    }
    ///////////////////////// seckill qzone share link end ////////////////////////////////

    ///////////////////////// seckill poker site start ///////////////////////////////
    private final static String SECKILL_POKER_FRONT_SIDE_KEY = "seckillPokerFrontSide";

    public String getPokerFrontSide() {
        return getValue(SECKILL_POKER_FRONT_SIDE_KEY);
    }

    public void updatePokerFrontSide(String path) {
        saveToDatabase(SECKILL_POKER_FRONT_SIDE_KEY, path);
    }

    private final static String SECKILL_POKER_BACK_SIDE_KEY = "seckillPokerBackSide";

    public String getPokerBackSide() {
        return getValue(SECKILL_POKER_BACK_SIDE_KEY);
    }

    public void updatePokerBackSide(String path) {
        saveToDatabase(SECKILL_POKER_BACK_SIDE_KEY, path);
    }
    ///////////////////////// seckill poker site end ////////////////////////////////

    //////////////////////////// seckill announcement template start ////////////////////////////
    private final static String SECKILL_ANNOUNCEMENT_TEMPLATE_KEY = "seckillAnnouncementTemplate";

    public String getAnnouncementTemplate() {
        return getValue(SECKILL_ANNOUNCEMENT_TEMPLATE_KEY);
    }

    public void updateAnnouncementTemplate(String template) {
        saveToDatabase(SECKILL_ANNOUNCEMENT_TEMPLATE_KEY, template);
    }
    //////////////////////////// seckill announcement template end //////////////////////////////
}
