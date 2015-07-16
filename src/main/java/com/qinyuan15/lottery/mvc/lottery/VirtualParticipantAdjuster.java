package com.qinyuan15.lottery.mvc.lottery;

import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.LotteryActivityDao;
import com.qinyuan15.lottery.mvc.dao.LotteryLotDao;

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
        Integer virtualParticipantCount = activity.getVirtualParticipants();
        if (virtualParticipantCount == null) {
            virtualParticipantCount = 0;
        }
        List<Integer> lots = new LotteryLotDao().getSerialNumbersByActivityId(activityId);
        Integer count = virtualParticipantCount + lots.size();

        if (count > result) {
            return;
        }

        Set<Integer> lotSet = new HashSet<>(lots);
        if (!lotSet.contains(count)) {
            return;
        }

        int retryTimes = 2000, virtualParticipantToAdd = 1;
        while ((--retryTimes) >= 0) {
            int winner = (int) (result % (count + virtualParticipantToAdd));
            if (!lotSet.contains(winner)) {
                new VirtualParticipantCreator().create(activityId, virtualParticipantToAdd);
                break;
            }
            virtualParticipantToAdd++;
        }
    }
}
