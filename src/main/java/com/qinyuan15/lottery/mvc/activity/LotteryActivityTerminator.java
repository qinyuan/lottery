package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.lang.concurrent.ThreadUtils;
import com.qinyuan.lib.lang.time.DateUtils;
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
    private Map<Integer, LotteryEndThread> endThreads = new HashMap<>();
    private Map<Integer, LotteryCloseThread> closeThreads = new HashMap<>();
    private List<DualColoredBallCrawler> crawlers;
    private final DecimalFormat lotNumberFormat;

    public LotteryActivityTerminator(DecimalFormat lotNumberFormat) {
        this.lotNumberFormat = lotNumberFormat;
    }

    public void init() {
        new EndThreadScheduler().start();
        new CloseThreadScheduler().start();
    }

    public void setCrawlers(List<DualColoredBallCrawler> crawlers) {
        this.crawlers = crawlers;
    }

    private class CloseThreadScheduler extends Thread {
        final static int INTERVAL = 60; // reload each minute

        @Override
        public void run() {
            while (true) {
                // build crawl thread for each unclosed lottery activity
                List<LotteryActivity> activities = new LotteryActivityDao().getUnclosedInstances();
                for (LotteryActivity activity : activities) {
                    LotteryCloseThread thread = closeThreads.get(activity.getId());
                    if (thread == null) {
                        // if related thread not close, create and run it
                        thread = new LotteryCloseThread(activity);
                        closeThreads.put(activity.getId(), thread);
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

    private class EndThreadScheduler extends Thread {
        final static int INTERVAL = 60; // reload each minute

        @Override
        public void run() {
            while (true) {
                // build crawl thread for each active lottery activity
                List<LotteryActivity> activities = LotteryActivityDao.factory().setExpire(false).getInstances();
                for (LotteryActivity activity : activities) {
                    LotteryEndThread thread = endThreads.get(activity.getId());

                    if (thread == null) {
                        // if related thread not exists, create and run it
                        thread = new LotteryEndThread(activity);
                        endThreads.put(activity.getId(), thread);
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

    private class LotteryCloseThread extends Thread {
        LotteryActivity activity;

        LotteryCloseThread(LotteryActivity activity) {
            this.activity = activity;
        }

        @Override
        public void run() {
            while (true) {
                if (!DateUtils.isDateOrDateTime(activity.getCloseTime())) {
                    break;
                }

                Date closeTime = DateUtils.newDate(activity.getCloseTime());
                long timeDiff = closeTime.getTime() - System.currentTimeMillis();
                try {
                    if (timeDiff <= 0) {
                        new LotteryActivityDao().close(activity);
                        new InvalidLotteryLotSystemInfoSender().send(activity);

                        closeThreads.remove(activity.getId());
                        break;
                    }
                    new LotteryLotCounter().count(activity);    // force program to update virtual participants
                } catch (Throwable e) {
                    e.printStackTrace();
                    LOGGER.error("Fail to crawl activity whose id is {}, info: {}", activity.getId(), e);
                }

                ActivityTerminatorUtils.sleep(timeDiff);
            }
        }
    }

    private class LotteryEndThread extends Thread {
        LotteryActivity activity;

        LotteryEndThread(LotteryActivity activity) {
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
                        //new VirtualParticipantAdjuster().adjustByDecrement(activity.getId(), Long.parseLong(result.result));
                        new DualColoredBallRecordDao().add(activity.getDualColoredBallTerm(),
                                result.drawTime, result.result);
                        new LotteryResultUpdater(lotNumberFormat).update(activity.getId(), result.result);

                        // remove related thread after success
                        endThreads.remove(activity.getId());
                        break;
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                    LOGGER.error("Fail to crawl activity whose id is {}, info: {}", activity.getId(), e);
                }

                // sleep time becomes less and less on coming of expect end time
                Date expectEndTime = DateUtils.newDate(activity.getExpectEndTime());
                long timeDiff = expectEndTime.getTime() - System.currentTimeMillis();
                ActivityTerminatorUtils.sleep(timeDiff);
            }
        }

        private DualColoredBallCrawler.Result getResult() {
            if (crawlers == null) {
                return null;
            }
            if (!new DualColoredBallPhaseValidator().validate(activity.getDualColoredBallTerm())) {
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
