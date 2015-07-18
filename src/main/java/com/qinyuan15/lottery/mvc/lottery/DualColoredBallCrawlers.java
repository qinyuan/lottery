package com.qinyuan15.lottery.mvc.lottery;

import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.LotteryActivityDao;
import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.concurrent.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DualColoredBallCrawlers {
    private final static Logger LOGGER = LoggerFactory.getLogger(DualColoredBallCrawlers.class);
    private Map<Integer, CrawlThread> threads = new HashMap<>();
    private List<DualColoredBallCrawler> crawlers;

    public void init() {
        new LoadActivityThread().start();
    }

    public void setCrawlers(List<DualColoredBallCrawler> crawlers) {
        this.crawlers = crawlers;
    }

    private class LoadActivityThread extends Thread {
        final static int INTERVAL = 60; // reload each minute

        @Override
        public void run() {
            while (true) {
                List<LotteryActivity> activities = LotteryActivityDao.factory().setExpire(false).getInstances();
                for (LotteryActivity activity : activities) {
                    CrawlThread thread = threads.get(activity.getId());

                    if (thread == null) {
                        thread = new CrawlThread(activity);
                        threads.put(activity.getId(), thread);
                        thread.start();
                    } else {
                        thread.activity = activity;
                    }
                }
                ThreadUtils.sleep(INTERVAL);
            }
        }
    }

    private class CrawlThread extends Thread {
        LotteryActivity activity;

        CrawlThread(LotteryActivity activity) {
            this.activity = activity;
        }

        @Override
        public void run() {
            while (true) {
                if (!DateUtils.isDateOrDateTime(activity.getExpectEndTime())) {
                    break;
                }

                Date expectEndTime = DateUtils.newDate(activity.getExpectEndTime());
                Date now = DateUtils.now();
                long timeDiff = Math.abs(expectEndTime.getTime() - now.getTime());
                try {
                    Long result = getResult();
                    if (result != null) {
                        new LotteryActivityDao().end(activity.getId());
                        new VirtualParticipantAdjuster().adjust(activity.getId(), result);
                        threads.remove(activity.getId());
                        break;
                    }
                } catch (Throwable e) {
                    LOGGER.error("Fail to crawl activity whose id is {}, info: {}", activity.getId(), e);
                }
                ThreadUtils.sleep(((double) timeDiff) / 1000 / 3);
            }
        }

        private Long getResult() {
            if (crawlers == null) {
                return null;
            }
            if (!new DualColoredBallTermValidator().validate(activity.getDualColoredBallTerm())) {
                return null;
            }

            for (DualColoredBallCrawler crawler : crawlers) {
                String result = crawler.getResult(activity.getDualColoredBallTerm());
                if (result != null && result.matches("^\\d{12}$")) {
                    return Long.parseLong(result);
                }
            }
            return null;
        }
    }
}