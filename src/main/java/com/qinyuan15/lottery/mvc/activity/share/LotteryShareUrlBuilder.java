package com.qinyuan15.lottery.mvc.activity.share;

import com.qinyuan15.lottery.mvc.config.AppConfig;
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
}
