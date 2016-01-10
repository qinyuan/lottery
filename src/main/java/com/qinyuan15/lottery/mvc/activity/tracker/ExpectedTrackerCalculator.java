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

        int virtualUserCount = new VirtualUserDao().count();
        int availableVirtualUserCount = (int) (virtualUserCount * 0.8); // keep 20% as reserve virtual users
        expectedParticipantCount = Math.min(expectedParticipantCount, availableVirtualUserCount);

        this.expectedTrackerSize = new ExpectParticipantsDivider(
                activity.getStartTime(), activity.getCloseTime(), expectedParticipantCount)
                .getCurrentExpectValue();
    }

    public int getExpectedTrackerSize() {
        return expectedTrackerSize;
    }
}
