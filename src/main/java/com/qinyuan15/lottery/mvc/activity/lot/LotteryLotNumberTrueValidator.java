package com.qinyuan15.lottery.mvc.activity.lot;

import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import org.apache.commons.lang3.tuple.Pair;

/**
 * implementation of LotteryLotNumberValidator, always return true
 * Created by qinyuan on 15-11-22.
 */
public class LotteryLotNumberTrueValidator implements LotteryLotNumberValidator {
    @Override
    public Pair<Boolean, String> validate(LotteryActivity activity, int serialNumber) {
        return Pair.of(true, "");
    }
}
