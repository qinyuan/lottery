package com.qinyuan15.lottery.mvc.config;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AppConfigTest extends DatabaseTestCase {
    @Test
    public void testMaxTelModificationTimes() {
        assertThat(AppConfig.getMaxTelModificationTimes()).isNull();

        int number = RandomUtils.nextInt(0, 10000);
        AppConfig.updateMaxTelModificationTimes(number);
        assertThat(AppConfig.getMaxTelModificationTimes()).isEqualTo(number);
    }

    @Test
    public void testSupportPageImage() {
        assertThat(AppConfig.getSupportPageImage()).isNull();

        String string = RandomStringUtils.randomAlphanumeric(2000);
        AppConfig.updateSupportPageImage(string);
        assertThat(AppConfig.getSupportPageImage()).isEqualTo(string);
    }

    @Test
    public void testSupportPageText() {
        assertThat(AppConfig.getSupportPageText()).isNull();

        String string = RandomStringUtils.randomAlphanumeric(2000);
        AppConfig.updateSupportPageText(string);
        assertThat(AppConfig.getSupportPageText()).isEqualTo(string);
    }
}
