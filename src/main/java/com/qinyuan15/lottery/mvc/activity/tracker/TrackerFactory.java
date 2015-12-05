package com.qinyuan15.lottery.mvc.activity.tracker;

import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan15.lottery.mvc.dao.VirtualUser;
import com.qinyuan15.lottery.mvc.dao.VirtualUserDao;

import java.util.List;

class TrackerFactory {
    private int activityId;

    TrackerFactory(int activityId) {
        this.activityId = activityId;
    }

    List<PreTracker> createActivePreTrackers(int size) {
        String filter = "id NOT IN (SELECT userId FROM LotteryLot WHERE activityId=" + activityId + " AND virtual=True)";
        List<VirtualUser> activeVirtualUsers = new HibernateListBuilder().addFilter(filter)
                .addEqualFilter("active", true).addOrder("rand()", true).limit(0, size).build(VirtualUser.class);

        if (activeVirtualUsers.size() < size) {
            List<VirtualUser> ambiguousVirtualUsers = new HibernateListBuilder().addFilter(filter)
                    .addFilter("active IS NULL").addOrder("rand()", true).limit(0, size - activeVirtualUsers.size())
                    .build(VirtualUser.class);
            new VirtualUserDao().activate(ambiguousVirtualUsers);
            activeVirtualUsers.addAll(ambiguousVirtualUsers);
        }

        return PreTracker.build(activeVirtualUsers);
    }

    List<PreTracker> createInactivePreTrackers(int size) {
        return null;
    }

    List<Tracker> getOnJobActiveTrackers() {
        return null;
    }
}
