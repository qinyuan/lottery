package com.qinyuan15.lottery.mvc.account;

import com.qinyuan15.lottery.mvc.dao.VirtualUserDao;

import java.util.ArrayList;
import java.util.List;

public class VirtualUserCrawler {
    private final static int DEFAULT_CRAWL_SIZE = 100000;
    private int crawlSize = DEFAULT_CRAWL_SIZE;
    private List<String> usernames = new ArrayList<>();
    private BaiduUserCrawler crawler;

    public void setCrawlSize(int crawlSize) {
        this.crawlSize = crawlSize;
    }

    public void run() {
        usernames.clear();
        crawler = new BaiduUserCrawler(new UsernameHandler());
        crawler.setInterval(0);
        crawler.run();

        VirtualUserDao dao = new VirtualUserDao();
        usernames = new VirtualUserFilter().setAsciiRate(0.75).filter(usernames);
        for (String username : usernames) {
            if (!dao.hasUsername(username)) {
                dao.add(username);
            }
        }
    }

    private class UsernameHandler implements BaiduUserCrawler.UsernameHandler {
        @Override
        public void handle(String username) {
            usernames.add(username);
            if (usernames.size() >= crawlSize) {
                if (crawler != null) {
                    crawler.stop();
                }
            }
        }
    }
}
