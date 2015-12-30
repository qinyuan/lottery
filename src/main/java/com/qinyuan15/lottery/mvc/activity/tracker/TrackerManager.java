package com.qinyuan15.lottery.mvc.activity.tracker;

import com.qinyuan15.lottery.mvc.activity.lot.CommonLotteryLotSerialGenerator;
import com.qinyuan15.lottery.mvc.activity.lot.LotteryLotNumberValidator;
import com.qinyuan15.lottery.mvc.activity.LotteryLotSerialGenerator;
import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.LotteryActivityDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TrackerManager {
    private final static Logger LOGGER = LoggerFactory.getLogger(TrackerManager.class);
    private static boolean running = false;
    private final CommonLotteryLotSerialGenerator serialGenerator;

    public TrackerManager(LotteryLotNumberValidator lotteryLotNumberValidator,
                          LotteryLotSerialGenerator lotteryLotSerialGenerator) {
        serialGenerator = new CommonLotteryLotSerialGenerator(lotteryLotNumberValidator, lotteryLotSerialGenerator);
    }

    public void run() {
        if (running) {
            LOGGER.warn("there are more than one tracker manager instance running, just skip");
            return; // if there is another instance running, just skip
        }

        running = true;

        try {
            List<LotteryActivity> activities = LotteryActivityDao.factory().setExpire(false).getInstances();
            for (LotteryActivity activity : activities) {
                if (isNotExpire(activity)) {
                    serialGenerator.setActivity(activity);
                    allocateActiveTrackers(activity);
                    allocateInactiveTrackers(activity);
                    runActiveTrackers(activity);
                    runInactiveTrackers(activity);
                }
            }
        } catch (Throwable e) {
            LOGGER.error("error in invoking TrackerManager#run, info: {}", e);
        }

        running = false;
    }

    private boolean isNotExpire(LotteryActivity activity) {
        activity = new LotteryActivityDao().getInstance(activity.getId());
        return activity != null && !activity.getExpire();
    }

    private void allocateActiveTrackers(LotteryActivity activity) {
        int trackerSize = new TrackerCounter(activity.getId()).countActive();
        int expectedTrackerSize = new ExpectedTrackerCalculator(activity).getExpectedActiveTrackerSize();
        if (trackerSize < expectedTrackerSize) {
            List<PreTracker> preTrackers = new PreTrackerFactory(activity.getId(), serialGenerator)
                    .createActivePreTrackers(expectedTrackerSize - trackerSize);
            takeLot(preTrackers);
        }
    }

    private void allocateInactiveTrackers(LotteryActivity activity) {
        int trackerSize = new TrackerCounter(activity.getId()).countInactive();
        int expectedTrackerSize = new ExpectedTrackerCalculator(activity).getExpectedInactiveTrackerSize();
        if (trackerSize < expectedTrackerSize) {
            List<PreTracker> preTrackers = new PreTrackerFactory(activity.getId(), serialGenerator)
                    .createInactivePreTrackers(expectedTrackerSize - trackerSize);
            takeLot(preTrackers);
        }
    }

    private void takeLot(List<PreTracker> preTrackers) {
        for (PreTracker preTracker : preTrackers) {
            preTracker.takeLot();
        }
    }

    private void runActiveTrackers(LotteryActivity activity) {
        TrackerFactory trackerFactory = new TrackerFactory(activity.getId(), true);
        Tracker tracker;
        while ((tracker = trackerFactory.next()) != null) {
            tracker.follow();
        }
    }

    private void runInactiveTrackers(LotteryActivity activity) {
        TrackerFactory trackerFactory = new TrackerFactory(activity.getId(), false);
        Tracker tracker;
        while ((tracker = trackerFactory.next()) != null) {
            tracker.follow();
        }
    }
}
