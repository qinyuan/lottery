package com.qinyuan15.lottery.mvc.account;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import com.qinyuan15.lottery.mvc.config.AppConfig;
import com.qinyuan15.lottery.mvc.dao.TelChangeLogDao;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RemainingTelChangeTimesCalculatorTest extends DatabaseTestCase {

    @Test
    public void test() {
        RemainingTelChangeTimesCalculator calculator = new RemainingTelChangeTimesCalculator(3);
        assertThat(calculator.isInfiniteTimes()).isTrue();
        assertThat(calculator.getTotalTimes()).isZero();
        assertThat(calculator.getUsedTimes()).isZero();
        assertThat(calculator.getRemainingTimes()).isZero();

        AppConfig.updateMaxTelModificationTimes(20);
        calculator = new RemainingTelChangeTimesCalculator(3);
        assertThat(calculator.isInfiniteTimes()).isFalse();
        assertThat(calculator.getTotalTimes()).isEqualTo(20);
        assertThat(calculator.getUsedTimes()).isZero();
        assertThat(calculator.getRemainingTimes()).isEqualTo(20);

        new TelChangeLogDao().add(2, "13000000000", "15000000000");
        calculator = new RemainingTelChangeTimesCalculator(3);
        assertThat(calculator.isInfiniteTimes()).isFalse();
        assertThat(calculator.getTotalTimes()).isEqualTo(20);
        assertThat(calculator.getUsedTimes()).isZero();
        assertThat(calculator.getRemainingTimes()).isEqualTo(20);

        new TelChangeLogDao().add(3, null, "13000000000");
        calculator = new RemainingTelChangeTimesCalculator(3);
        assertThat(calculator.isInfiniteTimes()).isFalse();
        assertThat(calculator.getTotalTimes()).isEqualTo(20);
        assertThat(calculator.getUsedTimes()).isEqualTo(1);
        assertThat(calculator.getRemainingTimes()).isEqualTo(19);

        new TelChangeLogDao().add(3, "13000000000", "15000000000");
        calculator = new RemainingTelChangeTimesCalculator(3);
        assertThat(calculator.isInfiniteTimes()).isFalse();
        assertThat(calculator.getTotalTimes()).isEqualTo(20);
        assertThat(calculator.getUsedTimes()).isEqualTo(2);
        assertThat(calculator.getRemainingTimes()).isEqualTo(18);
    }
}
