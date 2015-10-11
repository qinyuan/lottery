package com.qinyuan15.lottery.mvc.dao;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SystemInfoDaoTest {
    private SystemInfoDao dao = new SystemInfoDao();

    @Test
    public void testAdd() {
        assertThat(dao.count()).isEqualTo(0);
        dao.add("helloWorld");
        assertThat(dao.count()).isEqualTo(1);
    }
}
