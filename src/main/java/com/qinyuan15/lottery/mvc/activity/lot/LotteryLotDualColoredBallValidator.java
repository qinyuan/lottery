package com.qinyuan15.lottery.mvc.activity.lot;

import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import org.apache.commons.lang3.tuple.Pair;

import java.text.DecimalFormat;

import static com.qinyuan15.lottery.mvc.activity.dualcoloredball.DualColoredBallUtils.MAX_NUMBER;
import static com.qinyuan15.lottery.mvc.activity.dualcoloredball.DualColoredBallUtils.MIN_NUMBER;

/**
 * Lot number must be combination of last three numbers of dual colored ball
 * Created by qinyuan on 15-12-31.
 */
public class LotteryLotDualColoredBallValidator implements LotteryLotNumberValidator {
    @Override
    public Pair<Boolean, String> validate(LotteryActivity activity, int serialNumber) {
        String numberString = new DecimalFormat("000000").format(serialNumber);
        int ball4 = Integer.parseInt(numberString.substring(0, 2));
        int ball5 = Integer.parseInt(numberString.substring(2, 4));
        int ball6 = Integer.parseInt(numberString.substring(4, 6));
        if (ball4 < MIN_NUMBER || ball4 > MAX_NUMBER || ball5 < MIN_NUMBER || ball5 > MAX_NUMBER ||
                ball6 < MIN_NUMBER || ball6 > MAX_NUMBER || ball4 == ball5 || ball4 == ball6 || ball5 == ball6) {
            return Pair.of(false, "抽奖号必须为双色球的后3个号码");
        } else {
            return Pair.of(true, "");
        }
    }
}
