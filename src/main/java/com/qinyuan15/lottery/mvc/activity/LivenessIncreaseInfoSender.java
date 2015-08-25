package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan15.lottery.mvc.dao.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to send information when liveness of user increases
 * Created by qinyuan on 15-8-11.
 */
public abstract class LivenessIncreaseInfoSender {
    private final static Logger LOGGER = LoggerFactory.getLogger(LivenessIncreaseInfoSender.class);

    public void send(LotteryLiveness liveness) {
        Integer spreadUserId = liveness.getSpreadUserId();
        User user = new UserDao().getInstance(spreadUserId);
        if (user == null) {
            LOGGER.error("User with id {} doesn't exists", spreadUserId);
            return;
        }

        Integer activityId = liveness.getActivityId();
        LotteryActivity activity = new LotteryActivityDao().getInstance(activityId);
        if (activity == null) {
            LOGGER.error("Lottery activity with id {} doesn't exists", activityId);
            return;
        }

        PlaceholderConverter converter = new PlaceholderConverter(user.getUsername(), activity.getCommodityId(), liveness);
        doSend(user, activity, liveness, converter);
    }

    protected abstract void doSend(User user, LotteryActivity activity, LotteryLiveness liveness,
                                   PlaceholderConverter placeholderConverter);

    protected class PlaceholderConverter extends NewLotteryChanceInfoSender.PlaceholderConverter {
        final LotteryLiveness liveness;

        PlaceholderConverter(String username, int commodityId, LotteryLiveness liveness) {
            super(username, commodityId);
            this.liveness = liveness;
        }

        @Override
        public String convert(String content) {
            content = super.convert(content);

            content = content.replace("{{add_l}}", String.valueOf(liveness.getLiveness()));

            String invitee = new UserDao().getInstance(liveness.getReceiveUserId()).getUsername();
            content = content.replace("{{invitee}}", invitee);

            Integer totalLiveness = new LotteryLivenessDao().getLiveness(liveness.getSpreadUserId());
            content = content.replace("{{liveness}}", String.valueOf(totalLiveness));

            return content;
        }
    }
}
