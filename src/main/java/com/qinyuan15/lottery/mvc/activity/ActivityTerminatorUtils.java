package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan.lib.lang.concurrent.ThreadUtils;

public class ActivityTerminatorUtils {
    static void sleep(long remainingMilliSeconds) {
        // sleep time becomes less and less
        if (remainingMilliSeconds > 540 * 1000) {
            ThreadUtils.sleep(180); // sleep 180 seconds at most
        } else if (remainingMilliSeconds > 100) {
            ThreadUtils.sleep(((double) remainingMilliSeconds) / 1000 / 3);
        } else {
            ThreadUtils.sleep(0.2);
        }
    }
}
