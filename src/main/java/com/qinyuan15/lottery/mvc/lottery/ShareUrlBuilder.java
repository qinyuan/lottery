package com.qinyuan15.lottery.mvc.lottery;

import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.dao.Commodity;
import com.qinyuan15.utils.sns.share.QQShareUrlBuilder;
import com.qinyuan15.utils.sns.share.QzoneShareUrlBuilder;
import com.qinyuan15.utils.sns.share.SinaWeiboShareUrlBuilder;

import java.util.ArrayList;
import java.util.List;

public class ShareUrlBuilder {
    private final static String PAGE = "commodity.html";

    private final String pictureUrl;
    private final String targetUrl;

    public ShareUrlBuilder(String userSerialKey, String host, Commodity commodity) {
        this.pictureUrl = commodity.getSnapshot();
        if (!host.contains("/")) {
            host += "/";
        }
        this.targetUrl = host + PAGE + "?fromUser=" + userSerialKey + "&id=" + commodity.getId();
    }

    public String getSinaShareUrl() {
        String title = AppConfig.getSinaWeiboTitle();
        return new SinaWeiboShareUrlBuilder(getFinalTargetUrl(ShareMedium.SINA_WEIBO.en), title,
                getPictures(AppConfig.getSinaWeiboIncludePicture())).build();
    }

    public String getQQShareUrl() {
        String title = AppConfig.getQQTitle();
        String summary = AppConfig.getQQSummary();
        return new QQShareUrlBuilder(getFinalTargetUrl(ShareMedium.QQ.en), title, summary,
                getPictures(AppConfig.getQQIncludePicture())).build();
    }

    public String getQzoneShareUrl() {
        String title = AppConfig.getQZoneTitle();
        String summary = AppConfig.getQzoneSummary();
        Boolean includePicture = AppConfig.getQzoneIncludePicture();
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
}
