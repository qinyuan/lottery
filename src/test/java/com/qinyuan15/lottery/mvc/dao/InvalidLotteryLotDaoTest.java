package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.database.test.DatabaseTestCase;
import com.qinyuan15.lottery.mvc.config.AppConfig;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InvalidLotteryLotDaoTest extends DatabaseTestCase {
    private InvalidLotteryLotDao dao = new InvalidLotteryLotDao();

    @Test
    public void testCount() {
        assertThat(dao.count(2)).isEqualTo(2);
        new UserDao().updateTel(3, "13200000000");
        assertThat(dao.count(2)).isEqualTo(1);
        new UserDao().updateTel(4, "14200000000");
        assertThat(dao.count(2)).isEqualTo(0);
        changeMinLivenessToParticipate(2, 14);
        assertThat(dao.count(2)).isEqualTo(1);
        changeMinLivenessToParticipate(2, 26);
        assertThat(dao.count(2)).isEqualTo(2);

        assertThat(dao.count(15)).isEqualTo(0);
    }

    @Test
    public void testGetSerialNumbers() {
        assertThat(dao.getSerialNumbers(2)).containsExactly(10257, 217892);
        assertThat(dao.getSerialNumbers(15)).isEmpty();
    }

    @Test
    public void getGetNoTelUserIds() {
        LotteryActivity activity = new LotteryActivityDao().getInstance(2);
        assertThat(dao.getNoTelUserIds(activity)).containsExactly(3, 4);

        AppConfig.lottery.updateNoTelLotCount(2);
        assertThat(dao.getNoTelUserIds(activity)).containsExactly(3, 4);

        double commodityPrice = activity.getCommodity().getPrice();
        AppConfig.lottery.updateNoTelLotPrice(commodityPrice - 0.01);
        assertThat(dao.getNoTelUserIds(activity)).containsExactly(3, 4);
        AppConfig.lottery.updateNoTelLotPrice(commodityPrice);
        assertThat(dao.getNoTelUserIds(activity)).isEmpty();

        AppConfig.lottery.updateNoTelLotCount(0);
        assertThat(dao.getNoTelUserIds(activity)).containsExactly(3, 4);

        // give user 3 a tel
        new UserDao().updateTel(3, "13200000000");
        assertThat(dao.getNoTelUserIds(activity)).containsExactly(4);

        // give user 4 a tel
        new UserDao().updateTel(4, "13700000000");
        assertThat(dao.getNoTelUserIds(activity)).isEmpty();

        activity = new LotteryActivityDao().getInstance(15);
        assertThat(dao.getNoTelUserIds(activity)).isEmpty();
    }

    @Test
    public void getInsufficientLivenessUserIds() {
        assertThat(dao.getInsufficientLivenessUserIds(2)).isEmpty();

        changeMinLivenessToParticipate(2, 13);
        assertThat(dao.getInsufficientLivenessUserIds(2)).isEmpty();

        changeMinLivenessToParticipate(2, 14);
        assertThat(dao.getInsufficientLivenessUserIds(2)).containsExactly(4);

        changeMinLivenessToParticipate(2, 25);
        assertThat(dao.getInsufficientLivenessUserIds(2)).containsExactly(4);

        changeMinLivenessToParticipate(2, 26);
        assertThat(dao.getInsufficientLivenessUserIds(2)).containsExactly(3, 4);

        assertThat(dao.getInsufficientLivenessUserIds(15)).isEmpty();
    }

    private void changeMinLivenessToParticipate(int activityId, int liveness) {
        LotteryActivity activity = new LotteryActivityDao().getInstance(activityId);
        activity.setMinLivenessToParticipate(liveness);
        HibernateUtils.update(activity);
    }
}
