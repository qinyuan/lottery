package com.qinyuan15.lottery.mvc.dao;

import org.junit.Test;

public class InvalidLotteryLotDaoTest {
    @Test
    public void testCount() throws Exception {
        InvalidLotteryLotDao dao = new InvalidLotteryLotDao();
        System.out.println(dao.count(15));
    }

    @Test
    public void testGetSerialNumbers() {
        InvalidLotteryLotDao dao = new InvalidLotteryLotDao();
        System.out.println(dao.getSerialNumbers(15));
    }
}
