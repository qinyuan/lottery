package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.dao.Commodity;

public class SeckillShareUrlBuilder extends AbstractShareUrlBuilder {
    public SeckillShareUrlBuilder(String userSerialKey, String host, Commodity commodity) {
        super(userSerialKey, host, commodity);
    }

    @Override
    protected String getSinaWeiboTitleTemplate() {
        return AppConfig.getSeckillSinaWeiboTitle();
    }

    @Override
    protected String getQQTitleTemplate() {
        return AppConfig.getSeckillQQTitle();
    }

    @Override
    protected String getQQSummaryTemplate() {
        return AppConfig.getSeckillQQSummary();
    }

    @Override
    protected String getQzoneTitleTemplate() {
        return AppConfig.getSeckillQzoneTitle();
    }

    @Override
    protected String getQzoneSummaryTemplate() {
        return AppConfig.getSeckillQzoneSummary();
    }
}
