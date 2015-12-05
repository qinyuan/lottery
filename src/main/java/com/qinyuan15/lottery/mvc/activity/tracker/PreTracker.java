package com.qinyuan15.lottery.mvc.activity.tracker;

import com.qinyuan15.lottery.mvc.dao.VirtualUser;

import java.util.ArrayList;
import java.util.List;

class PreTracker {
    private final boolean active;
    private final int virtualUserId;

    private PreTracker(int virtualUserId, boolean active) {
        this.virtualUserId = virtualUserId;
        this.active = active;
    }

    void takeLot() {
    }

    static List<PreTracker> build(List<VirtualUser> virtualUsers) {
        List<PreTracker> preTrackers = new ArrayList<>();
        for (VirtualUser virtualUser : virtualUsers) {
            preTrackers.add(new PreTracker(virtualUser.getId(), virtualUser.getActive()));
        }
        return preTrackers;
    }

    @Override
    public String toString() {
        return virtualUserId + ":" + active;
    }
}
