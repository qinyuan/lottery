package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.dao.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class InvalidLotteryLotSystemInfoSender {
    private final static Logger LOGGER = LoggerFactory.getLogger(InvalidLotteryLotSystemInfoSender.class);

    public void send(Integer activityId) {
        send(new LotteryActivityDao().getInstance(activityId));
    }

    public void send(LotteryActivity activity) {
        List<Integer> noTelUserIds = new InvalidLotteryLotDao().getNoTelUserIds(activity);
        if (noTelUserIds.size() > 0) {
            sendNoTelUsers(noTelUserIds, activity.getTerm());
        }

        List<Integer> insufficientLivenessUserIds = new InvalidLotteryLotDao().getInsufficientLivenessUserIds(activity);
        if (insufficientLivenessUserIds.size() > 0) {
            sendInsufficientLivenessUsers(insufficientLivenessUserIds, activity.getTerm(),
                    activity.getMinLivenessToParticipate());
        }
    }

    private void sendNoTelUsers(List<Integer> userIds, Integer phase) {
        String template = AppConfig.getNoTelInvalidLotSystemInfoTemplate();
        if (StringUtils.isNotBlank(template)) {
            String content = new InvalidLotteryLotPlaceholderConverter(phase, 0, 0).convert(template);
            for (Integer userId : userIds) {
                new SystemInfoSendRecordDao().add(userId, content);
            }
        } else {
            LOGGER.error("no tel invalid lot system information template is blank: {}", template);
        }
    }

    private void sendInsufficientLivenessUsers(List<Integer> userIds, Integer phase, Integer minLiveness) {
        String template = AppConfig.getInsufficientLivenessInvalidLotSystemInfoTemplate();
        if (StringUtils.isNotBlank(template)) {
            for (Integer userId : userIds) {
                int liveness = new LotteryLivenessDao().getLiveness(userId);
                String content = new InvalidLotteryLotPlaceholderConverter(phase, liveness, minLiveness)
                        .convert(template);
                new SystemInfoSendRecordDao().add(userId, content);
            }
        } else {
            LOGGER.error("insufficient liveness invalid lot system information template is blank: {}", template);
        }
    }

}
