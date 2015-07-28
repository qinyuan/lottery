package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.utils.DateUtils;
import com.qinyuan15.utils.hibernate.HibernateUtils;

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
