package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VirtualUserDaoTest extends DatabaseTestCase {
    private VirtualUserDao dao = new VirtualUserDao();

    @Test
    public void testChangeLiveness() {
        VirtualUser virtualUser = dao.getInstance(1);
        assertThat(virtualUser.getLiveness()).isNull();
        dao.changeLiveness(virtualUser, 2);
        assertThat(virtualUser.getLiveness()).isEqualTo(2);
        virtualUser = dao.getInstance(1);
        assertThat(virtualUser.getLiveness()).isEqualTo(2);
    }

    @Test
    public void testDeactivateAndCountInactive() {
        assertThat(dao.countActive()).isEqualTo(0);

        dao.deactivate(dao.getInstance(1));
        assertThat(dao.countInactive()).isEqualTo(1);

        dao.deactivate(dao.getInstances());
        assertThat(dao.countInactive()).isEqualTo(2);
    }

    @Test
    public void testActivateAndCountActive() {
        assertThat(dao.countActive()).isEqualTo(0);

        dao.activate(dao.getInstance(1));
        assertThat(dao.countActive()).isEqualTo(1);

        dao.activate(dao.getInstances());
        assertThat(dao.countActive()).isEqualTo(2);
    }

    @Test
    public void testAdd() {
        assertThat(dao.count()).isEqualTo(2);
        dao.add("test_user", "15", "3248", "ai", "@qq.com");
        assertThat(dao.count()).isEqualTo(3);
    }

    @Test
    public void testAdd1() {
        assertThat(dao.count()).isEqualTo(2);
        dao.add("test_user");
        assertThat(dao.count()).isEqualTo(3);
    }

    @Test
    public void testHasUsername() throws Exception {
        assertThat(dao.hasUsername("test")).isFalse();
        assertThat(dao.hasUsername("virtual_user1")).isTrue();
    }

    @Test
    public void getInstanceByUsername() {
        assertThat(dao.getInstanceByUsername("test")).isNull();
        assertThat(dao.getInstanceByUsername("virtual_user1").getId()).isEqualTo(1);
    }
}
