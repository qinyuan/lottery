package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan.lib.lang.concurrent.ThreadUtils;
import org.junit.Test;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DualColoredBallCrawlersTest {
    @Test
    public void testInit() throws Exception {
        DualColoredBallCrawlers crawlers = new DualColoredBallCrawlers(new DecimalFormat("000000"));

        List<DualColoredBallCrawler> crawlerList = new ArrayList<>();
        crawlerList.add(new BaiduLecaiCrawler());
        crawlers.setCrawlers(crawlerList);
        crawlers.init();
        ThreadUtils.sleep(120);
    }
}
