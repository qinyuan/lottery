package com.qinyuan15.lottery.mvc.activity.tracker;

import com.qinyuan15.lottery.mvc.activity.ExpectParticipantsDivider;
import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.VirtualUserDao;

class ExpectedTrackerCalculator {
    private final int expectedActiveTrackerSize;
    private final int expectedInactiveTrackerSize;

    ExpectedTrackerCalculator(LotteryActivity activity) {
        Integer expectedParticipantCount = activity.getExpectParticipantCount();
        if (expectedParticipantCount == null) {
            expectedParticipantCount = 0;
        }

        int virtualUserCount = new VirtualUserDao().count();
        int availableVirtualUserCount = (int) (virtualUserCount * 0.8); // keep 20% as reserve virtual users
        expectedParticipantCount = Math.min(expectedParticipantCount, availableVirtualUserCount);

        int currentExpectedParticipantCount = new ExpectParticipantsDivider(
                activity.getStartTime(), activity.getCloseTime(), expectedParticipantCount)
                .getCurrentExpectValue();

        expectedActiveTrackerSize = currentExpectedParticipantCount / 2;
        expectedInactiveTrackerSize = currentExpectedParticipantCount / 2;
    }

    public int getExpectedActiveTrackerSize() {
        return expectedActiveTrackerSize;
    }

    public int getExpectedInactiveTrackerSize() {
        return expectedInactiveTrackerSize;
    }
}