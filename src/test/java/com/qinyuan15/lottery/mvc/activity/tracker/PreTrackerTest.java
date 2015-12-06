package com.qinyuan15.lottery.mvc.activity.tracker;

import com.google.common.collect.Lists;
import com.qinyuan.lib.database.test.DatabaseTestCase;
import com.qinyuan15.lottery.mvc.activity.DualColoredBallLotteryLotSerialGenerator;
import com.qinyuan15.lottery.mvc.dao.LotteryLot;
import com.qinyuan15.lottery.mvc.dao.LotteryLotDao;
import com.qinyuan15.lottery.mvc.dao.VirtualUser;
import com.qinyuan15.lottery.mvc.dao.VirtualUserDao;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class PreTrackerTest extends DatabaseTestCase {
    @Test
    public void testTakeLot() throws Exception {
        LotteryLot lot;
        LotteryLotDao lotteryLotDao = new LotteryLotDao();

        /*
         * if there is no real lot, preTracker will take randomly generated serial number
         */
        System.out.print("\nno real lot: ");
        for (int i = 0; i < 4; i++) {
            Integer lotId = buildPreTracker(1, true, 1).takeLot();
            assertThat(lotId).isEqualTo(3);

            lot = lotteryLotDao.getInstance(lotId);
            assertThat(lot.getVirtual()).isTrue();
            assertThat(lot.getUser()).isNull();
            assertThat(lot.getUserId()).isEqualTo(1);
            System.out.print(lot.getSerialNumber() + " ");
            setUp();
        }

        /*
         * if there is no real lot, preTracker will take serial number of real lot
         */
        List<Integer> serialsOfRealLot = Lists.newArrayList(10257, 217892);
        Set<Integer> serialsOfVirtualLot = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            Integer lotId = buildPreTracker(1, true, 2).takeLot();
            assertThat(lotId).isEqualTo(3);

            lot = lotteryLotDao.getInstance(lotId);
            assertThat(lot.getVirtual()).isTrue();
            assertThat(lot.getUserId()).isEqualTo(1);
            assertThat(lot.getSerialNumber()).isIn(serialsOfRealLot);
            serialsOfVirtualLot.add(lot.getSerialNumber());
            setUp();
        }
        assertThat(serialsOfVirtualLot).hasSameSizeAs(serialsOfVirtualLot).containsAll(serialsOfRealLot);

        /*
         * after serial numbers of real lots are taken by virtual lot,
         * preTracker will take randomly generated serial number
         */
        System.out.print("\nafter real lot tracked: ");
        serialsOfVirtualLot.clear();
        Integer lotId = buildPreTracker(1, true, 2).takeLot();
        serialsOfVirtualLot.add(new LotteryLotDao().getInstance(lotId).getSerialNumber());
        lotId = buildPreTracker(1, true, 2).takeLot();
        serialsOfVirtualLot.add(new LotteryLotDao().getInstance(lotId).getSerialNumber());
        assertThat(serialsOfVirtualLot).hasSize(2).containsAll(serialsOfRealLot);
        for (int i = 0; i < 5; i++) {
            lotId = buildPreTracker(1, true, 2).takeLot();
            lot = lotteryLotDao.getInstance(lotId);
            System.out.print(lot.getSerialNumber() + " ");
        }

        System.out.print("\nlot of inactive virtual user: ");
        for (int i = 0; i < 4; i++) {
            setUp();
            lotId = buildPreTracker(1, false, 2).takeLot();
            assertThat(lotId).isEqualTo(3);

            lot = lotteryLotDao.getInstance(lotId);
            assertThat(lot.getVirtual()).isTrue();
            assertThat(lot.getUser()).isNull();
            assertThat(lot.getUserId()).isEqualTo(1);
            System.out.print(lot.getSerialNumber() + " ");
        }
    }

    private PreTracker buildPreTracker(int virtualUserId, boolean active, int activityId) {
        VirtualUserDao virtualUserDao = new VirtualUserDao();
        VirtualUser virtualUser = virtualUserDao.getInstance(virtualUserId);
        if (active) {
            virtualUserDao.activate(virtualUser);
        } else {
            virtualUserDao.deactivate(virtualUser);
        }
        return PreTracker.build(Lists.newArrayList(virtualUser), activityId,
                new DualColoredBallLotteryLotSerialGenerator()).get(0);
    }
}
