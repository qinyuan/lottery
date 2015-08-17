package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan15.utils.DateUtils;

import java.sql.Date;

/**
 * Class to divide participants in a period of time averagely
 * Created by qinyuan on 15-7-14.
 */
public class ExpectParticipantsDivider {
    private final Date startTime;
    private final Date endTime;
    private final int expectParticipantCount;

    public ExpectParticipantsDivider(String startTime, String endTime, Integer expectParticipantCount) {
        this.startTime = startTime == null ? null : DateUtils.newDate(startTime);
        this.endTime = endTime == null ? null : DateUtils.newDate(endTime);
        this.expectParticipantCount = expectParticipantCount == null ? 0 : expectParticipantCount;
    }

    public int getCurrentExpectValue() {
        if (startTime == null || endTime == null || expectParticipantCount == 0) {
            return 0;
        }

        long startTimestamp = startTime.getTime();
        long endTimestamp = endTime.getTime();

        if (startTimestamp > endTimestamp) {
            return 0;
        }

        long currentTimestamp = System.currentTimeMillis();
        if (currentTimestamp < startTimestamp) {
            return 0;
        }
        if (currentTimestamp > endTimestamp) {
            return this.expectParticipantCount;
        }

        double currentExpectValue = 1.0 * expectParticipantCount * (currentTimestamp - startTimestamp)
                / (endTimestamp - startTimestamp);
        return (int) currentExpectValue;
    }
}
