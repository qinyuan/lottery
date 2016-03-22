package com.qinyuan15.lottery.mvc.activity.share;

import com.qinyuan15.lottery.mvc.config.AppConfig;
import com.qinyuan15.lottery.mvc.dao.Commodity;

public class SeckillShareUrlBuilder extends AbstractShareUrlBuilder {
    public SeckillShareUrlBuilder(String userSerialKey, String host, Commodity commodity) {
        super(userSerialKey, host, commodity);
    }

    @Override
    protected String getSinaWeiboTitleTemplate() {
        return AppConfig.seckill.getSinaWeiboTitle();
    }

    @Override
    protected String getQQTitleTemplate() {
        return AppConfig.seckill.getQQTitle();
    }

    @Override
    protected String getQQSummaryTemplate() {
        return AppConfig.seckill.getQQSummary();
    }

    @Override
    protected String getQzoneTitleTemplate() {
        return AppConfig.seckill.getQzoneTitle();
    }

    @Override
    protected String getQzoneSummaryTemplate() {
        return AppConfig.seckill.getQzoneSummary();
    }
}
