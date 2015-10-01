package com.qinyuan15.lottery.mvc;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test AppConfig
 * Created by qinyuan on 15-6-16.
 */
public class AppConfigTest {
    @Test
    public void testGetAppHost() {
        assertThat(AppConfig.getAppHost()).isEqualTo("http://qinyuan:8080/lottery/");
    }

    @Test
    public void testGetIndexHeaderLeftLogo() throws Exception {
        System.out.println(AppConfig.getIndexHeaderLeftLogo());
    }

    @Test
    public void testAllocateLotterySerialNumberInAdvance() {
        assertThat(AppConfig.allocateLotterySerialInAdvance()).isTrue();
    }
}
