package com.qinyuan15.lottery.mvc.lottery;

import com.qinyuan15.utils.concurrent.ThreadUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DualColoredBallCrawlersTest {
    @Test
    public void testInit() throws Exception {
        DualColoredBallCrawlers crawlers = new DualColoredBallCrawlers();

        List<DualColoredBallCrawler> crawlerList = new ArrayList<>();
        crawlerList.add(new BaiduLecaiCrawler());
        crawlers.setCrawlers(crawlerList);
        crawlers.init();
        ThreadUtils.sleep(120);
    }
}
