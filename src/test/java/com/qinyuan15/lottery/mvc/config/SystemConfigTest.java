package com.qinyuan15.lottery.mvc.config;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SystemConfigTest extends DatabaseTestCase {
    @Test
    public void testWebsiteIntroductionLink() {
        assertThat(AppConfig.sys.getWebsiteIntroductionLink()).isNull();
        String string = nextString();
        AppConfig.sys.updateWebsiteIntroductionLink(string);
        assertThat(AppConfig.sys.getWebsiteIntroductionLink()).isEqualTo(string);
    }

    @Test
    public void testIndexHeaderLeftLogo() {
        assertThat(AppConfig.sys.getIndexHeaderLeftLogo()).isNull();
        String string = nextString();
        AppConfig.sys.updateIndexHeaderLeftLogo(string);
        assertThat(AppConfig.sys.getIndexHeaderLeftLogo()).isEqualTo(string);
    }

    @Test
    public void testIndexHeaderSlogan() {
        assertThat(AppConfig.sys.getIndexHeaderSlogan()).isNull();
        String string = nextString();
        AppConfig.sys.updateIndexHeaderSlogan(string);
        assertThat(AppConfig.sys.getIndexHeaderSlogan()).isEqualTo(string);
    }

    @Test
    public void testFooterPoster() {
        assertThat(AppConfig.sys.getFooterPoster()).isNull();
        String string = nextString();
        AppConfig.sys.updateFooterPoster(string);
        assertThat(AppConfig.sys.getFooterPoster()).isEqualTo(string);
    }

    @Test
    public void testFooterText() {
        assertThat(AppConfig.sys.getFooterText()).isNull();
        String string = nextString();
        AppConfig.sys.updateFooterText(string);
        assertThat(AppConfig.sys.getFooterText()).isEqualTo(string);
    }

    @Test
    public void testCommodityHeaderLeftLogo() {
        assertThat(AppConfig.sys.getCommodityHeaderLeftLogo()).isNull();
        String string = nextString();
        AppConfig.sys.updateCommodityHeaderLeftLogo(string);
        assertThat(AppConfig.sys.getCommodityHeaderLeftLogo()).isEqualTo(string);
    }

    @Test
    public void testFavicon() {
        assertThat(AppConfig.sys.getFavicon()).isNull();
        String string = nextString();
        AppConfig.sys.updateFavicon(string);
        assertThat(AppConfig.sys.getFavicon()).isEqualTo(string);
    }

    @Test
    public void testTelValidateDescriptionPage() {
        assertThat(AppConfig.sys.getTelValidateDescriptionPage()).isNull();
        String string = nextString();
        AppConfig.sys.updateTelValidateDescriptionPage(string);
        assertThat(AppConfig.sys.getTelValidateDescriptionPage()).isEqualTo(string);
    }

    @Test
    public void testForumPage() {
        assertThat(AppConfig.sys.getForumImage()).isNull();
        String string = nextString();
        AppConfig.sys.updateForumImage(string);
        assertThat(AppConfig.sys.getForumImage()).isEqualTo(string);
    }

    private String nextString() {
        return RandomStringUtils.randomAlphanumeric(20);
    }
}
