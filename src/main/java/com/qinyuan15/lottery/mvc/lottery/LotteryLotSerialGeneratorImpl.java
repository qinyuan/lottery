package com.qinyuan15.lottery.mvc.lottery;

import com.qinyuan15.lottery.mvc.dao.LotteryActivityDao;
import com.qinyuan15.utils.IntegerUtils;

public class LotteryLotSerialGeneratorImpl implements LotteryLotSerialGenerator {
    private final Integer activityId;

    public LotteryLotSerialGeneratorImpl(Integer activityId) {
        this.activityId = activityId;
    }

    @Override
    public synchronized int next() {
        Integer maxSerialNumber = new LotteryActivityDao().getMaxSerialNumber(this.activityId);
        if (IntegerUtils.isPositive(maxSerialNumber)) {
            return maxSerialNumber;
        } else {
            return 1;
        }
    }
}
