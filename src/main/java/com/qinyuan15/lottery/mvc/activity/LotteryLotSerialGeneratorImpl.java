package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan15.lottery.mvc.dao.LotteryActivityDao;
import com.qinyuan15.lottery.mvc.dao.LotteryLotDao;
import com.qinyuan15.utils.IntegerUtils;

public class LotteryLotSerialGeneratorImpl implements LotteryLotSerialGenerator {
    private final Integer activityId;
    private final Integer continuousSerialLimit;

    public LotteryLotSerialGeneratorImpl(Integer activityId, Integer continuousSerialLimit) {
        this.activityId = activityId;
        this.continuousSerialLimit = continuousSerialLimit;
    }

    @Override
    public synchronized int next() {
        LotteryActivityDao dao = new LotteryActivityDao();
        Integer maxSerialNumber = dao.getMaxSerialNumber(this.activityId);

        // if maxSerial is null or negative
        if (!IntegerUtils.isNotNegative(maxSerialNumber)) {
            dao.updateMaxSerialNumber(this.activityId, 1);
            return 1;
        }

        if (validateContinuousSerialNumber(maxSerialNumber)) {
            dao.increaseMaxSerialNumber(this.activityId);
            return maxSerialNumber + 1;
        } else {
            new VirtualParticipantCreator().create(this.activityId, continuousSerialLimit);
            return maxSerialNumber + continuousSerialLimit;
        }
    }

    private boolean validateContinuousSerialNumber(int maxSerialNumber) {
        if (!IntegerUtils.isPositive(continuousSerialLimit)) {
            return true;
        }

        int startSerialNumber = maxSerialNumber - continuousSerialLimit + 1;
        if (startSerialNumber <= 0) {
            return true;
        }

        int count = new LotteryLotDao().countBySerialNumberRange(this.activityId, startSerialNumber, maxSerialNumber);
        return count < continuousSerialLimit;
    }
}
