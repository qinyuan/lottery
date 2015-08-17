package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.SystemInfoSendRecordDao;
import com.qinyuan15.lottery.mvc.dao.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class NewLotteryChanceSystemInfoSender extends NewLotteryChanceInfoSender {
    private final static Logger LOGGER = LoggerFactory.getLogger(NewLotteryChanceSystemInfoSender.class);

    @Override
    protected void doSend(User user, LotteryActivity activity, PlaceholderConverter placeholderConverter) {
        String content = AppConfig.getNewLotteryChanceSystemInfoTemplate();
        if (!StringUtils.hasText(content)) {
            LOGGER.error("Content is empty");
            return;
        }

        content = placeholderConverter.convert(content);
        new SystemInfoSendRecordDao().add(user.getId(), content);
    }
}
