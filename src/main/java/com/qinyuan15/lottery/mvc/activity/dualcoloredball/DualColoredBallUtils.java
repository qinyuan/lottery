package com.qinyuan15.lottery.mvc.activity.dualcoloredball;

import org.apache.commons.lang3.RandomUtils;

public class DualColoredBallUtils {
    public final static int MIN_NUMBER = 1;
    public final static int MAX_NUMBER = 33;
    public static int rand() {
        return RandomUtils.nextInt(MIN_NUMBER, MAX_NUMBER + 1);
    }
}
