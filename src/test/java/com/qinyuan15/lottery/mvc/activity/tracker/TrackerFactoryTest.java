package com.qinyuan15.lottery.mvc.activity.tracker;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import com.qinyuan15.lottery.mvc.dao.LotteryLotDao;
import com.qinyuan15.lottery.mvc.dao.VirtualUserDao;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TrackerFactoryTest extends DatabaseTestCase {
    /*
     * test active tracker
     */
    @Test
    public void testNext() throws Exception {
        final boolean active = true;
        /*
         * no virtual user take lot, no tracker
         */
        assertThat(new TrackerFactory(1, active).next()).isNull();

        /*
         * one virtual user take lot, but it is not active, so no active tracker
         */
        LotteryLotDao lotteryLotDao = new LotteryLotDao();
        lotteryLotDao.add(1, 1, 1, true);
        assertThat(new TrackerFactory(1, active).next()).isNull();

        /*
         * one virtual user take lot, but no real lot, so no active tracker
         */
        VirtualUserDao virtualUserDao = new VirtualUserDao();
        virtualUserDao.activate(virtualUserDao.getInstance(1));
        assertThat(new TrackerFactory(1, active).next()).isNull();

        /*
         * add one real lot to other serial number, there is no active tracker, either
         */
        lotteryLotDao.add(1, 1, 2, false);
        assertThat(new TrackerFactory(1, active).next()).isNull();

        /*
         * add one real lot to same serial number, there is one active tracker
         */
        lotteryLotDao.add(1, 2, 1, false);
        TrackerFactory factory = new TrackerFactory(1, active);
        assertThat(factory.next().toString()).isEqualTo("activity:1,userId:1,active:true");
        assertThat(factory.next()).isNull();

        /*
         * change virtual user to inactive, there is no active tracker
         */
        virtualUserDao.deactivate(virtualUserDao.getInstance(1));
        assertThat(new TrackerFactory(1, active).next()).isNull();

        /*
         * add 200 active virtual users and 100 inactive virtual users, then each virtual user take lot
         */
        for (int i = 0; i < 200; i++) {
            // add active virtual user
            int id = virtualUserDao.add(RandomStringUtils.randomAlphanumeric(10));
            virtualUserDao.activate(virtualUserDao.getInstance(id));
            lotteryLotDao.add(2, id, 10257, true);

            // add inactive virtual user
            if (i < 100) {
                id = virtualUserDao.add(RandomStringUtils.randomAlphanumeric(10));
                virtualUserDao.deactivate(virtualUserDao.getInstance(id));
                lotteryLotDao.add(2, id, 10257, true);
            }
        }

        /*
         * there should be 200 active active tracker
         */
        assertThat(factory.next()).isNull();
        factory = new TrackerFactory(2, active);
        for (int i = 0; i < 200; i++) {
            assertThat(factory.next()).isNotNull();
        }
        assertThat(factory.next()).isNull();
    }

    /*
     * test inactive tracker
     */
    @Test
    public void testNext2() throws Exception {
        final boolean active = false;
        /*
         * no virtual user take lot, no tracker
         */
        assertThat(new TrackerFactory(1, active).next()).isNull();

        /*
         * one virtual user take lot, but it is not inactive, so no inactive tracker
         */
        LotteryLotDao lotteryLotDao = new LotteryLotDao();
        lotteryLotDao.add(1, 1, 1, true);
        assertThat(new TrackerFactory(1, active).next()).isNull();

        /*
         * one virtual user take lot, one active tracker
         */
        VirtualUserDao virtualUserDao = new VirtualUserDao();
        virtualUserDao.deactivate(virtualUserDao.getInstance(1));
        assertThat(new TrackerFactory(1, active).next()).isNull();

        /*
         * add one real lot to other serial, there is no active tracker, either
         */
        lotteryLotDao.add(1, 1, 2, false);
        assertThat(new TrackerFactory(1, active).next()).isNull();

        /*
         * add one real lot to same serial number, there is one active tracker
         */
        lotteryLotDao.add(1, 2, 1, false);
        TrackerFactory factory = new TrackerFactory(1, active);
        assertThat(factory.next().toString()).isEqualTo("activity:1,userId:1,active:false");
        assertThat(factory.next()).isNull();

        /*
         * change virtual user to inactive, there is no active tracker
         */
        virtualUserDao.activate(virtualUserDao.getInstance(1));
        assertThat(new TrackerFactory(1, active).next()).isNull();

        /*
         * add 100 active virtual users and 200 inactive virtual users, then each virtual user take lot
         */
        for (int i = 0; i < 200; i++) {
            // add active virtual user
            if (i < 100) {
                int id = virtualUserDao.add(RandomStringUtils.randomAlphanumeric(10));
                virtualUserDao.activate(virtualUserDao.getInstance(id));
                lotteryLotDao.add(2, id, 10257, true);
            }

            // add inactive virtual user
            int id = virtualUserDao.add(RandomStringUtils.randomAlphanumeric(10));
            virtualUserDao.deactivate(virtualUserDao.getInstance(id));
            lotteryLotDao.add(2, id, 10257, true);
        }

        assertThat(factory.next()).isNull();
        factory = new TrackerFactory(2, active);
        for (int i = 0; i < 200; i++) {
            assertThat(factory.next()).isNotNull();
        }
        assertThat(factory.next()).isNull();
    }
}
