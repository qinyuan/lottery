package com.qinyuan15.lottery.mvc.mail;

import com.qinyuan.lib.contact.mail.MailAccount;
import com.qinyuan.lib.contact.mail.MailAccountDao;
import com.qinyuan.lib.mvc.controller.SelectFormItemsBuilder;

import java.util.List;

public class MailSelectFormItemBuilder {
    public List<SelectFormItemsBuilder.SelectFormItem> build() {
        //return new SelectFormItemsBuilder().build(new MailAccountDao().getInstances(), "username");
        return build(new MailAccountDao().getInstances());
    }

    public List<SelectFormItemsBuilder.SelectFormItem> build(List<MailAccount> accounts) {
        return new SelectFormItemsBuilder().build(accounts, "username");
    }
}
