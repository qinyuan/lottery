package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import org.apache.commons.lang3.RandomUtils;

/**
 * Generate serial number by three dualColoredBall number
 * Created by qinyuan on 15-11-23.
 */
public class DualColoredBallLotteryLotSerialGenerator implements LotteryLotSerialGenerator {
    private final static int MIN_DUAL_COLORED_BALL_NUMBER = 1;
    private final static int MAX_DUAL_COLORED_BALL_NUMBER = 33;
    private final static int DUAL_COLORED_BALL_NUMBER_LENGTH = String.valueOf(MAX_DUAL_COLORED_BALL_NUMBER).length();

    @Override
    public int next() {
        // DecimalFormat  format = new DecimalFormat("00");
        int serialNumber = 0;
        int multiplier = (int) Math.pow(10, DUAL_COLORED_BALL_NUMBER_LENGTH);
        for (int i = 0; i < 3; i++) {
            serialNumber = serialNumber * multiplier + RandomUtils.nextInt(MIN_DUAL_COLORED_BALL_NUMBER,
                    MAX_DUAL_COLORED_BALL_NUMBER + 1);
        }
        return serialNumber;
    }

    @Override
    public void setActivity(LotteryActivity activity) {
        // nothing to do
    }
}
