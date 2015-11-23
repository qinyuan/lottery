package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan15.lottery.mvc.dao.LotteryActivity;

public class CommonLotteryLotSerialGenerator implements LotteryLotSerialGenerator {
    private LotteryActivity activity;
    private final LotteryLotNumberValidator numberValidator;
    private final LotteryLotSerialGenerator serialGenerator;

    public CommonLotteryLotSerialGenerator(LotteryLotNumberValidator numberValidator,
                                           LotteryLotSerialGenerator serialGenerator) {
        if (numberValidator == null) {
            throw new IllegalArgumentException("numberValidator is null");
        }
        if (serialGenerator == null) {
            throw new IllegalArgumentException("serialGenerator is null");
        }

        this.numberValidator = numberValidator;
        this.serialGenerator = serialGenerator;
    }

    @Override
    public void setActivity(LotteryActivity activity) {
        this.activity = activity;
        this.serialGenerator.setActivity(activity);
    }

    @Override
    public synchronized int next() {
        if (activity == null) {
            throw new IllegalStateException("activity is null");
        }

        int retryTimes = 20;
        while (true) {
            int serialNumber = serialGenerator.next();

            // if number is valid, return it
            if (numberValidator.validate(activity, serialNumber).getLeft()) {
                return serialNumber;
            }

            if (--retryTimes <= 0) {
                return serialNumber;
            }
        }
    }
}
