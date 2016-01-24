package com.qinyuan15.lottery.mvc.activity.terminator;

import com.qinyuan.lib.lang.concurrent.ThreadUtils;
import com.qinyuan.lib.lang.time.DateUtils;
import com.qinyuan15.lottery.mvc.activity.dualcoloredball.DualColoredBallCrawler;
import com.qinyuan15.lottery.mvc.activity.tracker.WinnerManager;
import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.LotteryActivityDao;
import com.qinyuan15.lottery.mvc.dao.LotterySameLotDao;
import com.qinyuan15.lottery.mvc.dao.LotteryWinnerLivenessDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LotteryActivityTerminator {
    private final static Logger LOGGER = LoggerFactory.getLogger(LotteryActivityTerminator.class);
    private final Map<Integer, LotteryResultThread> resultThreads = new HashMap<>();
    private final Map<Integer, LotteryCloseThread> closeThreads = new HashMap<>();
    private List<DualColoredBallCrawler> crawlers;

    public void init() {
        new CloseThreadScheduler().start();
        new ResultThreadScheduler().start();
        new WinnerThreadScheduler().start();
    }

    public void setCrawlers(List<DualColoredBallCrawler> crawlers) {
        this.crawlers = crawlers;
    }

    /**
     * Scheduler to close activities in suitable time
     */
    private class CloseThreadScheduler extends Thread {
        final static int INTERVAL = 60; // reload each minute

        @Override
        public void run() {
            while (true) {
                ThreadUtils.sleep(INTERVAL);
                try {
                    // build crawl thread for each unclosed lottery activity
                    for (LotteryActivity activity : new LotteryActivityDao().getUnclosedInstances()) {
                        LotteryCloseThread thread = closeThreads.get(activity.getId());
                        if (thread == null) {
                            // if related thread not close, create and run it
                            thread = new LotteryCloseThread(activity, closeThreads);
                            closeThreads.put(activity.getId(), thread);
                            thread.start();
                        } else {
                            // if related thread already exists, just update activity data of it
                            thread.activity = activity;
                        }
                    }
                } catch (Throwable e) {
                    LOGGER.error("error in running CloseThreadScheduler: {}", e);
                }
            }
        }
    }

    private class ResultThreadScheduler extends Thread {
        final static int INTERVAL = 60; // sleep each minute

        @Override
        public void run() {
            while (true) {
                ThreadUtils.sleep(INTERVAL);
                try {
                    for (LotteryActivity activity : new LotteryActivityDao().getNoResultInstances()) {
                        LotteryResultThread thread = resultThreads.get(activity.getId());
                        if (thread == null) {
                            // if related thread not exists, create and run it
                            thread = new LotteryResultThread(activity, resultThreads, crawlers);
                            resultThreads.put(activity.getId(), thread);
                            thread.start();
                        } else {
                            // if related thread already exists, just update activity data of it
                            thread.activity = activity;
                        }
                    }
                } catch (Throwable e) {
                    LOGGER.error("error in running ResultThreadScheduler: {}", e);
                }
            }
        }
    }

    private class WinnerLivenessThreadScheduler extends Thread {
        final static int INTERVAL = 30; // sleep each 0.5 minute

        @Override
        public void run() {
            while (true) {
                for (LotteryActivity activity : new LotteryActivityDao().getNoWinnerResultInstances()) {
                    if (activity.getExpectEndTime().compareTo(DateUtils.nowString()) < 0) {
                        continue;
                    }

                    String winnerNumber = new WinnerManager().getWinnerNumber(activity);
                    if (winnerNumber == null) {
                        continue;
                    }

                    for (LotterySameLotDao.User user : new LotterySameLotDao().getUsers(activity.getId(), winnerNumber)) {
                        new LotteryWinnerLivenessDao().add(activity.getId(), user.userId, user.virtual, user.liveness);
                    }
                }
                ThreadUtils.sleep(INTERVAL);
            }
        }
    }

    private class WinnerThreadScheduler extends Thread {
        final static int INTERVAL = 60; // sleep each minute

        @Override
        public void run() {
            WinnerManager winnerManager = new WinnerManager();
            while (true) {
                for (LotteryActivity activity : new LotteryActivityDao().getUnexpiredWithResultInstances()) {
                    winnerManager.setWinner(activity);
                }
                ThreadUtils.sleep(INTERVAL);
            }
        }
    }

    /*private class LotteryCloseThread extends LotteryHandlerThread {
        LotteryCloseThread(LotteryActivity activity) {
            super(activity, closeThreads);
        }

        @Override
        protected boolean doLoop() {
            if (!DateUtils.isDateOrDateTime(activity.getCloseTime())) {
                return false;
            }

            try {
                if (LotteryActivityUtils.shouldBeClosed(activity)) {
                    new LotteryActivityDao().close(activity);
                    closeThreads.remove(activity.getId());
                    return false;
                }
                new LotteryLotCounter().count(activity);    // force program to update virtual participants
            } catch (Throwable e) {
                e.printStackTrace();
                LOGGER.error("Fail to close activity whose id is {}, info: {}", activity.getId(), e);
            }
            return true;
        }

        @Override
        protected long getSleepTime() {
            return LotteryActivityUtils.getRemainingTimeToClose(activity);
        }
    }*/
}
