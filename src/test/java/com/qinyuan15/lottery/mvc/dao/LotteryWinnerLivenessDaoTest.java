package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LotteryWinnerLivenessDaoTest extends DatabaseTestCase {
    private LotteryWinnerLivenessDao dao = new LotteryWinnerLivenessDao();

    @Test
    public void testAdd() throws Exception {
        assertThat(dao.count()).isZero();

        dao.add(1, 2, true, 3, "2012-12-12 18:20:20");
        assertThat(dao.count()).isEqualTo(1);
    }

    @Test
    public void testQuery() {
        dao.query(1);

        String recordTime = "2012-12-12 18:20:20";
        dao.add(1, 2, true, 2, recordTime);
        dao.add(1, 4, false, 4, recordTime);
        dao.add(1, 1, true, 1, recordTime);

        Pair<String, List<LotterySameLotDao.SimpleUser>> result = dao.query(1);
        assertThat(result.getKey()).isEqualTo(recordTime);
        assertThat(result.getValue()).hasSize(3);
        assertThat(result.getValue().get(0).username).isEqualTo("normal-user2");
        assertThat(result.getValue().get(0).liveness).isEqualTo(4);
        assertThat(result.getValue().get(1).username).isEqualTo("virtual_user2");
        assertThat(result.getValue().get(1).liveness).isEqualTo(2);
        assertThat(result.getValue().get(2).username).isEqualTo("virtual_user1");
        assertThat(result.getValue().get(2).liveness).isEqualTo(1);
    }
}
