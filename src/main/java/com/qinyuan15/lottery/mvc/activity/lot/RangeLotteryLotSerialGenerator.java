package com.qinyuan15.lottery.mvc.activity.lot;

import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan15.lottery.mvc.activity.lot.LotteryLotSerialGenerator;
import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import org.apache.commons.lang3.RandomUtils;

public class RangeLotteryLotSerialGenerator implements LotteryLotSerialGenerator {
    public final static int DEFAULT_MIN_SERIAL_NUMBER = 1000;
    public final static int DEFAULT_MAX_SERIAL_NUMBER = 100000;

    private int minSerialNumber;
    private int maxSerialNumber;

    public RangeLotteryLotSerialGenerator() {
        updateSerialNumberRange(null);
    }

    private void updateSerialNumberRange(LotteryActivity activity) {
        if (activity == null) {
            minSerialNumber = DEFAULT_MIN_SERIAL_NUMBER;
            maxSerialNumber = DEFAULT_MAX_SERIAL_NUMBER;
        } else {
            /*minSerialNumber = activity.getMinSerialNumber();
            maxSerialNumber = activity.getMaxSerialNumber();*/

            if (!IntegerUtils.isPositive(minSerialNumber)) {
                minSerialNumber = DEFAULT_MIN_SERIAL_NUMBER;
            }
            if (!IntegerUtils.isPositive(maxSerialNumber)) {
                maxSerialNumber = DEFAULT_MAX_SERIAL_NUMBER;
            }

            if (minSerialNumber > maxSerialNumber) {
                int temp = minSerialNumber;
                minSerialNumber = maxSerialNumber;
                maxSerialNumber = temp;
            }
        }
    }

    public void setActivity(LotteryActivity activity) {
        updateSerialNumberRange(activity);
    }

    @Override
    public int next() {
        return RandomUtils.nextInt(minSerialNumber, maxSerialNumber + 1);
    }
}
