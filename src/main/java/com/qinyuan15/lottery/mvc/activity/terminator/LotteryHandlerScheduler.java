package com.qinyuan15.lottery.mvc.activity.terminator;

import com.qinyuan.lib.lang.concurrent.ThreadUtils;
import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class LotteryHandlerScheduler extends Thread implements LotteryHandlerThreadObserver {

    public final static int DEFAULT_INTERVAL = 60; // reload each minute
    private final static Logger LOGGER = LoggerFactory.getLogger(LotteryHandlerScheduler.class);
    private final int interval;
    private final Map<Integer, LotteryHandlerThread> threads = new HashMap<>();

    public LotteryHandlerScheduler(int interval) {
        this.interval = interval;
    }

    public LotteryHandlerScheduler() {
        this(DEFAULT_INTERVAL);
    }

    @Override
    public void run() {
        while (true) {
            ThreadUtils.sleep(interval);
            try {
                // build crawl thread for each lottery activity
                for (LotteryActivity activity : getActivities()) {
                    LotteryHandlerThread thread = threads.get(activity.getId());
                    if (thread == null) {
                        // if related thread not close, create and run it
                        thread = buildLotteryHandlerThread(activity);
                        thread.addObserver(this);
                        thread.start();

                        threads.put(activity.getId(), thread);
                    } else {
                        // if related thread already exists, just update activity data of it
                        thread.activity = activity;
                    }
                }
            } catch (Throwable e) {
                LOGGER.error("error in running LotteryHandlerScheduler, implement: {}, info {}",
                        this.getClass().getName(), e);
            }
        }
    }

    protected abstract LotteryHandlerThread buildLotteryHandlerThread(LotteryActivity activity);

    protected abstract List<LotteryActivity> getActivities();

    @Override
    public void update(int activityId) {
        threads.remove(activityId);
    }
}
