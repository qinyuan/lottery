package com.qinyuan15.lottery.mvc.mail;

import com.qinyuan.lib.contact.mail.EmailDao;
import com.qinyuan.lib.contact.mail.MailSenderBuilder;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan15.lottery.mvc.dao.MailSendRecordDao;
import com.qinyuan15.lottery.mvc.dao.User;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class NormalMailSender implements MailSender {
    private final static Logger LOGGER = LoggerFactory.getLogger(NormalMailSender.class);
    private final EmailDao emailDao = new EmailDao();
    private final MailSendRecordDao recordDao = new MailSendRecordDao();

    public void send(List<Integer> accountIds, List<Integer> userIds, String subject, String content) {
        if (accountIds == null || accountIds.size() == 0) {
            LOGGER.error("accountIds is empty: {}", accountIds);
            return;
        }

        if (userIds == null || userIds.size() == 0) {
            LOGGER.error("userIds is empty: {}", userIds);
            return;
        }

        Integer mailId = emailDao.add(subject, content);

        for (Integer accountId : accountIds) {
            if (!IntegerUtils.isPositive(accountId)) {
                continue;
            }

            for (Integer userId : userIds) {
                if (!IntegerUtils.isPositive(userId)) {
                    continue;
                }

                send(accountId, userId, subject, content);
                recordDao.add(accountId, userId, mailId);
            }
        }
    }

    private void send(int accountId, int userId, String subject, String content) {
        User user = new UserDao().getInstance(userId);

        NormalMailPlaceholderConverter placeholderConverter = new NormalMailPlaceholderConverter(user.getUsername());
        subject = placeholderConverter.convert(subject);
        content = placeholderConverter.convert(content);

        new MailSenderBuilder().build(accountId).send(user.getEmail(), subject, content);
    }
}
