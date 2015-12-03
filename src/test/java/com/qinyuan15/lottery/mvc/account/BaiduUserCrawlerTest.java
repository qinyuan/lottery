package com.qinyuan15.lottery.mvc.account;

import com.qinyuan.lib.lang.concurrent.ThreadUtils;
import com.qinyuan.lib.lang.test.TestFileUtils;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class BaiduUserCrawlerTest {
    @Test
    public void testRun() throws Exception {
        final File file = TestFileUtils.getTempFile("users.log");
        FileUtils.deleteQuietly(file);
        final BaiduUserCrawler crawler = new BaiduUserCrawler(new BaiduUserCrawler.UsernameHandler() {
            @Override
            public void handle(String username) {
                try {
                    FileUtils.writeStringToFile(file, username + "\n", true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        crawler.setInterval(0.5);

        (new Thread() {
            @Override
            public void run() {
                ThreadUtils.sleep(10);
                crawler.stop();
            }
        }).start();

        crawler.run();
    }
}
