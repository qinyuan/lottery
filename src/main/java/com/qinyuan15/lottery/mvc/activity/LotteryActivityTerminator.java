package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan.lib.lang.time.DateUtils;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.lang.concurrent.ThreadUtils;
import com.qinyuan15.lottery.mvc.dao.DualColoredBallRecordDao;
import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.LotteryActivityDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LotteryActivityTerminator {
    private final static Logger LOGGER = LoggerFactory.getLogger(LotteryActivityTerminator.class);
    private Map<Integer, CrawlThread> threads = new HashMap<>();
    private List<DualColoredBallCrawler> crawlers;
    private final DecimalFormat lotNumberFormat;

    public LotteryActivityTerminator(DecimalFormat lotNumberFormat) {
        this.lotNumberFormat = lotNumberFormat;
    }

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
                // build crawl thread for each active lottery activity
                List<LotteryActivity> activities = LotteryActivityDao.factory().setExpire(false).getInstances();
                for (LotteryActivity activity : activities) {
                    CrawlThread thread = threads.get(activity.getId());

                    if (thread == null) {
                        // if related thread not exists, create and run it
                        thread = new CrawlThread(activity);
                        threads.put(activity.getId(), thread);
                        thread.start();
                    } else {
                        // if related thread already exists, just update activity data of it
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

                try {
                    DualColoredBallCrawler.Result result = getResult();
                    if (result != null) {
                        new LotteryActivityDao().end(activity.getId());
                        new VirtualParticipantAdjuster().adjust(activity.getId(), Long.parseLong(result.result));
                        new DualColoredBallRecordDao().add(activity.getDualColoredBallTerm(),
                                result.drawTime, result.result);
                        new LotteryResultUpdater(lotNumberFormat).update(activity.getId(), result.result);

                        // remove related thread after success
                        threads.remove(activity.getId());
                        break;
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                    LOGGER.error("Fail to crawl activity whose id is {}, info: {}", activity.getId(), e);
                }

                // sleep time becomes less and less on coming of expect end time
                Date expectEndTime = DateUtils.newDate(activity.getExpectEndTime());
                long timeDiff = expectEndTime.getTime() - System.currentTimeMillis();
                if (timeDiff > 100) {
                    ThreadUtils.sleep(((double) timeDiff) / 1000 / 3);
                } else {
                    ThreadUtils.sleep(0.2);
                }
            }
        }

        private DualColoredBallCrawler.Result getResult() {
            if (crawlers == null) {
                return null;
            }
            if (!new DualColoredBallTermValidator().validate(activity.getDualColoredBallTerm())) {
                return null;
            }

            for (DualColoredBallCrawler crawler : crawlers) {
                Integer dualColoredBallTerm = activity.getDualColoredBallTerm();
                if (!IntegerUtils.isPositive(dualColoredBallTerm)) {
                    continue;
                }

                DualColoredBallCrawler.Result result = crawler.getResult(dualColoredBallTerm);
                if (result != null && result.result != null && result.result.matches("^\\d{12}$")) {
                    return result;
                } else {
                    LOGGER.error("Fail to parse result of dualColoredBall {}", dualColoredBallTerm);
                }
            }
            return null;
        }
    }
}
