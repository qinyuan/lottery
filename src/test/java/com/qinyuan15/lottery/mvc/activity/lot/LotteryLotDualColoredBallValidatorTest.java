package com.qinyuan15.lottery.mvc.activity.lot;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LotteryLotDualColoredBallValidatorTest {
    private LotteryLotDualColoredBallValidator validator = new LotteryLotDualColoredBallValidator();

    @Test
    public void testValidate() throws Exception {
        assertThat(validator.validate(null, 313224).getKey()).isTrue();
        assertThat(validator.validate(null, 13224).getKey()).isTrue();
        assertThat(validator.validate(null, 101010).getKey()).isFalse();
        assertThat(validator.validate(null, 10).getKey()).isFalse();
        assertThat(validator.validate(null, 343224).getKey()).isFalse();
    }
}
