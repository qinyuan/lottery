package com.qinyuan15.lottery.mvc.activity.tracker;

import com.qinyuan15.lottery.mvc.activity.lot.LotteryLotSerialGenerator;
import com.qinyuan15.lottery.mvc.dao.LotteryLotDao;
import com.qinyuan15.lottery.mvc.dao.VirtualUser;

import java.util.ArrayList;
import java.util.List;

class PreTracker {
    private final int activityId;
    private final int virtualUserId;
    private final boolean active;
    private final LotteryLotSerialGenerator serialGenerator;

    private PreTracker(int activityId, int virtualUserId, boolean active, LotteryLotSerialGenerator serialGenerator) {
        this.activityId = activityId;
        this.virtualUserId = virtualUserId;
        this.active = active;
        this.serialGenerator = serialGenerator;
    }

    Integer takeLot() {
        /*
        if (active) {
            List<LotteryLot> noTrackLots = new RealLotMonitor(activityId).getNoTrackLots();
            if (noTrackLots.size() > 0) {
                LotteryLot lot = RandomUtils.getOne(noTrackLots);
                return takeLot(lot.getSerialNumber());
            } else {
                return takeLot(serialGenerator.next());
            }
        } else {
            return takeLot(serialGenerator.next());
        }
        */
        return takeLot(serialGenerator.next());
    }

    private Integer takeLot(int serialNumber) {
        return new LotteryLotDao().add(activityId, virtualUserId, serialNumber, true);
    }

    static List<PreTracker> build(List<VirtualUser> virtualUsers, int activityId,
                                  LotteryLotSerialGenerator serialGenerator) {
        List<PreTracker> preTrackers = new ArrayList<>();
        for (VirtualUser virtualUser : virtualUsers) {
            preTrackers.add(new PreTracker(activityId, virtualUser.getId(), virtualUser.getActive(), serialGenerator));
        }
        return preTrackers;
    }

    @Override
    public String toString() {
        return "activity:" + activityId + ",userId:" + virtualUserId + ",active:" + active;
    }
}
