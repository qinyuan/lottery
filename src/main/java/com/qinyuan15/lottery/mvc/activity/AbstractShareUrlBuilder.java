package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan.lib.sns.QQShareUrlBuilder;
import com.qinyuan.lib.sns.QzoneShareUrlBuilder;
import com.qinyuan.lib.sns.SinaWeiboShareUrlBuilder;
import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.dao.Commodity;

import java.util.ArrayList;
import java.util.List;

abstract class AbstractShareUrlBuilder {
    private final static String PAGE = "commodity.html";

    private final String pictureUrl;
    private final String targetUrl;

    protected AbstractShareUrlBuilder(String userSerialKey, String host, Commodity commodity) {
        this.pictureUrl = commodity.getSnapshot();
        if (!host.contains("/")) {
            host += "/";
        }
        this.targetUrl = host + PAGE + "?fromUser=" + userSerialKey + "&id=" + commodity.getId();
    }

    public String getSinaShareUrl() {
        String title = getSinaWeiboTitleTemplate();
        return new SinaWeiboShareUrlBuilder(getFinalTargetUrl(ShareMedium.SINA_WEIBO.en), title,
                getPictures(AppConfig.getLotterySinaWeiboIncludePicture())).build();
    }

    protected abstract String getSinaWeiboTitleTemplate();

    public String getQQShareUrl() {
        String title = getQQTitleTemplate();
        String summary = getQQSummaryTemplate();
        return new QQShareUrlBuilder(getFinalTargetUrl(ShareMedium.QQ.en), title, summary,
                getPictures(AppConfig.getLotteryQQIncludePicture())).build();
    }

    protected abstract String getQQTitleTemplate();

    protected abstract String getQQSummaryTemplate();

    public String getQzoneShareUrl() {
        String title = getQzoneTitleTemplate();
        String summary = getQzoneSummaryTemplate();
        Boolean includePicture = AppConfig.getLotteryQzoneIncludePicture();
        String picture = (includePicture != null && includePicture) ? pictureUrl : null;
        return new QzoneShareUrlBuilder(getFinalTargetUrl(ShareMedium.QZONE.en), title, summary, picture).build();
    }

    protected abstract String getQzoneTitleTemplate();

    protected abstract String getQzoneSummaryTemplate();

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
}
