package com.qinyuan15.lottery.mvc.config;

import com.qinyuan.lib.config.AppConfigDao;

/**
 * Application Configuration
 * Created by qinyuan on 15-6-16.
 */
public class AppConfig {
    public final static PropertiesConfig props = new PropertiesConfig();
    public final static SystemConfig sys = new SystemConfig();
    public final static InfoConfig mail = new InfoConfig();
    public final static IndexConfig index = new IndexConfig();
    public final static LotteryConfig lottery = new LotteryConfig();
    public final static SeckillConfig seckill = new SeckillConfig();
    public final static LivenessConfig liveness =  new LivenessConfig();

    private final static AppConfigDao dao = new AppConfigDao();

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

    //////////////////////////// invalid lot system info template ////////////////////////////
    private final static String NO_TEL_INVALID_LOT_SYSTEM_INFO_TEMPLATE_KEY = "noTelInvalidLotSystemInfoTemplate";

    public static String getNoTelInvalidLotSystemInfoTemplate() {
        return dao.get(NO_TEL_INVALID_LOT_SYSTEM_INFO_TEMPLATE_KEY);
    }

    public static void updateNoTelInvalidLotSystemInfoTemplate(String template) {
        dao.save(NO_TEL_INVALID_LOT_SYSTEM_INFO_TEMPLATE_KEY, template);
    }

    private final static String INSUFFICIENT_LIVENESS_INVALID_LOT_SYSTEM_INFO_TEMPLATE_KEY =
            "insufficientLivenessInvalidLotSystemInfoTemplate";

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
