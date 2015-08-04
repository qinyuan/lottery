package com.qinyuan15.lottery.mvc.mail;

import com.qinyuan15.utils.mail.MailAccountDao;
import com.qinyuan15.utils.mvc.controller.SelectFormItemsBuilder;

import java.util.List;

public class MailSelectFormItemBuilder {
    public List<SelectFormItemsBuilder.SelectFormItem> build() {
        return new SelectFormItemsBuilder().build(new MailAccountDao().getInstances(), "username");
    }
}
