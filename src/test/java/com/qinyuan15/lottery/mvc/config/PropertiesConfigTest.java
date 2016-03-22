package com.qinyuan15.lottery.mvc.config;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PropertiesConfigTest {
    @Test
    public void testGetAppHost() {
        assertThat(AppConfig.props.getAppHost()).isEqualTo("http://qinyuan:8080/lottery/");
    }

    @Test
    public void testGetIndexPageTitle() {
        assertThat(AppConfig.props.getIndexPageTitle()).isEqualTo("布迪网-满足烟瘾，拒绝伤害");
    }

    @Test
    public void testGetCommodityPageTitle() {
        assertThat(AppConfig.props.getCommodityPageTitle()).isEqualTo("布迪网-商品及活动");
    }

    @Test
    public void testIsOffline() {
        System.out.println(AppConfig.props.isOffline());
    }

    @Test
    public void testGetQQConnectAppId() {
        assertThat(AppConfig.props.getQQConnectAppId()).isEqualTo("101264653");
    }

    @Test
    public void testGetQQConnectAppKey() {
        assertThat(AppConfig.props.getQQConnectAppKey()).isEqualTo("a601a58696ce8658fd42604562eacc73");
    }

    @Test
    public void testGetQQConnectRedirectURI() {
        assertThat(AppConfig.props.getQQConnectRedirectURI()).isEqualTo("http%3A%2F%2Fwww.bud-vip.com%2Fregister-by-qq.html");
    }

    @Test
    public void testGetQQConnectScope() {
        assertThat(AppConfig.props.getQQConnectScope()).startsWith("get_user_info,");
    }

    @Test
    public void testAllocateLotterySerialInAdvance() {
        assertThat(AppConfig.props.allocateLotterySerialInAdvance()).isTrue();
    }
}
