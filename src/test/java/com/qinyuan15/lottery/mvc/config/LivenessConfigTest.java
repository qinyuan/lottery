package com.qinyuan15.lottery.mvc.config;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LivenessConfigTest extends DatabaseTestCase {
    private LivenessConfig config = new LivenessConfig();

    @Test
    public void testShareSucceedLiveness() {
        assertThat(config.getShareSucceedLiveness()).isNull();

        int number = RandomUtils.nextInt(0, 10000);
        config.updateShareSucceedLiveness(number);
        assertThat(config.getShareSucceedLiveness()).isEqualTo(number);
    }

    @Test
    public void testRemindLivenessIncreaseBySystemInfo() {
        assertThat(config.getRemindIncreaseBySystemInfo()).isNull();

        config.updateRemindIncreaseBySystemInfo(true);
        assertThat(config.getRemindIncreaseBySystemInfo()).isTrue();
        config.updateRemindIncreaseBySystemInfo(false);
        assertThat(config.getRemindIncreaseBySystemInfo()).isFalse();
    }

    @Test
    public void testLivenessIncreaseSystemInfoTemplate() {
        assertThat(config.getIncreaseSystemInfoTemplate()).isNull();

        String string = RandomStringUtils.randomAlphanumeric(2000);
        config.updateIncreaseSystemInfoTemplate(string);
        assertThat(config.getIncreaseSystemInfoTemplate()).isEqualTo(string);
    }
}
