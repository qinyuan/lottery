package com.qinyuan15.lottery.mvc.activity.tracker;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import com.qinyuan15.lottery.mvc.dao.LotteryLot;
import com.qinyuan15.lottery.mvc.dao.LotteryLotDao;
import com.qinyuan15.lottery.mvc.dao.VirtualUserDao;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TrackerTest extends DatabaseTestCase {
    private VirtualUserDao virtualUserDao = new VirtualUserDao();

    @Test
    public void testFollow() throws Exception {
        int lotId = new LotteryLotDao().add(1, 1, 10257, true);
        LotteryLot lot = new LotteryLotDao().getInstance(lotId);
        assertThat(virtualUserDao.getInstance(1).getLiveness()).isNull();

        /*
         * if there is no real lot, liveness stay unchanged
         */
        new Tracker(lot).follow();
        assertThat(virtualUserDao.getInstance(1).getLiveness()).isNull();
        new Tracker(lot).follow();
        assertThat(virtualUserDao.getInstance(1).getLiveness()).isNull();

        /*
         * if there is real lot, liveness will be changed
         */
        lotId = new LotteryLotDao().add(2, 1, 10257, true);
        lot = new LotteryLotDao().getInstance(lotId);
        assertThat(virtualUserDao.getInstance(1).getLiveness()).isNull();
        new Tracker(lot).follow();
        assertThat(virtualUserDao.getInstance(1).getLiveness()).isNotNull().isGreaterThanOrEqualTo(0).isLessThan(10);
        new Tracker(lot).follow();
        assertThat(virtualUserDao.getInstance(1).getLiveness()).isGreaterThanOrEqualTo(15).isLessThan(22);

        /*
         * if there is more than one active tracker, only the one with highest liveness follow
         */
        lotId = new LotteryLotDao().add(2, 2, 10257, true);
        lot = new LotteryLotDao().getInstance(lotId);
        assertThat(virtualUserDao.getInstance(2).getLiveness()).isNull();
        new Tracker(lot).follow();
        assertThat(virtualUserDao.getInstance(2).getLiveness()).isNull();
    }

    @Test
    public void testExceed() throws Exception {
        /*
         * inactive virtual user won't exceed real user
         */
        int lotId = new LotteryLotDao().add(2, 1, 10257, true);
        LotteryLot lot = new LotteryLotDao().getInstance(lotId);
        assertThat(virtualUserDao.getInstance(1).getLiveness()).isNull();
        new Tracker(lot).exceed();
        assertThat(virtualUserDao.getInstance(1).getLiveness()).isNull();

        /*
         * active virtual user can exceed real user
         */
        new Tracker(lot).exceed();
        assertThat(virtualUserDao.getInstance(1).getLiveness()).isGreaterThan(25).isLessThan(29);
    }
}
