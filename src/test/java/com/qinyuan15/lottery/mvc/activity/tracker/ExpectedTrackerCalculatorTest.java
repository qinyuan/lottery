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

        assertThat(getCalculator().getExpectedActiveTrackerSize()).isZero();
        assertThat(getCalculator().getExpectedInactiveTrackerSize()).isZero();

        for (int i = 0; i < 10; i++) {
            new VirtualUserDao().add(RandomStringUtils.randomAlphanumeric(12));
        }

        assertThat(getCalculator().getExpectedActiveTrackerSize()).isEqualTo(2);
        assertThat(getCalculator().getExpectedInactiveTrackerSize()).isEqualTo(2);

        activity.setCloseTime(now);
        assertThat(getCalculator().getExpectedActiveTrackerSize()).isEqualTo(4);
        assertThat(getCalculator().getExpectedActiveTrackerSize()).isEqualTo(4);

        for (int i = 0; i < 20; i++) {
            new VirtualUserDao().add(RandomStringUtils.randomAlphanumeric(12));
        }

        assertThat(getCalculator().getExpectedActiveTrackerSize()).isEqualTo(12);
        assertThat(getCalculator().getExpectedInactiveTrackerSize()).isEqualTo(12);
    }
}
