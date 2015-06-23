package com.qinyuan15.lottery.mvc;

import com.qinyuan15.utils.IntegerUtils;
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
}
