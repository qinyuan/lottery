package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.LotteryLiveness;
import com.qinyuan15.lottery.mvc.dao.SystemInfoSendRecordDao;
import com.qinyuan15.lottery.mvc.dao.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class LivenessIncreaseSystemInfoSender extends LivenessIncreaseInfoSender {
    private final static Logger LOGGER = LoggerFactory.getLogger(LivenessIncreaseSystemInfoSender.class);

    @Override
    protected void doSend(User user, LotteryActivity activity, LotteryLiveness liveness, PlaceholderConverter placeholderConverter) {
        String content = AppConfig.getLivenessIncreaseSystemInfoTemplate();
        if (StringUtils.hasText(content)) {
            new SystemInfoSendRecordDao().add(user.getId(), placeholderConverter.convert(content));
        } else {
            LOGGER.error("Content is empty");
        }
    }
}
