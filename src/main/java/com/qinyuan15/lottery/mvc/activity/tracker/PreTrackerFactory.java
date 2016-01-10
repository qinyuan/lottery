package com.qinyuan15.lottery.mvc.activity.tracker;

import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan15.lottery.mvc.activity.lot.LotteryLotSerialGenerator;
import com.qinyuan15.lottery.mvc.dao.VirtualUser;
import com.qinyuan15.lottery.mvc.dao.VirtualUserDao;

import java.util.List;

class PreTrackerFactory {
    private int activityId;
    private LotteryLotSerialGenerator serialGenerator;

    PreTrackerFactory(int activityId, LotteryLotSerialGenerator serialGenerator) {
        this.activityId = activityId;
        this.serialGenerator = serialGenerator;
    }

    private HibernateListBuilder getPreTrackerListBuilder() {
        // this filter excludes users already taken activity
        String filter = "id NOT IN (SELECT userId FROM LotteryLot WHERE activityId=" + activityId + " AND virtual=True)";

        return new HibernateListBuilder().addFilter(filter).addOrder("rand()", true);
    }

    List<PreTracker> create(int size) {
        List<VirtualUser> activeVirtualUsers = getPreTrackerListBuilder().addFilter("active IS NOT NULL")
                .limit(0, size).build(VirtualUser.class);

        if (activeVirtualUsers.size() < size) {
            List<VirtualUser> ambiguousVirtualUsers = getPreTrackerListBuilder().addFilter("active IS NULL")
                    .limit(0, size - activeVirtualUsers.size()).build(VirtualUser.class);
            new VirtualUserDao().activate(ambiguousVirtualUsers);
            activeVirtualUsers.addAll(ambiguousVirtualUsers);
        }

        return PreTracker.build(activeVirtualUsers, activityId, serialGenerator);
    }
}
