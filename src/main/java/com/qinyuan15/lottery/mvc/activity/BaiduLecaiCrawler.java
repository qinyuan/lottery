package com.qinyuan15.lottery.mvc.activity;

public class BaiduLecaiCrawler extends AbstractDualColoredBallCrawler {
    @Override
    protected String getPageUrl(int fullTermNumber) {
        return "http://baidu.lecai.com/lottery/draw/ajax_get_detail.php?lottery_type=50&phase=" + fullTermNumber;
    }

    @Override
    protected DualColoredBallPageParser getPageParser(String pageContent, int fullTermNumber) {
        return new BaiduLecaiPageParser(pageContent);
    }
}