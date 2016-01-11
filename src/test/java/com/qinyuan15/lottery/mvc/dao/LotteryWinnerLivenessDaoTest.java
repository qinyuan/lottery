package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LotteryWinnerLivenessDaoTest extends DatabaseTestCase {
    private LotteryWinnerLivenessDao dao = new LotteryWinnerLivenessDao();

    @Test
    public void testAdd() throws Exception {
        assertThat(dao.count()).isZero();

        dao.add(1, 2, true, 3, "2012-12-12 18:20:20");
        assertThat(dao.count()).isEqualTo(1);
    }
}
