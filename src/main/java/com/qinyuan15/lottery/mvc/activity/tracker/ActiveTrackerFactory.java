package com.qinyuan15.lottery.mvc.activity.tracker;

import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan15.lottery.mvc.dao.LotteryLot;

import java.util.List;

class ActiveTrackerFactory {
    private final static int PAGE_SIZE = 100;
    private int activityId;
    private int pageNumber = 1;
    private int pointer = 0;
    private List<LotteryLot> lots;

    ActiveTrackerFactory(int activityId) {
        this.activityId = activityId;
        initLots();
    }

    Tracker next() {
        if (pointer - getFirstResultIndex() < lots.size()) {
            return createTrackerAndIncreasePointer();
        } else if (lots.size() < PAGE_SIZE) {
            return null;
        } else {
            pageNumber++;
            initLots();
            return next();
        }
    }

    private Tracker createTrackerAndIncreasePointer() {
        Tracker tracker = Tracker.activeInstance(lots.get(pointer - getFirstResultIndex()));
        pointer++;
        return tracker;
    }


    private synchronized void initLots() {
        lots = new HibernateListBuilder().addEqualFilter("activityId", activityId)
                .addFilter("virtual=true").addFilter("userId IN (SELECT id FROM VirtualUser WHERE active=true)")
                .limit(getFirstResultIndex(), PAGE_SIZE).build(LotteryLot.class);
    }

    private int getFirstResultIndex() {
        return (pageNumber - 1) * PAGE_SIZE;
    }
}
