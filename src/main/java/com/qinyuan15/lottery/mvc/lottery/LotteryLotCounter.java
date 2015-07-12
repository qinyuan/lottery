package com.qinyuan15.lottery.mvc.lottery;

import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.LotteryLotDao;

/**
 * Class to count lottery lot of certain lottery activity
 */
public class LotteryLotCounter {
    public Integer count(LotteryActivity activity) {
        Integer count = activity.getVirtualParticipants();
        count += LotteryLotDao.factory().setActivityId(activity.getId()).getCount();
        return count;
    }
}
