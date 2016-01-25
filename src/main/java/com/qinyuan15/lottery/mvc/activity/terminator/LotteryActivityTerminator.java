package com.qinyuan15.lottery.mvc.activity.terminator;

import com.qinyuan.lib.lang.concurrent.ThreadUtils;
import com.qinyuan.lib.lang.time.DateUtils;
import com.qinyuan15.lottery.mvc.activity.dualcoloredball.DualColoredBallCrawler;
import com.qinyuan15.lottery.mvc.activity.tracker.WinnerManager;
import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.LotteryActivityDao;
import com.qinyuan15.lottery.mvc.dao.LotterySameLotDao;
import com.qinyuan15.lottery.mvc.dao.LotteryWinnerLivenessDao;

import java.util.List;

public class LotteryActivityTerminator {
    private List<DualColoredBallCrawler> crawlers;

    public void init() {
        new CloseThreadScheduler().start();
        new ResultThreadScheduler().start();
        new WinnerThreadScheduler().start();
        new WinnerLivenessThreadScheduler().start();
    }

    public void setCrawlers(List<DualColoredBallCrawler> crawlers) {
        this.crawlers = crawlers;
    }

    /**
     * Scheduler to close activities in suitable time
     */
    private class CloseThreadScheduler extends LotteryHandlerScheduler {
        @Override
        protected LotteryHandlerThread buildLotteryHandlerThread(LotteryActivity activity) {
            return new LotteryCloseThread(activity);
        }

        @Override
        protected List<LotteryActivity> getActivities() {
            return new LotteryActivityDao().getUnclosedInstances();
        }
    }

    private class ResultThreadScheduler extends LotteryHandlerScheduler {
        @Override
        protected LotteryHandlerThread buildLotteryHandlerThread(LotteryActivity activity) {
            return new LotteryResultThread(activity, crawlers);
        }

        @Override
        protected List<LotteryActivity> getActivities() {
            return new LotteryActivityDao().getNoResultInstances();
        }
    }

    private class WinnerLivenessThreadScheduler extends Thread {
        final static int INTERVAL = 30; // sleep each 0.5 minute

        @Override
        public void run() {
            while (true) {
                for (LotteryActivity activity : new LotteryActivityDao().getNoWinnerRecordInstances()) {
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
                // make sure that winner of each unexpired activity with result is virtual user
                for (LotteryActivity activity : new LotteryActivityDao().getUnexpiredWithResultInstances()) {
                    winnerManager.setWinner(activity);
                }
                ThreadUtils.sleep(INTERVAL);
            }
        }
    }
}
