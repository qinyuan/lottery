package com.qinyuan15.lottery.mvc.account;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.junit.Test;

public class VirtualUserCrawlerTest extends DatabaseTestCase {
    @Test
    public void testRun() throws Exception {
        VirtualUserCrawler crawler = new VirtualUserCrawler();
        //crawler.setCrawlSize(10000);
        crawler.run();
    }
}
