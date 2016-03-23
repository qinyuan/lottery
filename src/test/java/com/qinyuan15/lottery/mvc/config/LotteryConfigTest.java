package com.qinyuan15.lottery.mvc.config;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LotteryConfigTest extends DatabaseTestCase {
    private LotteryConfig config = new LotteryConfig();

    @Test
    public void testSinaWeiboTitle() {
        assertThat(config.getSinaWeiboTitle()).isNull();

        String string = nextString();
        config.updateSinaWeiboTitle(string);
        assertThat(config.getSinaWeiboTitle()).isEqualTo(string);
    }

    @Test
    public void testSinaWeiboIncludePicture() {
        assertThat(config.getSinaWeiboIncludePicture()).isNull();

        config.updateSinaWeiboIncludePicture(true);
        assertThat(config.getSinaWeiboIncludePicture()).isTrue();
        config.updateSinaWeiboIncludePicture(false);
        assertThat(config.getSinaWeiboIncludePicture()).isFalse();
    }

    @Test
    public void testQQTitle() {
        assertThat(config.getQQTitle()).isNull();

        String string = nextString();
        config.updateQQTitle(string);
        assertThat(config.getQQTitle()).isEqualTo(string);
    }

    @Test
    public void testQQSummary() {
        assertThat(config.getQQSummary()).isNull();

        String string = nextString();
        config.updateQQSummary(string);
        assertThat(config.getQQSummary()).isEqualTo(string);
    }

    @Test
    public void testQQIncludePicture() {
        assertThat(config.getQQIncludePicture()).isNull();

        config.updateQQIncludePicture(true);
        assertThat(config.getQQIncludePicture()).isTrue();
        config.updateQQIncludePicture(false);
        assertThat(config.getQQIncludePicture()).isFalse();
    }

    @Test
    public void testQzoneTitle() {
        assertThat(config.getQzoneTitle()).isNull();

        String string = nextString();
        config.updateQzoneTitle(string);
        assertThat(config.getQzoneTitle()).isEqualTo(string);
    }

    @Test
    public void testQzoneSummary() {
        assertThat(config.getQzoneSummary()).isNull();

        String string = nextString();
        config.updateQzoneSummary(string);
        assertThat(config.getQzoneSummary()).isEqualTo(string);
    }

    @Test
    public void testQzoneIncludePicture() {
        assertThat(config.getQzoneIncludePicture()).isNull();

        config.updateQzoneIncludePicture(true);
        assertThat(config.getQzoneIncludePicture()).isTrue();
        config.updateQzoneIncludePicture(false);
        assertThat(config.getQzoneIncludePicture()).isFalse();
    }

    @Test
    public void testRemindNewChanceByMail() {
        assertThat(config.getRemindNewChanceByMail()).isNull();

        config.updateRemindNewChanceByMail(true);
        assertThat(config.getRemindNewChanceByMail()).isTrue();
        config.updateRemindNewChanceByMail(false);
        assertThat(config.getRemindNewChanceByMail()).isFalse();
        config.updateRemindNewChanceByMail(null);
        assertThat(config.getRemindNewChanceByMail()).isFalse();
    }

    @Test
    public void testNewChanceMailSubjectTemplate() {
        assertThat(config.getNewChanceMailSubjectTemplate()).isNull();

        String string = nextString();
        config.updateNewChanceMailSubjectTemplate(string);
        assertThat(config.getNewChanceMailSubjectTemplate()).isEqualTo(string);
    }

    @Test
    public void testNewChanceMailContentTemplate() {
        assertThat(config.getNewChanceMailContentTemplate()).isNull();

        String string = nextString();
        config.updateNewChanceMailContentTemplate(string);
        assertThat(config.getNewChanceMailContentTemplate()).isEqualTo(string);
    }

    @Test
    public void testRemindNewChanceBySystemInfo() {
        assertThat(config.getRemindNewChanceBySystemInfo()).isNull();

        config.updateRemindNewChanceBySystemInfo(true);
        assertThat(config.getRemindNewChanceBySystemInfo()).isTrue();
        config.updateRemindNewChanceBySystemInfo(false);
        assertThat(config.getRemindNewChanceBySystemInfo()).isFalse();
    }

    @Test
    public void testNewChanceSystemInfoTemplate() {
        assertThat(config.getNewChanceSystemInfoTemplate()).isNull();

        String string = nextString();
        config.updateNewChanceSystemInfoTemplate(string);
        assertThat(config.getNewChanceSystemInfoTemplate()).isEqualTo(string);
    }

    @Test
    public void testAnnouncementTemplate() {
        assertThat(config.getAnnouncementTemplate()).isNull();

        String string = nextString();
        config.updateAnnouncementTemplate(string);
        assertThat(config.getAnnouncementTemplate()).isEqualTo(string);
    }

    @Test
    public void testRuleLink() {
        assertThat(config.getRuleLink()).isNull();

        String string = nextString();
        config.updateRuleLink(string);
        assertThat(config.getRuleLink()).isEqualTo(string);
    }

    @Test
    public void testNoTelInvalidLotSystemInfoTemplate() {
        assertThat(config.getNoTelInvalidLotSystemInfoTemplate()).isNull();

        String string = nextString();
        config.updateNoTelInvalidLotSystemInfoTemplate(string);
        assertThat(config.getNoTelInvalidLotSystemInfoTemplate()).isEqualTo(string);
    }

    @Test
    public void testInsufficientLivenessInvalidLotSystemInfoTemplate() {
        assertThat(config.getInsufficientLivenessInvalidLotSystemInfoTemplate()).isNull();

        String string = nextString();
        config.updateInsufficientLivenessInvalidLotSystemInfoTemplate(string);
        assertThat(config.getInsufficientLivenessInvalidLotSystemInfoTemplate()).isEqualTo(string);
    }

    @Test
    public void testNoTelLotteryCount() {
        assertThat(config.getNoTelLotCount()).isEqualTo(null);
        assertThat(config.getNoTelLotCountValue()).isZero();

        int number = RandomUtils.nextInt(0, 10000);
        config.updateNoTelLotCount(number);
        assertThat(config.getNoTelLotCount()).isEqualTo(number);
        assertThat(config.getNoTelLotCountValue()).isEqualTo(number);
    }

    @Test
    public void testNoTelLotPrice() {
        assertThat(config.getNoTelLotPrice()).isNull();
        assertThat(config.getNoTelLotPriceValue()).isZero();

        double number = RandomUtils.nextDouble(0, 10000);
        config.updateNoTelLotPrice(number);
        assertThat(config.getNoTelLotPrice()).isEqualTo(number);
        assertThat(config.getNoTelLotPriceValue()).isEqualTo(number);
    }

    @Test
    public void testNoTelLiveness() {
        assertThat(config.getNoTelLiveness()).isNull();

        int number = RandomUtils.nextInt(0, 10000);
        config.updateNoTelLiveness(number);
        assertThat(config.getNoTelLiveness()).isEqualTo(number);
    }

    private String nextString() {
        return RandomStringUtils.randomAlphanumeric(100);
    }
}
