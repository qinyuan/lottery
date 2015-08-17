package com.qinyuan15.lottery.mvc.lottery;

import com.qinyuan15.utils.IntegerUtils;

public class DualColoredBallTermValidator {
    public boolean validate(Integer fullTermNumber) {
        return IntegerUtils.isPositive(fullTermNumber)
                && fullTermNumber >= 1900000
                && fullTermNumber < 2100000;
    }
}
