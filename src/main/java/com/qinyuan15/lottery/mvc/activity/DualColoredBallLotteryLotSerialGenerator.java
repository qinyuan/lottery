package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Generate serial number by three dualColoredBall number
 * Created by qinyuan on 15-11-23.
 */
public class DualColoredBallLotteryLotSerialGenerator implements LotteryLotSerialGenerator {
    private final static int MIN_DUAL_COLORED_BALL_NUMBER = 1;
    private final static int MAX_DUAL_COLORED_BALL_NUMBER = 33;

    @Override
    public int next() {
        List<Integer> numbers = new ArrayList<>();
        while (numbers.size() < 3) {
            int singleBall = nextSingleBall();
            if (!numbers.contains(singleBall)) {
                numbers.add(singleBall);
            }
        }
        Collections.sort(numbers);

        int result = 0;
        for (int number : numbers) {
            result = result * 100 + number;
        }
        return result;
    }

    public int nextSingleBall() {
        return RandomUtils.nextInt(MIN_DUAL_COLORED_BALL_NUMBER, MAX_DUAL_COLORED_BALL_NUMBER + 1);
    }

    @Override
    public void setActivity(LotteryActivity activity) {
        // nothing to do
    }
}
