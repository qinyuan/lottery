package com.qinyuan15.lottery.mvc.activity;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DualColoredBallTermTest {
    @Test
    public void testToFullTerm() throws Exception {
        assertThat(DualColoredBallTerm.toFullTerm(2015, 13)).isEqualTo(2015013);
    }
}
