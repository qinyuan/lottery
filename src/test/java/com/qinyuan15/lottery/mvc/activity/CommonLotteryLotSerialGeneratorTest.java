package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan15.lottery.mvc.activity.lot.CommonLotteryLotSerialGenerator;
import com.qinyuan15.lottery.mvc.activity.lot.DualColoredBallLotteryLotSerialGenerator;
import com.qinyuan15.lottery.mvc.activity.lot.LotteryLotNumberTrueValidator;
import com.qinyuan15.lottery.mvc.activity.lot.LotteryLotSerialGenerator;
import com.qinyuan15.lottery.mvc.dao.LotteryActivityDao;
import org.junit.Test;

public class CommonLotteryLotSerialGeneratorTest {
    @Test
    public void testNext() throws Exception {
        LotteryLotSerialGenerator generator = getTestInstance();
        for (int i = 0; i < 20; i++) {
            System.out.println(generator.next());
        }
    }

    public static CommonLotteryLotSerialGenerator getTestInstance() {
        CommonLotteryLotSerialGenerator serialGenerator = new CommonLotteryLotSerialGenerator(
                new LotteryLotNumberTrueValidator(), new DualColoredBallLotteryLotSerialGenerator());
        serialGenerator.setActivity(new LotteryActivityDao().getInstance(2));
        return serialGenerator;
    }
}
