package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.HibernateUtils;
import com.qinyuan.lib.lang.DateUtils;

public class MailSendRecordDao {
    public Integer add(Integer mailAccountId, Integer userId, Integer mailId) {
        MailSendRecord record = new MailSendRecord();
        record.setMailAccountId(mailAccountId);
        record.setUserId(userId);
        record.setMailId(mailId);
        record.setSendTime(DateUtils.nowString());
        return HibernateUtils.save(record);
    }
}
