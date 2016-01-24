package com.qinyuan15.lottery.mvc.activity.terminator;

import com.qinyuan15.lottery.mvc.dao.LotteryActivity;

import java.util.ArrayList;
import java.util.List;

abstract class LotteryHandlerThread extends Thread {
    protected LotteryActivity activity;
    private final List<LotteryHandlerThreadObserver> observers = new ArrayList<>();

    LotteryHandlerThread(LotteryActivity activity) {
        this.activity = activity;
    }

    public void setActivity(LotteryActivity activity) {
        this.activity = activity;
    }

    public void addObserver(LotteryHandlerThreadObserver observer) {
        observers.add(observer);
    }

    @Override
    public void run() {
        while (doLoop()) {
            ActivityTerminatorUtils.sleep(getSleepTime());
        }
        for (LotteryHandlerThreadObserver observer : observers) {
            observer.update(activity.getId());
        }
    }

    /**
     * @return false if loop is completed, else return true
     */
    protected abstract boolean doLoop();

    protected abstract long getSleepTime();
}
