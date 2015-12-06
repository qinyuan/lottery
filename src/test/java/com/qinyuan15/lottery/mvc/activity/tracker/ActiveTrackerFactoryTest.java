package com.qinyuan15.lottery.mvc.activity.tracker;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import com.qinyuan15.lottery.mvc.dao.LotteryLotDao;
import com.qinyuan15.lottery.mvc.dao.VirtualUserDao;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ActiveTrackerFactoryTest extends DatabaseTestCase {
    @Test
    public void testNext() throws Exception {
        /*
         * no virtual user take lot, no tracker
         */
        ActiveTrackerFactory factory = new ActiveTrackerFactory(1);
        assertThat(factory.next()).isNull();

        /*
         * one virtual user take lot, but it is not active, so no active tracker
         */
        LotteryLotDao lotteryLotDao = new LotteryLotDao();
        lotteryLotDao.add(1, 1, 1, true);
        factory = new ActiveTrackerFactory(1);
        assertThat(factory.next()).isNull();

        /*
         * one virtual user take lot, one active tracker
         */
        VirtualUserDao virtualUserDao = new VirtualUserDao();
        virtualUserDao.activate(virtualUserDao.getInstance(1));
        assertThat(factory.next()).isNull();
        factory = new ActiveTrackerFactory(1);
        assertThat(factory.next().toString()).isEqualTo("activity:1,userId:1,active:true");
        assertThat(factory.next()).isNull();

        /*
         * add 200 active virtual users and 200 inactive virtual users, then each virtual user take lot
         */
        for (int i = 0; i < 200; i++) {
            // add active virtual user
            int id = virtualUserDao.add(RandomStringUtils.randomAlphanumeric(10));
            virtualUserDao.activate(virtualUserDao.getInstance(id));
            lotteryLotDao.add(1, id, 1, true);

            // add inactive virtual user
            id = virtualUserDao.add(RandomStringUtils.randomAlphanumeric(10));
            virtualUserDao.deactivate(virtualUserDao.getInstance(id));
            lotteryLotDao.add(1, id, 1, true);
        }

        assertThat(factory.next()).isNull();
        factory = new ActiveTrackerFactory(1);
        for (int i = 0; i < 201; i++) {
            assertThat(factory.next()).isNotNull();
        }
        assertThat(factory.next()).isNull();
    }
}
