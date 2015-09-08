package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.LotteryLotDao;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;

public class LotteryLotSerialGeneratorImpl implements LotteryLotSerialGenerator {
    public final static int DEFAULT_MIN_SERIAL_NUMBER = 1000;
    public final static int DEFAULT_MAX_SERIAL_NUMBER = 100000;

    private final LotteryActivity activity;

    public LotteryLotSerialGeneratorImpl(LotteryActivity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("activity is null");
        }

        this.activity = activity;
    }

    @Override
    public synchronized int next() {
        Integer minSerialNumber = activity.getMinSerialNumber();
        if (!IntegerUtils.isPositive(minSerialNumber)) {
            minSerialNumber = DEFAULT_MIN_SERIAL_NUMBER;
        }

        Integer maxSerialNumber = activity.getMaxSerialNumber();
        if (!IntegerUtils.isPositive(maxSerialNumber)) {
            maxSerialNumber = DEFAULT_MAX_SERIAL_NUMBER;
        }

        if (minSerialNumber > maxSerialNumber) {
            int temp = minSerialNumber;
            minSerialNumber = maxSerialNumber;
            maxSerialNumber = temp;
        }

        int nextSerialNumber = RandomUtils.nextInt(minSerialNumber, maxSerialNumber + 1);
        int retryTimes = 20;
        while ((--retryTimes) >= 0 && !validateSerialNumberContinuation(activity, nextSerialNumber)) {
            nextSerialNumber = RandomUtils.nextInt(minSerialNumber, maxSerialNumber + 1);
        }
        return nextSerialNumber;
        /*
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
        */
    }

    private boolean validateSerialNumberContinuation(LotteryActivity activity, int newSerialNumber) {
        Integer continuousSerialLimit = activity.getContinuousSerialLimit();
        if (!IntegerUtils.isPositive(continuousSerialLimit)) {
            return true;
        }

        int start = newSerialNumber - continuousSerialLimit;
        int end = newSerialNumber + continuousSerialLimit;
        List<Integer> serialNumbers = new LotteryLotDao().getSerialNumbers(activity.getId(), start, end);
        return IntegerUtils.getMaxContinuationSize(serialNumbers) <= continuousSerialLimit;
    }

/*
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
    }*/
}
