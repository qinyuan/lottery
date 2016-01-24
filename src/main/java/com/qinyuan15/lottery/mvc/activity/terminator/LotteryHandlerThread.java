package com.qinyuan15.lottery.mvc.activity.terminator;

import com.qinyuan15.lottery.mvc.dao.LotteryActivity;

import java.util.Map;

abstract class LotteryHandlerThread extends Thread {
    protected LotteryActivity activity;
    private final Map<Integer, ? extends Thread> threads;

    LotteryHandlerThread(LotteryActivity activity, Map<Integer, ? extends Thread> threads) {
        this.activity = activity;
        this.threads = threads;
    }

    public void setActivity(LotteryActivity activity) {
        this.activity = activity;
    }

    @Override
    public void run() {
        while (doLoop()) {
            ActivityTerminatorUtils.sleep(getSleepTime());
        }
        threads.remove(activity.getId());
    }

    /**
     * @return false if loop is completed, else return true
     */
    protected abstract boolean doLoop();

    protected abstract long getSleepTime();
}
