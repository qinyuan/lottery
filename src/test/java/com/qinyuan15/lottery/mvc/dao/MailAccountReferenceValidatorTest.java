package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MailAccountReferenceValidatorTest extends DatabaseTestCase {
    @Test
    public void testIsUsed() {
        MailAccountReferenceValidator validator = new MailAccountReferenceValidator();
        assertThat(validator.isUsed(1)).isFalse();
        assertThat(validator.isUsed(2)).isTrue();
    }
}
