package com.qinyuan15.lottery.mvc.dao;

import org.junit.Test;

public class MailAccountReferenceValidatorTest {
    @Test
    public void testIsUsed() throws Exception {
        MailAccountReferenceValidator validator = new MailAccountReferenceValidator();
        System.out.println(validator.isUsed(1));
        System.out.println(validator.isUsed(2));
    }
}
