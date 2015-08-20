package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan.lib.lang.IntegerUtils;

public class DualColoredBallTermValidator {
    public boolean validate(Integer fullTermNumber) {
        return IntegerUtils.isPositive(fullTermNumber)
                && fullTermNumber >= 1900000
                && fullTermNumber < 2100000;
    }
}
