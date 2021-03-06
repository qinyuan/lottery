package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import com.qinyuan.lib.lang.time.DateUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TelChangeLogDaoTest extends DatabaseTestCase {
    private TelChangeLogDao dao = new TelChangeLogDao();

    @Test
    public void testAdd() throws Exception {
        assertThat(dao.count()).isEqualTo(1);

        dao.add(2, "15000000000", "13000000000");
        assertThat(dao.count()).isEqualTo(2);

        dao.add(2, null, "130");
        assertThat(dao.count()).isEqualTo(3);

        dao.add(2, "150", null);
        assertThat(dao.count()).isEqualTo(4);

        dao.add(3, null, null);
        assertThat(dao.count()).isEqualTo(5);
    }

    @Test
    public void testCount() throws Exception {
        assertThat(dao.count(2, DateUtils.newDate("2014-12-12 10:10:09"))).isEqualTo(1);
        assertThat(dao.count(2, DateUtils.newDate("2014-12-12 10:10:11"))).isZero();
        assertThat(dao.count(3, DateUtils.newDate("2014-12-12 10:10:09"))).isZero();
    }

    @Test
    public void testCountInOneYear() throws Exception {
        System.out.println(dao.countInOneYear(2));
    }
}
