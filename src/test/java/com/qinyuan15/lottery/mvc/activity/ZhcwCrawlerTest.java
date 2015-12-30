package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan15.lottery.mvc.activity.dualcoloredball.ZhcwCrawler;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ZhcwCrawlerTest {
    private ZhcwCrawler crawler = new ZhcwCrawler();

    @Test
    public void test() {
        DualColoredBallCrawler.Result result = crawler.getResult(2015090);
        assertThat(result).isNotNull();
        assertThat(result.drawTime).isEqualTo("2015-08-04 21:15:00");
        assertThat(result.result).isEqualTo("10 12 14 22 25 33");
    }
}
