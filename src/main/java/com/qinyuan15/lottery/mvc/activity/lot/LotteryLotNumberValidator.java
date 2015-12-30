package com.qinyuan15.lottery.mvc.activity.lot;

import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import org.apache.commons.lang3.tuple.Pair;

public interface LotteryLotNumberValidator {
    Pair<Boolean, String> validate(LotteryActivity activity, int serialNumber);
}
