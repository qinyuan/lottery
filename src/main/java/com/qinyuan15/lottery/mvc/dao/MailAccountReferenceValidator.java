package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.hibernate.ReferenceValidator;

public class MailAccountReferenceValidator {
    private ReferenceValidator validator = new ReferenceValidator().add(MailSendRecord.class, "mailAccountId");

    public boolean isUsed(int mailAccountId) {
        return this.validator.isUsed(mailAccountId);
    }
}
