package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import com.qinyuan15.lottery.mvc.activity.lot.LotteryLotCounter;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LotteryLotCounterTest extends DatabaseTestCase {
    private LotteryLotCounter counter = new LotteryLotCounter();

    @Test
    public void testGetAvailableLotCount() {
        int count = new LotteryLotCounter().getAvailableLotCount(11, 2);
        System.out.println(count);
    }

    @Test
    public void testCountByUser() {
        assertThat(counter.countByUser(1)).isZero();
        assertThat(counter.countByUser(3)).isEqualTo(1);
        assertThat(counter.countByUser(4)).isEqualTo(1);
        assertThat(counter.countByUser(5)).isZero();
    }
}
