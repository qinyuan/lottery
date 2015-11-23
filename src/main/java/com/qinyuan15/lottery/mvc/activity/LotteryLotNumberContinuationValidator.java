package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.LotteryLotDao;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class LotteryLotNumberContinuationValidator implements LotteryLotNumberValidator {
    @Override
    public Pair<Boolean, String> validate(LotteryActivity activity, int serialNumber) {
        Integer continuousSerialLimit = activity.getContinuousSerialLimit();
        if (!IntegerUtils.isPositive(continuousSerialLimit)) {
            return Pair.of(true, "");
        }

        int start = serialNumber - continuousSerialLimit;
        int end = serialNumber + continuousSerialLimit;
        List<Integer> serialNumbers = new LotteryLotDao().getSerialNumbers(activity.getId(), start, end);
        if (IntegerUtils.getMaxContinuationSize(serialNumbers) <= continuousSerialLimit) {
            return Pair.of(true, "");
        } else {
            return Pair.of(false, "此抽奖号已经被使用");
        }
    }
}
