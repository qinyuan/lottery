package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan15.lottery.mvc.dao.SeckillLotDao;

public class SeckillLotCounter implements LotCounter {
    public int countReal(Integer activityId) {
        return SeckillLotDao.factory().setActivityId(activityId).getCount();
    }
}
