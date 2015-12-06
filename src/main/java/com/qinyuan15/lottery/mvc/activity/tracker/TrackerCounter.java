package com.qinyuan15.lottery.mvc.activity.tracker;

import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan15.lottery.mvc.dao.LotteryLot;

class TrackerCounter {
    private int activityId;

    TrackerCounter(int activityId) {
        this.activityId = activityId;
    }

    int countActive() {
        return count(true);
    }

    int countInactive() {
        return count(false);
    }

    private int count(boolean active) {
        return new HibernateListBuilder().addEqualFilter("activityId", activityId).addFilter("virtual=true")
                .addFilter("userId IN (SELECT id FROM VirtualUser WHERE active=:active)")
                .addArgument("active", active).count(LotteryLot.class);
    }
}
