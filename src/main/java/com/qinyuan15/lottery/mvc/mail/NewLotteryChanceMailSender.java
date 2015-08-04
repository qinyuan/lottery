package com.qinyuan15.lottery.mvc.mail;

import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.LotteryActivityDao;
import com.qinyuan15.lottery.mvc.dao.User;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import com.qinyuan15.utils.IntegerUtils;
import com.qinyuan15.utils.mail.MailSenderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Send mail to user if the user has new lottery chance
 * Created by qinyuan on 15-8-4.
 */
public class NewLotteryChanceMailSender {
    private final static Logger LOGGER = LoggerFactory.getLogger(NewLotteryChanceMailSender.class);

    public void send(Integer userId, Integer activityId) {
        User user = new UserDao().getInstance(userId);
        if (user == null) {
            LOGGER.error("User with id {} doesn't exists", userId);
            return;
        }

        LotteryActivity activity = new LotteryActivityDao().getInstance(activityId);
        if (activity == null) {
            LOGGER.error("Lottery activity with id {} doesn't exists", activityId);
            return;
        }

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

        // replace placeholder
        PlaceholderConverter converter = new PlaceholderConverter(user.getUsername(), activity.getCommodityId());
        subject = converter.convert(subject);
        content = converter.convert(content);

        new MailSenderBuilder().build(mailAccountId).send(user.getEmail(), subject, content);
    }

    private class PlaceholderConverter implements MailPlaceholderConverter {
        final String username;
        final int commodityId;

        private PlaceholderConverter(String username, int commodityId) {
            this.username = username;
            this.commodityId = commodityId;
        }

        @Override
        public String convert(String content) {
            if (!StringUtils.hasText(content)) {
                return content;
            }

            content = content.replace("{{user}}", username);

            String url = AppConfig.getAppHost() + "commodity.html?id=" + commodityId;
            content = content.replace("{{url}}", url);

            return content;
        }
    }
}
