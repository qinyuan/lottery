package com.qinyuan15.lottery.mvc.lottery;

import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.LotteryLotDao;

/**
 * Class to count lottery lot of certain lottery activity
 */
public class LotteryLotCounter {
    public int count(LotteryActivity activity) {
        Integer count = activity.getVirtualParticipants();
        return count == null ? realCount(activity.getId()) : count + realCount(activity.getId());
    }

    public int realCount(Integer activityId) {
        return LotteryLotDao.factory().setActivityId(activityId).getCount();
    }
}
