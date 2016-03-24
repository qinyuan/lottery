package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommodityDaoTest extends DatabaseTestCase {
    private CommodityDao dao = new CommodityDao();

    @Test
    public void testFactory() {
        assertThat(CommodityDao.factory().getCount()).isEqualTo(4);
        assertThat(CommodityDao.factory().getInstances(0, Integer.MAX_VALUE)).hasSize(4);
        assertThat(CommodityDao.factory().getInstances(0, 2)).hasSize(2);
        assertThat(CommodityDao.factory().getInstances(0, 1)).hasSize(1);

        assertThat(CommodityDao.factory().getFirstInstance().getId()).isEqualTo(4);
    }

    @Test
    public void testIsUsed() {
        assertThat(dao.isUsed(1)).isTrue();
        assertThat(dao.isUsed(2)).isFalse();
        assertThat(dao.isUsed(100)).isFalse();
    }

    @Test
    public void testGetInstances() {
        assertThat(dao.getInstances()).hasSize(4);
    }

    @Test
    public void testGetFirstInstance() {
        assertThat(dao.getFirstInstance().getId()).isEqualTo(4);
    }

    @Test
    public void testGetFirstVisibleInstance() {
        assertThat(dao.getFirstVisibleInstance().getId()).isEqualTo(2);
    }

    @Test
    public void testGetVisibleInstances() {
        assertThat(dao.getVisibleInstances()).hasSize(2);
    }

    @Test
    public void testRankUp() {
        assertThat(dao.getInstance(2).getRanking()).isEqualTo(11);
        assertThat(dao.getInstance(1).getRanking()).isEqualTo(10);
        dao.rankUp(2);
        assertThat(dao.getInstance(2).getRanking()).isEqualTo(10);
        assertThat(dao.getInstance(1).getRanking()).isEqualTo(11);
        dao.rankUp(2);
        assertThat(dao.getInstance(2).getRanking()).isEqualTo(8);
        dao.rankUp(2);
        assertThat(dao.getInstance(2).getRanking()).isEqualTo(8);

        assertThat(dao.getInstance(3).getRanking()).isEqualTo(14);
        dao.rankUp(3);
        assertThat(dao.getInstance(3).getRanking()).isEqualTo(11);
        assertThat(dao.getInstance(1).getRanking()).isEqualTo(14);
    }

    @Test
    public void testRankDown() {
        assertThat(dao.getInstance(2).getRanking()).isEqualTo(11);
        assertThat(dao.getInstance(3).getRanking()).isEqualTo(14);
        dao.rankDown(2);
        assertThat(dao.getInstance(2).getRanking()).isEqualTo(14);
        assertThat(dao.getInstance(3).getRanking()).isEqualTo(11);
    }

    @Test
    public void testAdd() {
        assertThat(dao.count()).isEqualTo(4);
        dao.add("name_1", 15.0, "snapshot_1");
        assertThat(dao.count()).isEqualTo(5);
        assertThat(dao.getInstance(5).getRanking()).isEqualTo(15);
    }
}
