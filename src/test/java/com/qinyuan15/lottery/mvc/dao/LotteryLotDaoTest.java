package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import com.qinyuan15.lottery.mvc.activity.CommonLotteryLotSerialGeneratorTest;
import com.qinyuan15.lottery.mvc.activity.LotteryLotSerialGenerator;
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
    public void testgetUsers() {
        List<LotteryLotDao.SimpleUser> simpleUsers = dao.getUsers(2, 10257);
        assertThat(simpleUsers).hasSize(1);
        assertThat(simpleUsers.get(0).username).isEqualTo("normal-user1");
        assertThat(simpleUsers.get(0).liveness).isEqualTo(25);

        new LotteryLotDao().add(2, 4, 10257);

        simpleUsers = dao.getUsers(2, 10257);
        assertThat(simpleUsers).hasSize(2);
        assertThat(simpleUsers.get(0).username).isEqualTo("normal-user1");
        assertThat(simpleUsers.get(0).liveness).isEqualTo(25);
        assertThat(simpleUsers.get(1).username).isEqualTo("normal-user2");
        assertThat(simpleUsers.get(1).liveness).isEqualTo(13);

        assertThat(dao.getUsers(2, 10258)).isEmpty();
        assertThat(dao.getUsers(1, 10257)).isEmpty();
    }

    @Test
    public void testgetUsers2() {
        assertThat(dao.getUsers(2, "010257")).hasSize(1);
        assertThat(dao.getUsers(2, "10257")).hasSize(1);
        assertThat(dao.getUsers(2, "10258")).isEmpty();
    }
}
