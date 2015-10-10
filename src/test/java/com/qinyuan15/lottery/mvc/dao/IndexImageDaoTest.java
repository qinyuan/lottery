package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test IndexImageDao
 * Created by qinyuan on 15-6-18.
 */
public class IndexImageDaoTest extends DatabaseTestCase {
    private IndexImageDao dao = new IndexImageDao();

    @Test
    public void testGetInstances() throws Exception {
        assertThat(dao.getInstances()).hasSize(2);
    }

    @Test
    public void testAdd() throws Exception {
        assertThat(dao.count()).isEqualTo(2);
        dao.add("/var/www/html/lottery/hello", "/var/www/html/lottery/world");
        assertThat(dao.count()).isEqualTo(3);
    }
}
