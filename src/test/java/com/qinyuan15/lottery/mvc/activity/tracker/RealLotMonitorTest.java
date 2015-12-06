package com.qinyuan15.lottery.mvc.activity.tracker;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import com.qinyuan15.lottery.mvc.dao.LotteryLotDao;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RealLotMonitorTest extends DatabaseTestCase {
    @Test
    public void testGetNoTrackLots() throws Exception {
        RealLotMonitor realLotMonitor = new RealLotMonitor(2);
        assertThat(realLotMonitor.getNoTrackLots()).hasSize(2);

        realLotMonitor = new RealLotMonitor(1);
        assertThat(realLotMonitor.getNoTrackLots()).isEmpty();

        LotteryLotDao lotteryLotDao = new LotteryLotDao();
        lotteryLotDao.add(1, 1, 1, true);
        assertThat(realLotMonitor.getNoTrackLots()).isEmpty();

        lotteryLotDao.add(1, 1, 1, false);
        assertThat(realLotMonitor.getNoTrackLots()).isEmpty();

        lotteryLotDao.add(1, 2, 2, false);
        assertThat(realLotMonitor.getNoTrackLots()).hasSize(1);
        assertThat(realLotMonitor.getNoTrackLots().get(0).getUserId()).isEqualTo(2);
    }
}
