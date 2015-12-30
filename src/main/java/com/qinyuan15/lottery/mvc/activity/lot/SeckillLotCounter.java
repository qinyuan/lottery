package com.qinyuan15.lottery.mvc.activity.lot;

import com.qinyuan15.lottery.mvc.activity.lot.LotCounter;
import com.qinyuan15.lottery.mvc.dao.SeckillActivity;
import com.qinyuan15.lottery.mvc.dao.SeckillLotDao;

public class SeckillLotCounter implements LotCounter {
    /**
     * count the total lot number of certain seckill activity,
     * including virtual lot and real lot
     *
     * @param activity seckill activity to count
     * @return total lot number of the activity
     */
    public int count(SeckillActivity activity) {
        int realCount = countReal(activity.getId());
        Integer virtualCount = activity.getExpectParticipantCount();
        if (virtualCount == null || virtualCount < realCount) {
            return realCount;
        } else {
            return virtualCount;
        }
    }

    public int countReal(Integer activityId) {
        return SeckillLotDao.factory().setActivityId(activityId).getCount();
    }
}
