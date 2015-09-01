package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.dao.SeckillActivity;
import com.qinyuan15.lottery.mvc.dao.SeckillActivityDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Class to update result of lottery automatically
 * Created by qinyuan on 15-8-5.
 */
public class SeckillResultUpdater {
    private final static Logger LOGGER = LoggerFactory.getLogger(SeckillResultUpdater.class);

    public void update(int activityId) {
        SeckillActivity activity = new SeckillActivityDao().getInstance(activityId);
        if (activity == null) {
            LOGGER.error("invalid activityId: {}", activityId);
            return;
        }

        int participantCount = new SeckillLotCounter().countReal(activityId);
        if (participantCount == 0) {
            return;
        }

        String winners = activity.getWinners();
        String announcementTemplate = AppConfig.getSeckillAnnouncementTemplate();
        if (!StringUtils.hasText(announcementTemplate)) {
            return;
        }
        String announcement = new SeckillAnnouncementPlaceholderConverter(
                winners, participantCount).convert(announcementTemplate);
        new SeckillActivityDao().updateResult(activityId, winners, announcement);
    }
}
