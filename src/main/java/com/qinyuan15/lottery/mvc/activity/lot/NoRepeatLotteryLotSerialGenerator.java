package com.qinyuan15.lottery.mvc.activity.lot;

import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.LotteryLotDao;

import java.util.List;

public class NoRepeatLotteryLotSerialGenerator extends CommonLotteryLotSerialGenerator {
    private List<Integer> serialNumbers;

    public NoRepeatLotteryLotSerialGenerator(LotteryLotNumberValidator numberValidator, LotteryLotSerialGenerator serialGenerator) {
        super(numberValidator, serialGenerator);
    }

    @Override
    public void setActivity(LotteryActivity activity) {
        if (activity != null && IntegerUtils.isPositive(activity.getId())) {
            this.serialNumbers = new LotteryLotDao().getVirtualSerialNumbers(activity.getId());
        }
        super.setActivity(activity);
    }

    @Override
    public synchronized int next() {
        int retryTimes = 5;
        while (true) {
            int serialNumber = super.next();

            // if number is valid, return it
            if (!serialNumbers.contains(serialNumber)) {
                return serialNumber;
            }

            if (--retryTimes <= 0) {
                return serialNumber;
            }
        }
    }
}
