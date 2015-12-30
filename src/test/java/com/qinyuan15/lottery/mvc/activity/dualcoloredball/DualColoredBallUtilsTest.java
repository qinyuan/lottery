package com.qinyuan15.lottery.mvc.activity.dualcoloredball;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class DualColoredBallUtilsTest {
    @Test
    public void testRand() throws Exception {
        Set<Integer> values = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            int value = DualColoredBallUtils.rand();
            assertThat(value).isGreaterThanOrEqualTo(DualColoredBallUtils.MIN_NUMBER);
            assertThat(value).isLessThanOrEqualTo(DualColoredBallUtils.MAX_NUMBER);
            values.add(value);
        }
        assertThat(values).hasSize(DualColoredBallUtils.MAX_NUMBER - DualColoredBallUtils.MIN_NUMBER + 1);
    }
}
