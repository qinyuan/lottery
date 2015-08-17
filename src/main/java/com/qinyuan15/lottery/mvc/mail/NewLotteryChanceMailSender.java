package com.qinyuan15.lottery.mvc.mail;

import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.User;
import com.qinyuan15.lottery.mvc.activity.NewLotteryChanceInfoSender;
import com.qinyuan15.utils.IntegerUtils;
import com.qinyuan15.utils.mail.MailSenderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Send mail to user if the user has new activity chance
 * Created by qinyuan on 15-8-4.
 */
public class NewLotteryChanceMailSender extends NewLotteryChanceInfoSender {
    private final static Logger LOGGER = LoggerFactory.getLogger(NewLotteryChanceMailSender.class);

    @Override
    protected void doSend(User user, LotteryActivity activity, PlaceholderConverter placeholderConverter) {
        Integer mailAccountId = AppConfig.getNewLotteryChanceMailAccountId();
        if (!IntegerUtils.isPositive(mailAccountId)) {
            LOGGER.error("Invalid mailAccountId: {}", mailAccountId);
            return;
        }
        String subject = AppConfig.getNewLotteryChanceMailSubjectTemplate();
        if (!StringUtils.hasText(subject)) {
            LOGGER.error("Subject is empty");
            return;
        }
        String content = AppConfig.getNewLotteryChanceMailContentTemplate();
        if (!StringUtils.hasText(content)) {
            LOGGER.error("Content is empty");
            return;
        }

        subject = placeholderConverter.convert(subject);
        content = placeholderConverter.convert(content);
        new MailSenderBuilder().build(mailAccountId).send(user.getEmail(), subject, content);
    }
}
