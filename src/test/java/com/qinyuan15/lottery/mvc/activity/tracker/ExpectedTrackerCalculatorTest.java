package com.qinyuan15.lottery.mvc.activity.tracker;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import com.qinyuan.lib.lang.time.DateUtils;
import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.LotteryActivityDao;
import com.qinyuan15.lottery.mvc.dao.VirtualUserDao;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ExpectedTrackerCalculatorTest extends DatabaseTestCase {
    private LotteryActivity activity;

    private ExpectedTrackerCalculator getCalculator() {
        return new ExpectedTrackerCalculator(activity);
    }

    @Test
    public void test() throws Exception {
        String now = DateUtils.nowString();
        String startTime = DateUtils.toLongString(DateUtils.oneDayAgo());
        String endTime = DateUtils.toLongString(DateUtils.oneDayLater());

        int expectParticipantCount = 10000;
        int activityId = new LotteryActivityDao().add(10, 1, startTime, endTime, endTime,
                expectParticipantCount, 2015103, "", 0);
        activity = new LotteryActivityDao().getInstance(activityId);

        // there is 2 virtual users at first

        assertThat(getCalculator().getExpectedTrackerSize()).isZero();

        for (int i = 0; i < 10; i++) {
            new VirtualUserDao().add(RandomStringUtils.randomAlphanumeric(12));
        }
        // there is 12 virtual users now

        assertThat(getCalculator().getExpectedTrackerSize()).isEqualTo(4);

        activity.setCloseTime(now);
        assertThat(getCalculator().getExpectedTrackerSize()).isEqualTo(9);

        for (int i = 0; i < 20; i++) {
            new VirtualUserDao().add(RandomStringUtils.randomAlphanumeric(12));
        }
        // there is 32 virtual users now

        assertThat(getCalculator().getExpectedTrackerSize()).isEqualTo(25);
    }
}
