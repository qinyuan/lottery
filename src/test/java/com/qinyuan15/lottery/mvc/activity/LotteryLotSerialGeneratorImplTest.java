package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.LotteryActivityDao;
import org.junit.Test;

public class LotteryLotSerialGeneratorImplTest {
    @Test
    public void testNext() throws Exception {
        LotteryActivity activity = new LotteryActivityDao().getInstance(1);
        LotteryLotSerialGenerator generator = new LotteryLotSerialGeneratorImpl(activity);
        for (int i = 0; i < 20; i++) {
            System.out.println(generator.next());
        }
    }
}
