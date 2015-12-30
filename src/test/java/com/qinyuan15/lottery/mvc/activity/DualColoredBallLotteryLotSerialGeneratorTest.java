package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan15.lottery.mvc.activity.lot.DualColoredBallLotteryLotSerialGenerator;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DualColoredBallLotteryLotSerialGeneratorTest {
    private DualColoredBallLotteryLotSerialGenerator serialGenerator =
            new DualColoredBallLotteryLotSerialGenerator();

    @Test
    public void testNext() throws Exception {
        for (int i = 0; i < 1000; i++) {
            assertThat(serialGenerator.next()).isGreaterThanOrEqualTo(10101)
                    .isLessThanOrEqualTo(333333);
        }
    }
}
