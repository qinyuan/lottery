package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan.lib.lang.time.DateUtils;
import com.qinyuan.lib.lang.time.Time;
import com.qinyuan.lib.lang.time.WeightedTime;

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
        // if there is null or 0, just return 0
        if (startTime == null || endTime == null || expectParticipantCount == 0) {
            return 0;
        }

        // if start time is later than end time, just return 0
        long startTimestamp = startTime.getTime();
        long endTimestamp = endTime.getTime();
        if (startTimestamp > endTimestamp) {
            return 0;
        }

        // if haven't start, just return 0
        Date now = DateUtils.now();
        long currentTimestamp = now.getTime();
        if (currentTimestamp < startTimestamp) {
            return 0;
        }

        // if already end, just return expect participants number
        if (currentTimestamp > endTimestamp) {
            return this.expectParticipantCount;
        }

        long startToNow = getWeightedSeconds(startTime, now);
        long totalTime = getWeightedSeconds(startTime, endTime);
        if (startToNow > totalTime) {
            startToNow = totalTime;
        }

        double currentExpectValue = (1.0 * startToNow / totalTime) * expectParticipantCount;
        return (int) currentExpectValue;
    }

    private static long getWeightedSeconds(Date startTime, Date endTime) {
        WeightedTime weightedTime = new WeightedTime(startTime, endTime);

        // during 10 to 20 o'clock, make adding of virtual participants faster
        weightedTime.addWeight(new Time(10, 0, 0), new Time(22, 0, 0), 1000);

        return weightedTime.countSeconds();
    }
}
