package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan15.lottery.mvc.config.AppConfig;
import com.qinyuan15.lottery.mvc.activity.lot.SeckillLotCounter;
import com.qinyuan15.lottery.mvc.dao.SeckillActivity;
import com.qinyuan15.lottery.mvc.dao.SeckillActivityDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        int participantCount = new SeckillLotCounter().count(activity);
        String winners = activity.getWinners();
        String announcementTemplate = AppConfig.seckill.getAnnouncementTemplate();
        String announcement = new SeckillAnnouncementPlaceholderConverter(
                winners, participantCount).convert(announcementTemplate);
        new SeckillActivityDao().updateResult(activityId, winners, announcement);
    }
}
