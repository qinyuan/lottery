package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan.lib.lang.concurrent.ThreadUtils;
import com.qinyuan15.lottery.mvc.activity.dualcoloredball.BaiduLecaiCrawler;
import com.qinyuan15.lottery.mvc.activity.dualcoloredball.DualColoredBallCrawler;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class LotteryActivityTerminatorTest {
    @Test
    public void testInit() throws Exception {
        LotteryActivityTerminator crawlers = new LotteryActivityTerminator(/*new DecimalFormat("000000")*/);

        List<DualColoredBallCrawler> crawlerList = new ArrayList<>();
        crawlerList.add(new BaiduLecaiCrawler());
        crawlers.setCrawlers(crawlerList);
        crawlers.init();
        ThreadUtils.sleep(120);
    }
}
