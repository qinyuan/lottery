package com.qinyuan15.lottery.mvc.activity.tracker;

import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan15.lottery.mvc.dao.LotteryLot;

import java.util.List;

class TrackerFactory {
    private final static int PAGE_SIZE = 100;
    private final int activityId;
    private final Boolean active;
    private int pageNumber = 1;
    private int pointer = 0;
    private List<LotteryLot> lots;

    TrackerFactory(int activityId, Boolean active) {
        this.activityId = activityId;
        this.active = active;
        initLots();
    }

    TrackerFactory(int activityId) {
        this(activityId, null);
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
        Tracker tracker = new Tracker(lots.get(pointer - getFirstResultIndex()));
        pointer++;
        return tracker;
    }

    private synchronized void initLots() {
        String realLotSerialNumber = "(SELECT serialNumber FROM LotteryLot " +
                "WHERE activityId=" + activityId + " AND (virtual IS NULL OR virtual=false))";
        HibernateListBuilder listBuilder = new HibernateListBuilder().addFilter("activityId=" + activityId)
                .addFilter("virtual=true").addFilter("serialNumber IN " + realLotSerialNumber)
                .limit(getFirstResultIndex(), PAGE_SIZE);

        if (active != null) {
            listBuilder.addFilter("userId IN (SELECT id FROM VirtualUser WHERE active=:active)")
                    .addArgument("active", active);
        }

        lots = listBuilder.build(LotteryLot.class);
    }

    private int getFirstResultIndex() {
        return (pageNumber - 1) * PAGE_SIZE;
    }
}
