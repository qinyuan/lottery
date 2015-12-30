package com.qinyuan15.lottery.mvc.activity.lot;

import com.qinyuan15.lottery.mvc.activity.dualcoloredball.DualColoredBallUtils;
import com.qinyuan15.lottery.mvc.dao.LotteryActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Generate serial number by three dualColoredBall number
 * Created by qinyuan on 15-11-23.
 */
public class DualColoredBallLotteryLotSerialGenerator implements LotteryLotSerialGenerator {

    @Override
    public int next() {
        List<Integer> numbers = new ArrayList<>();
        while (numbers.size() < 3) {
            int singleBall = DualColoredBallUtils.rand();
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

    @Override
    public void setActivity(LotteryActivity activity) {
        // nothing to do
    }
}
