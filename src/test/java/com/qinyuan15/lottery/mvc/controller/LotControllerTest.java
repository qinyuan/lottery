package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.LotteryActivityDao;
import org.junit.Test;

public class LotControllerTest {
    @Test
    public void testTakeLottery() throws Exception {
        LotteryActivity activity = new LotteryActivityDao().getActiveInstanceByCommodityId(1);
        System.out.println(activity);
    }
}
