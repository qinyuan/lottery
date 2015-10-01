package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan15.lottery.mvc.dao.InvalidLotteryLotDao;
import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.LotteryActivityDao;
import com.qinyuan15.lottery.mvc.dao.LotteryLotDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class to change virtual participants to make sure that
 * no real participant win the lottery
 */
public class VirtualParticipantAdjuster {
    private final static Logger LOGGER = LoggerFactory.getLogger(VirtualParticipantAdjuster.class);

    private void adjust(int activityId, long result, int step) {
        LotteryActivity activity = new LotteryActivityDao().getInstance(activityId);
        if (activity == null) {
            LOGGER.error("invalid activityId: {}", activityId);
            return;
        }
        Set<Integer> lots = getLotNumbers(activityId);
        int count = getTotalParticipantCount(activity, lots);

        if (count == 0 || count > result) {// this should not happen in normal situation
            return;
        }

        int retryTimes = 4000, virtualParticipantToAdd = 0;
        WinnerCalculator winnerCalculator = new WinnerCalculator();
        while ((--retryTimes) >= 0) {
            int totalCount = count + virtualParticipantToAdd;
            if (totalCount <= 0) {
                break;
            }
            if (totalCount > 0 && !lots.contains(winnerCalculator.run(result, totalCount))) {
                new VirtualParticipantCreator().create(activityId, virtualParticipantToAdd);
                break;
            }
            virtualParticipantToAdd = virtualParticipantToAdd + step;
        }
    }

    public void adjustByDecrement(int activityId, long result) {
        adjust(activityId, result, -1);
    }

    public void adjustByIncrement(int activityId, long result) {
        adjust(activityId, result, 1);
    }

    private Set<Integer> getLotNumbers(int activityId) {
        // get real participants and their serial number
        List<Integer> lots = new LotteryLotDao().getSerialNumbers(activityId);
        lots.removeAll(new InvalidLotteryLotDao().getSerialNumbers(activityId));
        return new HashSet<>(lots);
    }

    private int getTotalParticipantCount(LotteryActivity activity, Collection<Integer> lots) {
        return getVirtualParticipantCount(activity) + lots.size();
    }

    private int getVirtualParticipantCount(LotteryActivity activity) {
        Integer virtualParticipantCount = activity.getVirtualParticipants();
        return IntegerUtils.isPositive(virtualParticipantCount) ? virtualParticipantCount : 0;
    }
}
