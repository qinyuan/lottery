package com.qinyuan15.lottery.mvc.config;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QQListConfigTest extends DatabaseTestCase {
    private QQListConfig config = new QQListConfig();

    @Test
    public void testId() {
        assertThat(config.getId()).isNull();

        String string = RandomStringUtils.randomAlphanumeric(1000);
        config.updateId(string);
        assertThat(config.getId()).isEqualTo(string);
    }

    @Test
    public void testDescription() {
        assertThat(config.getDescription()).isNull();

        String string = RandomStringUtils.randomAlphanumeric(1000);
        config.updateDescription(string);
        assertThat(config.getDescription()).isEqualTo(string);
    }
}
