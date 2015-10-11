package com.qinyuan15.lottery.mvc.dao;

import com.google.common.collect.Lists;
import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SeckillLotDaoTest extends DatabaseTestCase {
    private SeckillLotDao dao = new SeckillLotDao();

    @Test
    public void testGetInstances() {
        assertThat(dao.getInstances()).hasSize(3);
        for (SeckillLot lot : dao.getInstances()) {
            assertThat(lot).isExactlyInstanceOf(SeckillLot.class);
        }
    }

    @Test
    public void testUpdateWinnerBySerialNumbers() throws Exception {
        for (int i = 1; i <= 3; i++) {
            assertThat(dao.getInstance(i).getWin()).isNull();
        }


        dao.updateWinnerBySerialNumbers(7, Lists.newArrayList("normal-user1"));
        for (int i = 1; i <= 3; i++) {
            assertThat(dao.getInstance(i).getWin()).isNull();
        }

        dao.updateWinnerBySerialNumbers(2, Lists.newArrayList("normal-user1"));
        assertThat(dao.getInstance(1).getWin()).isTrue();
        assertThat(dao.getInstance(2).getWin()).isNull();
        assertThat(dao.getInstance(3).getWin()).isNull();

        dao.updateWinnerBySerialNumbers(2, Lists.newArrayList("normal-user1", "hello", "normal-user2"));
        assertThat(dao.getInstance(1).getWin()).isTrue();
        assertThat(dao.getInstance(2).getWin()).isTrue();
        assertThat(dao.getInstance(3).getWin()).isNull();
    }
}
