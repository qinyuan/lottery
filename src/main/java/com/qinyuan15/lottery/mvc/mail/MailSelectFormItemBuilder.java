package com.qinyuan15.lottery.mvc.mail;

import com.qinyuan.lib.contact.mail.MailAccountDao;
import com.qinyuan.lib.mvc.controller.SelectFormItemsBuilder;

import java.util.List;

public class MailSelectFormItemBuilder {
    public List<SelectFormItemsBuilder.SelectFormItem> build() {
        return new SelectFormItemsBuilder().build(new MailAccountDao().getInstances(), "username");
    }
}
