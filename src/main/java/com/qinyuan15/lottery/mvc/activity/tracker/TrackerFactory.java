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

    private HibernateListBuilder getPreTrackerListBuilder() {
        String filter = "id NOT IN (SELECT userId FROM LotteryLot WHERE activityId=" + activityId + " AND virtual=True)";
        return new HibernateListBuilder().addFilter(filter).addOrder("rand()", true);
    }

    List<PreTracker> createActivePreTrackers(int size) {
        List<VirtualUser> activeVirtualUsers = getPreTrackerListBuilder().addEqualFilter("active", true)
                .limit(0, size).build(VirtualUser.class);

        if (activeVirtualUsers.size() < size) {
            List<VirtualUser> ambiguousVirtualUsers = getPreTrackerListBuilder().addFilter("active IS NULL")
                    .limit(0, size - activeVirtualUsers.size()).build(VirtualUser.class);
            new VirtualUserDao().activate(ambiguousVirtualUsers);
            activeVirtualUsers.addAll(ambiguousVirtualUsers);
        }

        return PreTracker.build(activeVirtualUsers);
    }

    List<PreTracker> createInactivePreTrackers(int size) {
        List<VirtualUser> inactiveVirtualUsers = getPreTrackerListBuilder().addEqualFilter("active", false)
                .limit(0, size).build(VirtualUser.class);

        if (inactiveVirtualUsers.size() < size) {
            List<VirtualUser> ambiguousVirtualUsers = getPreTrackerListBuilder().addFilter("active IS NULL")
                    .limit(0, size - inactiveVirtualUsers.size()).build(VirtualUser.class);
            new VirtualUserDao().deactivate(ambiguousVirtualUsers);
            inactiveVirtualUsers.addAll(ambiguousVirtualUsers);
        }

        return PreTracker.build(inactiveVirtualUsers);
    }

    List<Tracker> getOnJobActiveTrackers() {
        return null;
    }
}
