package com.qinyuan15.lottery.mvc.config;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MailConfigTest extends DatabaseTestCase {
    @Test
    public void testActivateMailSubjectTemplate() {
        assertThat(AppConfig.mail.getActivateMailSubjectTemplate()).isNull();

        String string = nextString();
        AppConfig.mail.updateActivateMailSubjectTemplate(string);
        assertThat(AppConfig.mail.getActivateMailSubjectTemplate()).isEqualTo(string);
    }

    @Test
    public void testActivateMailContentTemplate() {
        assertThat(AppConfig.mail.getActivateMailContentTemplate()).isNull();

        String string = nextString();
        AppConfig.mail.updateActivateMailContentTemplate(string);
        assertThat(AppConfig.mail.getActivateMailContentTemplate()).isEqualTo(string);
    }

    @Test
    public void testActivateMailAccountId() {
        assertThat(AppConfig.mail.getActivateMailAccountId()).isNull();

        int id = nextInt();
        AppConfig.mail.updateActivateMailAccountId(id);
        assertThat(AppConfig.mail.getActivateMailAccountId()).isEqualTo(id);
    }

    @Test
    public void testRegisterMailSubjectTemplate() {
        assertThat(AppConfig.mail.getRegisterMailSubjectTemplate()).isNull();

        String string = nextString();
        AppConfig.mail.updateRegisterMailSubjectTemplate(string);
        assertThat(AppConfig.mail.getRegisterMailSubjectTemplate()).isEqualTo(string);
    }

    @Test
    public void testRegisterMailContentTemplate() {
        assertThat(AppConfig.mail.getRegisterMailContentTemplate()).isNull();

        String string = nextString();
        AppConfig.mail.updateRegisterMailContentTemplate(string);
        assertThat(AppConfig.mail.getRegisterMailContentTemplate()).isEqualTo(string);
    }

    @Test
    public void testRegisterMailAccountId() {
        assertThat(AppConfig.mail.getRegisterMailAccountId()).isNull();

        int id = nextInt();
        AppConfig.mail.updateRegisterMailAccountId(id);
        assertThat(AppConfig.mail.getRegisterMailAccountId()).isEqualTo(id);
    }

    @Test
    public void testResetPasswordMailSubjectTemplate() {
        assertThat(AppConfig.mail.getResetPasswordMailSubjectTemplate()).isNull();

        String string = nextString();
        AppConfig.mail.updateResetPasswordMailSubjectTemplate(string);
        assertThat(AppConfig.mail.getResetPasswordMailSubjectTemplate()).isEqualTo(string);
    }

    @Test
    public void testRestPasswordMailContentTemplate() {
        assertThat(AppConfig.mail.getResetPasswordMailContentTemplate()).isNull();

        String string = nextString();
        AppConfig.mail.updateResetPasswordMailContentTemplate(string);
        assertThat(AppConfig.mail.getResetPasswordMailContentTemplate()).isEqualTo(string);
    }

    @Test
    public void testResetPasswordMailAccountId() {
        assertThat(AppConfig.mail.getResetPasswordMailAccountId()).isNull();

        int id = nextInt();
        AppConfig.mail.updateResetPasswordMailAccountId(id);
        assertThat(AppConfig.mail.getResetPasswordMailAccountId()).isEqualTo(id);
    }

    @Test
    public void testResetEmailMailSubject() {
        assertThat(AppConfig.mail.getResetEmailMailSubjectTemplate()).isNull();

        String string = nextString();
        AppConfig.mail.updateResetEmailMailSubjectTemplate(string);
        assertThat(AppConfig.mail.getResetEmailMailSubjectTemplate()).isEqualTo(string);
    }

    @Test
    public void testResetEmailMailContent() {
        assertThat(AppConfig.mail.getResetEmailMailContentTemplate()).isNull();

        String string = nextString();
        AppConfig.mail.updateResetEmailMailContentTemplate(string);
        assertThat(AppConfig.mail.getResetEmailMailContentTemplate()).isEqualTo(string);
    }

    @Test
    public void testResetEmailMailAccountId() {
        assertThat(AppConfig.mail.getResetEmailMailAccountId()).isNull();

        int id = nextInt();
        AppConfig.mail.updateResetEmailMailAccountId(id);
        assertThat(AppConfig.mail.getResetEmailMailAccountId()).isEqualTo(id);
    }

    private String nextString() {
        return RandomStringUtils.randomAlphanumeric(200);
    }

    private int nextInt() {
        return RandomUtils.nextInt(1, 1000);
    }
}
