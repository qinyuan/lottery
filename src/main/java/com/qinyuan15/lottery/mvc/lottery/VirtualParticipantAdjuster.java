package com.qinyuan15.lottery.mvc.lottery;

import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.LotteryActivityDao;
import com.qinyuan15.lottery.mvc.dao.LotteryLotDao;
import com.qinyuan15.utils.IntegerUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class to change virtual participants to make sure that
 * no real participant win the lottery
 */
public class VirtualParticipantAdjuster {
    public void adjust(int activityId, long result) {
        LotteryActivity activity = new LotteryActivityDao().getInstance(activityId);

        // get virtual participants number
        Integer virtualParticipantCount = activity.getVirtualParticipants();
        if (!IntegerUtils.isPositive(virtualParticipantCount)) {
            virtualParticipantCount = 0;
        }

        // get real participants and their serial number
        List<Integer> lots = new LotteryLotDao().getSerialNumbersByActivityId(activityId);

        // calculate total participant numbers
        Integer count = virtualParticipantCount + lots.size();

        if (count > result) {// this should not happen in normal situation
            return;
        }

        Set<Integer> lotSet = new HashSet<>(lots);
        if (!lotSet.contains(count)) {
            return;
        }

        int retryTimes = 4000, virtualParticipantToAdd = 1;
        WinnerCalculator winnerCalculator = new WinnerCalculator();
        while ((--retryTimes) >= 0) {
            if (!lotSet.contains(winnerCalculator.run(result, count + virtualParticipantToAdd))) {
                new VirtualParticipantCreator().create(activityId, virtualParticipantToAdd);
                break;
            }
            virtualParticipantToAdd++;
        }
    }
}
