package com.qinyuan15.lottery.mvc;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import com.qinyuan15.lottery.mvc.config.AppConfig;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test AppConfig
 * Created by qinyuan on 15-6-16.
 */
public class AppConfigTest extends DatabaseTestCase {
    @Test
    public void testRegisterMailAccountId() {
        assertThat(AppConfig.getRegisterMailAccountId()).isNull();

        int id = RandomUtils.nextInt(1, 1000);
        AppConfig.updateRegisterMailAccountId(id);
        assertThat(AppConfig.getRegisterMailAccountId()).isEqualTo(id);
    }

    @Test
    public void testAllocateLotterySerialNumberInAdvance() {
        assertThat(AppConfig.allocateLotterySerialInAdvance()).isTrue();
    }
}
