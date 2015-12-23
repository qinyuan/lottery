package com.qinyuan15.lottery.mvc;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test AppConfig
 * Created by qinyuan on 15-6-16.
 */
public class AppConfigTest extends DatabaseTestCase {

    @Test
    public void testGetIndexPageTitle() {
        assertThat(AppConfig.getIndexPageTitle()).isEqualTo("布迪网-满足烟瘾，拒绝伤害");
    }

    @Test
    public void testGetCommodityPageTitle() {
        assertThat(AppConfig.getCommodityPageTitle()).isEqualTo("布迪网-商品及活动");
    }

    @Test
    public void testGetAppHost() {
        assertThat(AppConfig.getAppHost()).isEqualTo("http://qinyuan:8080/lottery/");
    }

    @Test
    public void testGetIsOffline() {
        System.out.println(AppConfig.isOffline());
    }

    @Test
    public void testGetQQConnectAppId() {
        assertThat(AppConfig.getQQConnectAppId()).isEqualTo("101264653");
    }

    @Test
    public void testGetQQConnectAppKey() {
        assertThat(AppConfig.getQQConnectAppKey()).isEqualTo("a601a58696ce8658fd42604562eacc73");
    }

    @Test
    public void testGetQQConnectRedirectURI() {
        assertThat(AppConfig.getQQConnectRedirectURI()).isEqualTo("http%3A%2F%2Fwww.bud-vip.com%2Fregister-by-qq.html");
    }

    @Test
    public void testGetQQConnectScope() {
        assertThat(AppConfig.getQQConnectScope()).startsWith("get_user_info,");
    }

    @Test
    public void testIndexHeaderLeftLogo() {
        assertThat(AppConfig.getIndexHeaderLeftLogo()).isNull();

        String string = RandomStringUtils.randomAlphanumeric(20);
        AppConfig.updateIndexHeaderLeftLogo(string);
        assertThat(AppConfig.getIndexHeaderLeftLogo()).isEqualTo(string);
    }

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
