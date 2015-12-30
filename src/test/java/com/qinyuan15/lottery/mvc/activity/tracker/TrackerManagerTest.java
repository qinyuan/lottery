package com.qinyuan15.lottery.mvc.activity.tracker;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import com.qinyuan15.lottery.mvc.activity.lot.DualColoredBallLotteryLotSerialGenerator;
import com.qinyuan15.lottery.mvc.activity.lot.LotteryLotNumberTrueValidator;
import org.junit.Test;

public class TrackerManagerTest extends DatabaseTestCase {
    @Test
    public void testRun() throws Exception {
        TrackerManager manager = new TrackerManager(new LotteryLotNumberTrueValidator(),
                new DualColoredBallLotteryLotSerialGenerator());
        manager.run();
    }
}
