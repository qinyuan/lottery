package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LotteryLivenessDaoTest extends DatabaseTestCase {
    private LotteryLivenessDao dao = new LotteryLivenessDao();

    @Test
    public void testGetInstances() {
        assertThat(dao.getInstances(3)).hasSize(2);
        assertThat(dao.getInstances(1)).isEmpty();
    }

    @Test
    public void testGetLiveness() {
        assertThat(dao.getLiveness(3)).isEqualTo(25);
        assertThat(dao.getLiveness(4)).isEqualTo(13);
        assertThat(dao.getLiveness(5)).isZero();
        assertThat(dao.getLiveness(6)).isZero();    // invalid user id
    }

    @Test
    public void testGetMaxLivenessUsernames() {
        Pair<String, Integer> maxLivenessUsernames = dao.getMaxLivenessUsernames();
        assertThat(maxLivenessUsernames.getLeft()).isEqualTo("normal-user1");
        assertThat(maxLivenessUsernames.getRight()).isEqualTo(25);

        dao.add(4, 3, 12, "sina", false);
        maxLivenessUsernames = dao.getMaxLivenessUsernames();
        assertThat(maxLivenessUsernames.getLeft()).isEqualTo("normal-user1,normal-user2");
        assertThat(maxLivenessUsernames.getRight()).isEqualTo(25);
    }

    @Test
    public void testAdd() {
        assertThat(dao.getInstances(4)).hasSize(1);
        dao.add(4, 3, 2, "xina", true);
        assertThat(dao.getInstances(4)).hasSize(2);
    }
}
