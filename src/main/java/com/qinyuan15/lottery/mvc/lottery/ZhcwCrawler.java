package com.qinyuan15.lottery.mvc.lottery;

public class ZhcwCrawler extends AbstractDualColoredBallCrawler {
    @Override
    protected String getPageUrl(int fullTermNumber) {
        return "http://www.zhcw.com/kaijiang/kjData/2012/zhcw_ssq_index_last30.js";
    }

    @Override
    protected DualColoredBallPageParser getPageParser(String pageContent, int fullTermNumber) {
        return new ZhcwPageParser(pageContent, fullTermNumber);
    }
}
