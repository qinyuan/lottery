package com.qinyuan15.lottery.mvc.config;

/**
 * Application Configuration
 * Created by qinyuan on 15-6-16.
 */
public class AppConfig extends DatabaseConfig {
    public final static PropertiesConfig props = new PropertiesConfig();
    public final static SystemConfig sys = new SystemConfig();
    public final static InfoConfig mail = new InfoConfig();
    public final static IndexConfig index = new IndexConfig();
    public final static LotteryConfig lottery = new LotteryConfig();
    public final static SeckillConfig seckill = new SeckillConfig();
    public final static LivenessConfig liveness = new LivenessConfig();
    public final static QQListConfig qqlist = new QQListConfig();

    //private final static AppConfigDao dao = new AppConfigDao();

    //////////////////////////////////// tel modification limit start ////////////////////////////
    private final static String MAX_TEL_MODIFICATION_TIMES_KEY = "maxTelModificationTimes";

    public static Integer getMaxTelModificationTimes() {
        return dao.getInteger(MAX_TEL_MODIFICATION_TIMES_KEY);
    }

    public static void updateMaxTelModificationTimes(Integer maxTelModificationTimes) {
        dao.saveInteger(MAX_TEL_MODIFICATION_TIMES_KEY, maxTelModificationTimes);
    }
    //////////////////////////////////// tel modification limit end //////////////////////////////

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
