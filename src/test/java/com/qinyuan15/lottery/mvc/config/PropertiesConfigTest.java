package com.qinyuan15.lottery.mvc.config;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PropertiesConfigTest {
    @Test
    public void testGetIndexPageTitle() {
        assertThat(AppConfig.properties.getIndexPageTitle()).isEqualTo("布迪网-满足烟瘾，拒绝伤害");
    }

    @Test
    public void testGetCommodityPageTitle() {
        assertThat(AppConfig.properties.getCommodityPageTitle()).isEqualTo("布迪网-商品及活动");
    }

    @Test
    public void testGetAppHost() {
        assertThat(AppConfig.properties.getAppHost()).isEqualTo("http://qinyuan:8080/lottery/");
    }

    @Test
    public void testGetIsOffline() {
        System.out.println(AppConfig.properties.isOffline());
    }

    @Test
    public void testGetQQConnectAppId() {
        assertThat(AppConfig.properties.getQQConnectAppId()).isEqualTo("101264653");
    }

    @Test
    public void testGetQQConnectAppKey() {
        assertThat(AppConfig.properties.getQQConnectAppKey()).isEqualTo("a601a58696ce8658fd42604562eacc73");
    }

    @Test
    public void testGetQQConnectRedirectURI() {
        assertThat(AppConfig.properties.getQQConnectRedirectURI()).isEqualTo("http%3A%2F%2Fwww.bud-vip.com%2Fregister-by-qq.html");
    }

    @Test
    public void testGetQQConnectScope() {
        assertThat(AppConfig.properties.getQQConnectScope()).startsWith("get_user_info,");
    }
}
