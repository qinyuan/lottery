package com.qinyuan15.lottery.mvc.account;

import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan15.lottery.mvc.config.AppConfig;
import com.qinyuan15.lottery.mvc.dao.TelChangeLogDao;

public class RemainingTelChangeTimesCalculator {
    private boolean infiniteTimes = false;
    private int totalTimes = 0;
    private int usedTimes = 0;
    private int remainingTimes = 0;

    public RemainingTelChangeTimesCalculator(int userId) {
        if (userId <= 0) {
            infiniteTimes = true;
            return;
        }

        Integer maxTelModificationTimes = AppConfig.getMaxTelModificationTimes();
        if (!IntegerUtils.isPositive(maxTelModificationTimes)) {
            infiniteTimes = true;
            return;
        }

        totalTimes = maxTelModificationTimes;
        usedTimes = new TelChangeLogDao().countInOneYear(userId);
        remainingTimes = totalTimes - usedTimes;
        if (remainingTimes < 0) {
            remainingTimes = 0;
        }
    }

    public boolean isInfiniteTimes() {
        return infiniteTimes;
    }

    public int getTotalTimes() {
        return totalTimes;
    }

    public int getUsedTimes() {
        return usedTimes;
    }

    public int getRemainingTimes() {
        return remainingTimes;
    }
}
