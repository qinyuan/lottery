package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.LotteryActivityDao;
import com.qinyuan15.lottery.mvc.dao.User;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import com.qinyuan15.lottery.mvc.mail.MailPlaceholderConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Class to send information when user gain new activity chance
 * Created by qinyuan on 15-8-11.
 */
public abstract class NewLotteryChanceInfoSender {
    private final static Logger LOGGER = LoggerFactory.getLogger(NewLotteryChanceInfoSender.class);

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

        PlaceholderConverter converter = new PlaceholderConverter(user.getUsername(), activity.getCommodityId());
        doSend(user, activity, converter);
    }

    protected abstract void doSend(User user, LotteryActivity activity, PlaceholderConverter placeholderConverter);

    protected class PlaceholderConverter implements MailPlaceholderConverter {
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
