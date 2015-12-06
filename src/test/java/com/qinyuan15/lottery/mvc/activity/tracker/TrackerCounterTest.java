package com.qinyuan15.lottery.mvc.activity.tracker;

import com.qinyuan.lib.database.hibernate.HibernateUpdater;
import com.qinyuan.lib.database.test.DatabaseTestCase;
import com.qinyuan15.lottery.mvc.dao.LotteryLotDao;
import com.qinyuan15.lottery.mvc.dao.VirtualUser;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TrackerCounterTest extends DatabaseTestCase {

    @Test
    public void testCountActive() throws Exception {
        TrackerCounter counter = new TrackerCounter(1);
        assertThat(counter.countActive()).isZero();

        new LotteryLotDao().add(1, 1, 1, true);
        assertThat(counter.countActive()).isZero();

        new HibernateUpdater().update(VirtualUser.class, "active=true");
        assertThat(counter.countActive()).isEqualTo(1);

        new LotteryLotDao().add(1, 2, 1, true);
        assertThat(counter.countActive()).isEqualTo(2);

        new HibernateUpdater().addEqualFilter("id", 1).update(VirtualUser.class, "active=null");
        assertThat(counter.countActive()).isEqualTo(1);

        new HibernateUpdater().addEqualFilter("id", 1).update(VirtualUser.class, "active=false");
        assertThat(counter.countActive()).isEqualTo(1);
    }

    @Test
    public void testCountInactive() throws Exception {
        TrackerCounter counter = new TrackerCounter(1);
        assertThat(counter.countInactive()).isZero();

        new LotteryLotDao().add(1, 1, 1, true);
        assertThat(counter.countInactive()).isZero();

        new HibernateUpdater().update(VirtualUser.class, "active=false");
        assertThat(counter.countInactive()).isEqualTo(1);

        new LotteryLotDao().add(1, 2, 1, true);
        assertThat(counter.countInactive()).isEqualTo(2);

        new HibernateUpdater().addEqualFilter("id", 1).update(VirtualUser.class, "active=null");
        assertThat(counter.countInactive()).isEqualTo(1);

        new HibernateUpdater().addEqualFilter("id", 1).update(VirtualUser.class, "active=true");
        assertThat(counter.countInactive()).isEqualTo(1);

    }
}
