package com.qinyuan15.lottery.mvc.dao;

import org.junit.Test;

import java.util.List;

public class LotteryLotDaoTest {
    LotteryLotDao dao = new LotteryLotDao();

    @Test
    public void testAdd() throws Exception {
        /*
        for (int i = 0; i < 20; i++) {
            dao.add(26, 2, new LotteryLotSerialGeneratorImpl(26, 5));
        }*/
    }

    @Test
    public void testGetSerialNumbersByActivityId() {
        List<Integer> serialNumbers = dao.getSerialNumbersByActivityId(23);
        System.out.println(serialNumbers.size());
        System.out.println(serialNumbers);
    }

    @Test
    public void testGetInstances() {
        List<LotteryLot> lots = LotteryLotDao.factory()
                .setActivityId(11).setUserId(2).getInstances();
        System.out.println(lots.size());
    }
}
