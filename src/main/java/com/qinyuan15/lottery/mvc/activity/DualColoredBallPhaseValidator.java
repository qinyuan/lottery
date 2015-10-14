package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan.lib.lang.IntegerUtils;

public class DualColoredBallPhaseValidator {
    public boolean validate(Integer fullPhase) {
        return IntegerUtils.isPositive(fullPhase) && fullPhase >= 1900000 && fullPhase < 2100000;
    }
}
