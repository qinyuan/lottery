package com.qinyuan15.lottery.mvc.activity.tracker;

import com.qinyuan15.lottery.mvc.activity.lot.LotteryLotSerialGenerator;
import com.qinyuan15.lottery.mvc.dao.LotteryLotDao;
import com.qinyuan15.lottery.mvc.dao.VirtualUser;
import com.qinyuan15.lottery.mvc.dao.VirtualUserDao;
import org.apache.commons.lang3.RandomUtils;

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
        return takeLot(serialGenerator.next());
    }

    private Integer takeLot(int serialNumber) {
        return new LotteryLotDao().add(activityId, virtualUserId, serialNumber, true);
    }

    static List<PreTracker> build(List<VirtualUser> virtualUsers, int activityId,
                                  LotteryLotSerialGenerator serialGenerator) {
        List<PreTracker> preTrackers = new ArrayList<>();
        for (VirtualUser virtualUser : virtualUsers) {
            initLivenessIfNecessary(virtualUser);
            preTrackers.add(new PreTracker(activityId, virtualUser.getId(), virtualUser.getActive(), serialGenerator));
        }
        return preTrackers;
    }

    private final static int MAX_INIT_LIVENESS = 10;
    private final static int MIN_INIT_LIVENESS = 0;

    private static void initLivenessIfNecessary(VirtualUser virtualUser) {
        if (virtualUser.getLiveness() != null) {
            return;
        }

        int liveness = RandomUtils.nextInt(MIN_INIT_LIVENESS, MAX_INIT_LIVENESS + 1);
        new VirtualUserDao().changeLiveness(virtualUser, liveness);
    }

    @Override
    public String toString() {
        return "activity:" + activityId + ",userId:" + virtualUserId + ",active:" + active;
    }
}
