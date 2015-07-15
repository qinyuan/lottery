package com.qinyuan15.lottery.mvc.lottery;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BaiduLecaiCrawlerTest {
    @Test
    public void testGetResult() {
        BaiduLecaiCrawler crawler = new BaiduLecaiCrawler();
        assertThat(crawler.getResult(2015080)).isEqualTo("141725272830");
        assertThat(crawler.getResult(2015081)).isEqualTo("132022262831");
        assertThat(crawler.getResult(2015076)).isEqualTo("010910192327");
        System.out.println(crawler.getResult(2015090));

        for (int i = 0; i < 10000; i++) {
            String result = crawler.getResult(2015080);
            if (!result.equals("141725272830")) {
                System.out.println(i);
            }
            if (i % 100 == 0) {
                System.out.println(i);
            }
        }
    }
}
