package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan.lib.lang.time.DateUtils;
import com.qinyuan15.lottery.mvc.dao.LotteryActivity;

import java.sql.Date;

public class LotteryActivityUtils {
    public static boolean shouldBeClosed(LotteryActivity activity) {
        return getRemainingTimeToClose(activity) <= 0;
    }

    public static long getRemainingTimeToClose(LotteryActivity activity) {
        Date closeTime = DateUtils.newDate(activity.getCloseTime());
        return closeTime.getTime() - System.currentTimeMillis();
    }

    public static long getRemainingTimeToEnd(LotteryActivity activity) {
        Date expectEndTime = DateUtils.newDate(activity.getExpectEndTime());
        return expectEndTime.getTime() - System.currentTimeMillis();
    }
}
