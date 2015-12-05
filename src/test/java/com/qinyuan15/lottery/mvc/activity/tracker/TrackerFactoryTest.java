package com.qinyuan15.lottery.mvc.activity.tracker;

import com.qinyuan.lib.database.hibernate.HibernateUpdater;
import com.qinyuan.lib.database.test.DatabaseTestCase;
import com.qinyuan15.lottery.mvc.dao.LotteryLotDao;
import com.qinyuan15.lottery.mvc.dao.VirtualUser;
import com.qinyuan15.lottery.mvc.dao.VirtualUserDao;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TrackerFactoryTest extends DatabaseTestCase {
    private TrackerFactory factory = new TrackerFactory(1);

    @Test
    public void testCreateActivePreTrackers() throws Exception {
        assertThat(factory.createActivePreTrackers(2)).hasSize(2);

        setUp();

        assertThat(factory.createActivePreTrackers(1)).hasSize(1);
        assertThat(factory.createActivePreTrackers(2)).hasSize(2);
        assertThat(factory.createActivePreTrackers(3)).hasSize(2);

        /*
         * virtual user 1 and virtual user 2 have be used as pre tracker,
         * so virtual user 3 won't be used as pre tracker
         */
        new VirtualUserDao().add("virtual_user3");
        for (int i = 0; i < 10; i++) {
            assertThat(factory.createActivePreTrackers(2).toString())
                    .contains("1").contains("2").doesNotContain("3");
        }

        /*
         * if all virtual user is inactive, each one all have chance to be used as pre tracker
         */
        boolean trackerOfVirtualUser1Created = false;
        boolean trackerOfVirtualUser2Created = false;
        boolean trackerOfVirtualUser3Created = false;
        for (int i = 0; i < 10; i++) {
            new HibernateUpdater().update(VirtualUser.class, "active=null");
            String trackerString = factory.createActivePreTrackers(2).toString();
            if (trackerString.contains("1")) {
                trackerOfVirtualUser1Created = true;
            }
            if (trackerString.contains("2")) {
                trackerOfVirtualUser2Created = true;
            }
            if (trackerString.contains("3")) {
                trackerOfVirtualUser3Created = true;
            }
        }
        assertThat(trackerOfVirtualUser1Created).isTrue();
        assertThat(trackerOfVirtualUser2Created).isTrue();
        assertThat(trackerOfVirtualUser3Created).isTrue();

        /*
         * if virtual user 3 has taken lot, it won't be used as pre user
         */
        new LotteryLotDao().add(1, 3, 1, true);
        for (int i = 0; i < 10; i++) {
            new HibernateUpdater().update(VirtualUser.class, "active=null");
            assertThat(factory.createActivePreTrackers(2).toString()).doesNotContain("3");
        }
    }

    @Test
    public void testCreateInactivePreTrackers() throws Exception {
        assertThat(factory.createInactivePreTrackers(2)).hasSize(2);

        setUp();

        assertThat(factory.createInactivePreTrackers(1)).hasSize(1);
        assertThat(factory.createInactivePreTrackers(2)).hasSize(2);
        assertThat(factory.createInactivePreTrackers(3)).hasSize(2);

        /*
         * virtual user 1 and virtual user 2 have be used as pre tracker,
         * so virtual user 3 won't be used as pre tracker
         */
        new VirtualUserDao().add("virtual_user3");
        for (int i = 0; i < 10; i++) {
            assertThat(factory.createInactivePreTrackers(2).toString())
                    .contains("1").contains("2").doesNotContain("3");
        }

        /*
         * if all virtual user is inactive, each one all have chance to be used as pre tracker
         */
        boolean trackerOfVirtualUser1Created = false;
        boolean trackerOfVirtualUser2Created = false;
        boolean trackerOfVirtualUser3Created = false;
        for (int i = 0; i < 10; i++) {
            new HibernateUpdater().update(VirtualUser.class, "active=null");
            String trackerString = factory.createActivePreTrackers(2).toString();
            if (trackerString.contains("1")) {
                trackerOfVirtualUser1Created = true;
            }
            if (trackerString.contains("2")) {
                trackerOfVirtualUser2Created = true;
            }
            if (trackerString.contains("3")) {
                trackerOfVirtualUser3Created = true;
            }
        }
        assertThat(trackerOfVirtualUser1Created).isTrue();
        assertThat(trackerOfVirtualUser2Created).isTrue();
        assertThat(trackerOfVirtualUser3Created).isTrue();

        /*
         * if virtual user 3 has taken lot, it won't be used as pre user
         */
        new LotteryLotDao().add(1, 3, 1, true);
        for (int i = 0; i < 10; i++) {
            new HibernateUpdater().update(VirtualUser.class, "active=null");
            assertThat(factory.createInactivePreTrackers(2).toString()).doesNotContain("3");
        }
    }

    @Test
    public void testGetOnJobActiveTrackers() throws Exception {

    }
}
