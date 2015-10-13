package com.qinyuan15.lottery.mvc.activity;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DualColoredBallPhaseTest {
    @Test
    public void testToFullTerm() throws Exception {
        assertThat(DualColoredBallPhase.toFullPhase(2015, 13)).isEqualTo(2015013);
    }
}
