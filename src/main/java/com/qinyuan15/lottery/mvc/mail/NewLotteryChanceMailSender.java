package com.qinyuan15.lottery.mvc.mail;

import com.qinyuan.lib.contact.mail.MailSenderBuilder;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan15.lottery.mvc.activity.NewLotteryChanceInfoSender;
import com.qinyuan15.lottery.mvc.config.AppConfig;
import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Send mail to user if the user has new lottery chance
 * Created by qinyuan on 15-8-4.
 */
public class NewLotteryChanceMailSender extends NewLotteryChanceInfoSender {
    private final static Logger LOGGER = LoggerFactory.getLogger(NewLotteryChanceMailSender.class);

    @Override
    protected void doSend(User user, LotteryActivity activity, PlaceholderConverter placeholderConverter) {
        Integer mailAccountId = AppConfig.lottery.getNewChanceMailAccountId();
        if (!IntegerUtils.isPositive(mailAccountId)) {
            LOGGER.error("Invalid mailAccountId: {}", mailAccountId);
            return;
        }
        String subject = AppConfig.lottery.getNewChanceMailSubjectTemplate();
        if (StringUtils.isBlank(subject)) {
            LOGGER.error("Subject is empty");
            return;
        }
        String content = AppConfig.lottery.getNewChanceMailContentTemplate();
        if (StringUtils.isBlank(content)) {
            LOGGER.error("Content is empty");
            return;
        }

        subject = placeholderConverter.convert(subject);
        content = placeholderConverter.convert(content);
        new MailSenderBuilder().build(mailAccountId).send(user.getEmail(), subject, content);
    }
}
