package com.qinyuan15.lottery.mvc.activity.tracker;

import com.qinyuan15.lottery.mvc.activity.ExpectParticipantsDivider;
import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.VirtualUserDao;

class ExpectedTrackerCalculator {
    private final int expectedTrackerSize;

    ExpectedTrackerCalculator(LotteryActivity activity) {
        Integer expectedParticipantCount = activity.getExpectParticipantCount();
        if (expectedParticipantCount == null) {
            expectedParticipantCount = 0;
        }
        expectedParticipantCount = Math.min(expectedParticipantCount, countAvailableVirtualUser());

        this.expectedTrackerSize = new ExpectParticipantsDivider(
                activity.getStartTime(), activity.getCloseTime(), expectedParticipantCount)
                .getCurrentExpectValue();
    }

    int countAvailableVirtualUser() {
        int virtualUserCount = new VirtualUserDao().count();
        return (int) (virtualUserCount * 0.8); // keep 20% as reserve virtual users
    }

    int getExpectedTrackerSize() {
        return expectedTrackerSize;
    }
}
