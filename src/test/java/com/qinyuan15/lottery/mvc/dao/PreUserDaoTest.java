package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PreUserDaoTest extends DatabaseTestCase {
    private PreUserDao dao = new PreUserDao();

    @Test
    public void testAdd() throws Exception {
        assertThat(dao.count()).isEqualTo(2);
        String serialKey = RandomStringUtils.randomAlphanumeric(20);
        dao.add("test@qq.com", serialKey);
        assertThat(dao.count()).isEqualTo(3);
    }

    @Test
    public void testGetInstanceBySerialKey() throws Exception {
        String serialKey = "fpiaohgiosajfslja";
        assertThat(dao.getInstanceBySerialKey(serialKey)).isNotNull();

        assertThat(dao.getInstanceBySerialKey(RandomStringUtils.randomAlphanumeric(20))).isNull();
    }

    @Test
    public void testHasSerialKey() throws Exception {
        assertThat(dao.hasSerialKey("fpiaohgiosajfslja")).isTrue();
        assertThat(dao.hasSerialKey(RandomStringUtils.randomAlphanumeric(20))).isFalse();
    }

    @Test
    public void testGetInstanceByEmail() throws Exception {
        assertThat(dao.getInstanceByEmail("test12345@qq.com")).isNull();
        assertThat(dao.getInstanceByEmail("test12345@sina.com").getId()).isEqualTo(1);
    }
}
