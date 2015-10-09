package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class VirtualUserDaoTest extends DatabaseTestCase {
    private VirtualUserDao dao = new VirtualUserDao();

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
