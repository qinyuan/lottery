package com.qinyuan15.lottery.mvc.lottery;

import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.LotteryActivityDao;
import com.qinyuan15.lottery.mvc.dao.LotteryLotDao;

/**
 * Class to count lottery lot of certain lottery activity
 */
public class LotteryLotCounter {
    public int count(LotteryActivity activity) {
        Integer count = activity.getVirtualParticipants();
        if (count == null) {
            count = realCount(activity.getId());
        } else {
            count += realCount(activity.getId());
        }

        if (activity.getExpire()) {
            return count;
        }

        ExpectParticipantsDivider participantsDivider = new ExpectParticipantsDivider(
                activity.getStartTime(), activity.getExpectEndTime(), activity.getExpectParticipantCount());
        int currentExpectParticipantCount = participantsDivider.getCurrentExpectValue();
        if (currentExpectParticipantCount > count && !new LotteryActivityDao().isExpire(activity.getId())) {
            new VirtualParticipantCreator().create(activity.getId(),
                    currentExpectParticipantCount - count);
            return currentExpectParticipantCount;
        } else {
            return count;
        }
    }

    public int realCount(Integer activityId) {
        return LotteryLotDao.factory().setActivityId(activityId).getCount();
    }
}
