package com.qinyuan15.lottery.mvc.activity.tracker;

import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan15.lottery.mvc.dao.LotteryLot;

class TrackerCounter {
    private int activityId;

    TrackerCounter(int activityId) {
        this.activityId = activityId;
    }

    int count() {
        return count(null);
    }

    private int count(Boolean active) {
        HibernateListBuilder listBuilder = new HibernateListBuilder().addEqualFilter("activityId", activityId)
                .addFilter("virtual=true");
        if (active != null) {
            listBuilder.addFilter("userId IN (SELECT id FROM VirtualUser WHERE active=:active)")
                    .addArgument("active", active);
        }

        return listBuilder.count(LotteryLot.class);
    }
}
