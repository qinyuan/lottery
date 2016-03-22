package com.qinyuan15.lottery.mvc.activity.share;

import com.qinyuan15.lottery.mvc.config.AppConfig;
import com.qinyuan15.lottery.mvc.dao.Commodity;

public class LotteryShareUrlBuilder extends AbstractShareUrlBuilder {
    public LotteryShareUrlBuilder(String userSerialKey, String host, Commodity commodity) {
        super(userSerialKey, host, commodity);
    }

    @Override
    protected String getSinaWeiboTitleTemplate() {
        return AppConfig.lottery.getSinaWeiboTitle();
    }

    @Override
    protected String getQQTitleTemplate() {
        return AppConfig.lottery.getQQTitle();
    }

    @Override
    protected String getQQSummaryTemplate() {
        return AppConfig.lottery.getQQSummary();
    }

    @Override
    protected String getQzoneTitleTemplate() {
        return AppConfig.lottery.getQzoneTitle();
    }

    @Override
    protected String getQzoneSummaryTemplate() {
        return AppConfig.lottery.getQzoneSummary();
    }
}
