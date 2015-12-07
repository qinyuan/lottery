package com.qinyuan15.lottery.mvc.activity.tracker;

import com.qinyuan15.lottery.mvc.dao.LotteryLot;

class Tracker {
    private final int activityId;
    private final int virtualUserId;
    private final boolean active;

    private Tracker(int activityId, int virtualUserId, boolean active) {
        this.activityId = activityId;
        this.virtualUserId = virtualUserId;
        this.active = active;
    }

    void follow() {

    }

    void exceed() {
    }

    @Override
    public String toString() {
        return "activity:" + activityId + ",userId:" + virtualUserId + ",active:" + active;
    }

    static Tracker activeInstance(LotteryLot lot) {
        return new Tracker(lot.getActivityId(), lot.getUserId(), true);
    }
}
