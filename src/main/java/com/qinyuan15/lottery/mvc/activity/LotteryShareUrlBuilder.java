package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.dao.Commodity;

public class LotteryShareUrlBuilder extends AbstractShareUrlBuilder {
    public LotteryShareUrlBuilder(String userSerialKey, String host, Commodity commodity) {
        super(userSerialKey, host, commodity);
    }

    @Override
    protected String getSinaWeiboTitleTemplate() {
        return AppConfig.getLotterySinaWeiboTitle();
    }

    @Override
    protected String getQQTitleTemplate() {
        return AppConfig.getLotteryQQTitle();
    }

    @Override
    protected String getQQSummaryTemplate() {
        return AppConfig.getLotteryQQSummary();
    }

    @Override
    protected String getQzoneTitleTemplate() {
        return AppConfig.getLotteryQzoneTitle();
    }

    @Override
    protected String getQzoneSummaryTemplate() {
        return AppConfig.getLotteryQzoneSummary();
    }

    /*private final static String PAGE = "commodity.html";

    private final String pictureUrl;
    private final String targetUrl;

    public LotteryShareUrlBuilder(String userSerialKey, String host, Commodity commodity) {
        this.pictureUrl = commodity.getSnapshot();
        if (!host.contains("/")) {
            host += "/";
        }
        this.targetUrl = host + PAGE + "?fromUser=" + userSerialKey + "&id=" + commodity.getId();
    }

    public String getSinaShareUrl() {
        String title = AppConfig.getLotterySinaWeiboTitle();
        return new SinaWeiboShareUrlBuilder(getFinalTargetUrl(ShareMedium.SINA_WEIBO.en), title,
                getPictures(AppConfig.getLotterySinaWeiboIncludePicture())).build();
    }

    public String getQQShareUrl() {
        String title = AppConfig.getLotteryQQTitle();
        String summary = AppConfig.getLotteryQQSummary();
        return new QQShareUrlBuilder(getFinalTargetUrl(ShareMedium.QQ.en), title, summary,
                getPictures(AppConfig.getLotteryQQIncludePicture())).build();
    }

    public String getQzoneShareUrl() {
        String title = AppConfig.getLotteryQzoneTitle();
        String summary = AppConfig.getLotteryQzoneSummary();
        Boolean includePicture = AppConfig.getLotteryQzoneIncludePicture();
        String picture = (includePicture != null && includePicture) ? pictureUrl : null;
        return new QzoneShareUrlBuilder(getFinalTargetUrl(ShareMedium.QZONE.en), title, summary, picture).build();
    }

    private String getFinalTargetUrl(String medium) {
        return targetUrl + "&medium=" + medium;
    }

    private List<String> getPictures(Boolean includePicture) {
        List<String> pictures = new ArrayList<>();
        if (includePicture != null && includePicture) {
            pictures.add(pictureUrl);
        }
        return pictures;
    }
    */
}
