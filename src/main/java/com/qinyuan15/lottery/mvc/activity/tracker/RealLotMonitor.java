package com.qinyuan15.lottery.mvc.activity.tracker;

import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan15.lottery.mvc.dao.LotteryLot;

import java.util.List;

class RealLotMonitor {
    private final int activityId;

    RealLotMonitor(int activityId) {
        this.activityId = activityId;
    }

    List<LotteryLot> getNoTrackLots() {
        String oneLotSerial = "(SELECT serialNumber FROM LotteryLot GROUP BY serialNumber HAVING COUNT(*)=1)";
        return new HibernateListBuilder().addFilter("virtual=false OR virtual IS NULL")
                .addEqualFilter("activityId", activityId)
                .addFilter("serialNumber IN " + oneLotSerial)
                .build(LotteryLot.class);
    }
}
