package com.qinyuan15.lottery.mvc.activity.tracker;

import com.qinyuan15.lottery.mvc.dao.LotteryLot;
import com.qinyuan15.lottery.mvc.dao.VirtualUser;

import java.util.ArrayList;
import java.util.List;

class PreTracker {
    private final int activityId;
    private final int virtualUserId;
    private final boolean active;

    private PreTracker(int activityId, int virtualUserId, boolean active) {
        this.activityId = activityId;
        this.virtualUserId = virtualUserId;
        this.active = active;
    }

    void takeLot() {
        List<LotteryLot> noTrackLots = new RealLotMonitor(activityId).getNoTrackLots();
    }

    static List<PreTracker> build(List<VirtualUser> virtualUsers, int activityId) {
        List<PreTracker> preTrackers = new ArrayList<>();
        for (VirtualUser virtualUser : virtualUsers) {
            preTrackers.add(new PreTracker(activityId, virtualUser.getId(), virtualUser.getActive()));
        }
        return preTrackers;
    }

    @Override
    public String toString() {
        return "activity:" + activityId + ",userId:" + virtualUserId + ",active:" + active;
    }
}
