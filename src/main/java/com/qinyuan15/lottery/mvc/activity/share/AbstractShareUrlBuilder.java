package com.qinyuan15.lottery.mvc.activity.share;

import com.qinyuan.lib.sns.QQShareUrlBuilder;
import com.qinyuan.lib.sns.QzoneShareUrlBuilder;
import com.qinyuan.lib.sns.SinaWeiboShareUrlBuilder;
import com.qinyuan15.lottery.mvc.config.AppConfig;
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
        //this.targetUrl = host + PAGE + "?fromUser=" + userSerialKey + "&id=" + commodity.getId();
        this.targetUrl = host + PAGE + "?id=" + commodity.getId() + "&fromUser=" + userSerialKey +
                "&t=" + System.currentTimeMillis();
    }

    public String getSinaShareUrl() {
        String title = getSinaWeiboTitleTemplate();
        return new SinaWeiboShareUrlBuilder(getFinalTargetUrl(ShareMedium.SINA_WEIBO.en), title,
                getPictures(AppConfig.lottery.getSinaWeiboIncludePicture())).build();
    }

    protected abstract String getSinaWeiboTitleTemplate();

    public String getQQShareUrl() {
        String title = getQQTitleTemplate();
        String summary = getQQSummaryTemplate();
        String url = getFinalTargetUrl(ShareMedium.QQ.en);
        return new QQShareUrlBuilder(url, title, summary,
                getPictures(AppConfig.lottery.getQQIncludePicture())).build();
    }

    protected abstract String getQQTitleTemplate();

    protected abstract String getQQSummaryTemplate();

    public String getQzoneShareUrl() {
        String title = getQzoneTitleTemplate();
        String summary = getQzoneSummaryTemplate();
        Boolean includePicture = AppConfig.lottery.getQzoneIncludePicture();
        String picture = (includePicture != null && includePicture) ? pictureUrl : null;
        return new QzoneShareUrlBuilder(getFinalTargetUrl(ShareMedium.QZONE.en), title, summary, picture).build();
    }

    protected abstract String getQzoneTitleTemplate();

    protected abstract String getQzoneSummaryTemplate();

    public String getFinalTargetUrl(String medium) {
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
