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
        String appHost = AppConfig.getAppHost();
        assertThat(appHost).isEqualTo("http://localhost:8080/lottery/");
    }

    @Test
    public void testGetIndexHeaderLeftLogo() throws Exception {
        System.out.println(AppConfig.getIndexHeaderLeftLogo());
    }
}
