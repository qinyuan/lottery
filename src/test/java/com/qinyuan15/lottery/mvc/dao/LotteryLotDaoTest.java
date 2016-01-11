package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import com.qinyuan15.lottery.mvc.activity.CommonLotteryLotSerialGeneratorTest;
import com.qinyuan15.lottery.mvc.activity.lot.LotteryLotSerialGenerator;
import org.junit.Test;

import java.text.DecimalFormat;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LotteryLotDaoTest extends DatabaseTestCase {
    private LotteryLotDao dao = new LotteryLotDao();

    @Test
    public void testAdd() {
        assertThat(dao.count()).isEqualTo(2);
        LotteryLotSerialGenerator serialGenerator = CommonLotteryLotSerialGeneratorTest.getTestInstance();
        dao.add(2, 3, serialGenerator);
        assertThat(dao.count()).isEqualTo(3);
    }

    @Test
    public void testGetInstances() {
        assertThat(dao.getInstances()).hasSize(2);
        for (LotteryLot lot : dao.getInstances()) {
            assertThat(lot).isExactlyInstanceOf(LotteryLot.class);
        }
    }

    @Test
    public void testFactory() {
        List<LotteryLot> lots = LotteryLotDao.factory()
                .setActivityId(11).setUserId(2).getInstances();
        assertThat(lots).isEmpty();

        lots = LotteryLotDao.factory().setActivityId(2).getInstances();
        assertThat(lots).hasSize(2);

        lots = LotteryLotDao.factory().setActivityId(2).setUserId(3).getInstances();
        assertThat(lots).hasSize(1);
    }

    @Test
    public void testGetSerialNumbers() {
        List<Integer> integers = dao.getSerialNumbers(2, 3000, 5000);
        assertThat(integers).isEmpty();

        integers = dao.getSerialNumbers(2, 10000, 20000);
        assertThat(integers).hasSize(1);

        integers = dao.getSerialNumbers(2, 10000, 900000);
        assertThat(integers).hasSize(2);

        integers = dao.getSerialNumbers(2, 10000, 500);
        assertThat(integers).isEmpty();

        integers = dao.getSerialNumbers(1, 10000, 900000);
        assertThat(integers).isEmpty();
    }

    @Test
    public void testGetSerialNumbers2() {
        List<Integer> serialNumbers = dao.getSerialNumbers(2);
        assertThat(serialNumbers).hasSize(2);

        serialNumbers = dao.getSerialNumbers(1);
        assertThat(serialNumbers).isEmpty();
    }

    @Test
    public void testGetSerialNumbers3() {
        List<String> serialNumbers = dao.getSerialNumbers(2, 3);
        assertThat(serialNumbers).containsExactly("10257");
    }

    @Test
    public void testGetSerialNumbers4() {
        DecimalFormat format = new DecimalFormat("000000");
        List<String> serialNumbers = dao.getSerialNumbers(2, 3, format);
        assertThat(serialNumbers).containsExactly("010257");
    }

    @Test
    public void testGetVirtualSerialNumbers() {
        assertThat(dao.getVirtualSerialNumbers(2)).isEmpty();

        dao.add(2, 2, 1, false);
        assertThat(dao.getVirtualSerialNumbers(2)).isEmpty();

        dao.add(2, 2, 1, true);
        assertThat(dao.getVirtualSerialNumbers(2)).containsExactly(1);
    }
}
