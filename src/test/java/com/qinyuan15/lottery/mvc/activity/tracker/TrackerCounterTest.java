package com.qinyuan15.lottery.mvc.activity.tracker;

import com.qinyuan.lib.database.hibernate.HibernateUpdater;
import com.qinyuan.lib.database.test.DatabaseTestCase;
import com.qinyuan15.lottery.mvc.dao.LotteryLotDao;
import com.qinyuan15.lottery.mvc.dao.VirtualUser;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TrackerCounterTest extends DatabaseTestCase {

    @Test
    public void testCount() {
        TrackerCounter counter = new TrackerCounter(1);
        assertThat(counter.count()).isZero();

        new LotteryLotDao().add(1, 1, 1, false);
        assertThat(counter.count()).isZero();

        new LotteryLotDao().add(1, 1, 1, true);
        assertThat(counter.count()).isEqualTo(1);

        new LotteryLotDao().add(1, 2, 1, true);
        assertThat(counter.count()).isEqualTo(2);

        new HibernateUpdater().addEqualFilter("id", 1).update(VirtualUser.class, "active=null");
        assertThat(counter.count()).isEqualTo(2);

        new HibernateUpdater().addEqualFilter("id", 1).update(VirtualUser.class, "active=false");
        assertThat(counter.count()).isEqualTo(2);

        new HibernateUpdater().addEqualFilter("id", 1).update(VirtualUser.class, "active=true");
        assertThat(counter.count()).isEqualTo(2);
    }
}
