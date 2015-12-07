package com.qinyuan15.lottery.mvc.activity.tracker;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import com.qinyuan15.lottery.mvc.dao.LotteryLotDao;
import com.qinyuan15.lottery.mvc.dao.VirtualUserDao;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RealLotMonitorTest extends DatabaseTestCase {
    @Test
    public void testGetNoTrackLots() throws Exception {
        /*
         * there are two real lot in activity 2, so there are two no track lots
         */
        RealLotMonitor realLotMonitor = new RealLotMonitor(2);
        assertThat(realLotMonitor.getNoTrackLots()).hasSize(2);

        /*
         * there is no real lot in activity 1, so there is no no track lot
         */
        realLotMonitor = new RealLotMonitor(1);
        assertThat(realLotMonitor.getNoTrackLots()).isEmpty();

        /*
         * add a virtual lot to activity 1, but there is no real lot in activity 1
         */
        final int serialNumber = 10257;
        LotteryLotDao lotteryLotDao = new LotteryLotDao();
        lotteryLotDao.add(1, 1, serialNumber, true);
        assertThat(realLotMonitor.getNoTrackLots()).isEmpty();

        /*
         * add a real lot to activity 1, notice that the virtual lot is ambiguous lot,
         * so there is one no track lot
         */
        lotteryLotDao.add(1, 1, serialNumber, false);
        assertThat(realLotMonitor.getNoTrackLots()).hasSize(1);

        /*
         * change virtual lot to inactive, do not affect the result
         */
        VirtualUserDao virtualUserDao = new VirtualUserDao();
        virtualUserDao.deactivate(virtualUserDao.getInstance(1));
        assertThat(realLotMonitor.getNoTrackLots()).hasSize(1);

        /*
         * change virtual lot to active, there is no no track lot
         */
        virtualUserDao.activate(virtualUserDao.getInstance(1));
        assertThat(realLotMonitor.getNoTrackLots()).isEmpty();

        /*
         * add a real lot to same serial number, do not affect the result
         */
        lotteryLotDao.add(1, 2, serialNumber, false);
        assertThat(realLotMonitor.getNoTrackLots()).isEmpty();

        /*
         * add a real lot to another serial number, there is one no track lot
         */
        lotteryLotDao.add(1, 2, serialNumber + 1, false);
        assertThat(realLotMonitor.getNoTrackLots()).hasSize(1);
        assertThat(realLotMonitor.getNoTrackLots().get(0).getUserId()).isEqualTo(2);
    }
}
