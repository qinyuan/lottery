package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan.lib.lang.concurrent.ThreadUtils;

public class ActivityTerminatorUtils {
    static void sleep(long remainingMilliSeconds) {
        // sleep time becomes less and less
        if (remainingMilliSeconds > 120000) {
            ThreadUtils.sleep(40); // sleep 40 seconds at most
        } else if (remainingMilliSeconds > 100) {
            ThreadUtils.sleep(((double) remainingMilliSeconds) / 1000 / 3);
        } else {
            ThreadUtils.sleep(0.2);
        }
    }
}
