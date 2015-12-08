package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LotteryActivityDaoTest extends DatabaseTestCase {
    private LotteryActivityDao dao = new LotteryActivityDao();

    @Test
    public void testFactory() {
        assertThat(LotteryActivityDao.factory().getCount()).isEqualTo(2);
        assertThat(LotteryActivityDao.factory().setExpire(true).getCount()).isEqualTo(1);
        assertThat(LotteryActivityDao.factory().getInstances()).hasSize(2);
        assertThat(LotteryActivityDao.factory().getFirstInstance().getId()).isEqualTo(1);
    }

    @Test
    public void testAdd() {
        assertThat(dao.count()).isEqualTo(2);
        dao.add(3, 1, "2015-12-12 14:14:14", "2015-12-13 12:12:12", "2015-12-13 12:00:00",
                1000, 2015081, "hello", 2);
        assertThat(dao.count()).isEqualTo(3);
    }

    @Test
    public void testUpdate() {
        assertThat(dao.getInstance(1).getCommodityId()).isEqualTo(1);
        assertThat(dao.getInstance(1).getCommodity()).isNotNull();
        dao.update(1, 3, 123, "2015-12-12 14:14:14", "2015-12-13 12:12:12", "2015-12-13 12:00:00",
                1000, 2015081, "hello", 2);
        assertThat(dao.getInstance(1).getCommodityId()).isEqualTo(123);
        assertThat(dao.getInstance(1).getCommodity()).isNull();
        assertThat(dao.count()).isEqualTo(2);
    }

    @Test
    public void testGetMaxTerm() {
        assertThat(dao.getMaxTerm()).isEqualTo(21);
    }

    @Test
    public void testGetLatestDescription() {
        assertThat(dao.getLatestDescription()).isNull();

        String string = RandomStringUtils.randomAlphanumeric(40);
        LotteryActivity activity = dao.getInstance(1);
        activity.setDescription(string);
        HibernateUtils.update(activity);

        assertThat(dao.getLatestDescription()).isEqualTo(string);
    }

    @Test
    public void testGetLatestMinLivenessToParticipate() {
        assertThat(dao.getLatestMinLivenessToParticipate()).isEqualTo(4);
    }

    @Test
    public void testGetNoResultInstances() {
        assertThat(dao.getNoResultInstances()).hasSize(2);
        new DualColoredBallRecordDao().add(2015, 81, "20151212", "080808080808");
        assertThat(dao.getNoResultInstances()).hasSize(1);
    }
}
