package com.qinyuan15.lottery.mvc.lottery;

import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.LotteryActivityDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;

/**
 * Class to update result of lottery automatically
 * Created by qinyuan on 15-8-5.
 */
public class LotteryResultUpdater {
    private final static Logger LOGGER = LoggerFactory.getLogger(LotteryResultUpdater.class);
    private DecimalFormat lotNumberFormat;

    public LotteryResultUpdater(DecimalFormat lotNumberFormat) {
        this.lotNumberFormat = lotNumberFormat;
    }

    public void update(int activityId, String dualColoredBallResult) {
        LotteryActivity activity = new LotteryActivityDao().getInstance(activityId);
        if (activity == null) {
            LOGGER.error("invalid activityId: {}", activityId);
            return;
        }

        int phase = activity.getDualColoredBallTerm();
        int participantCount = new LotteryLotCounter().count(activity);
        if (participantCount == 0) {
            return;
        }

        int winner = new WinnerCalculator().run(Long.parseLong(dualColoredBallResult), participantCount);
        String winnerString = lotNumberFormat.format(winner);

        String announcementTemplate = AppConfig.getLotteryAnnouncementTemplate();
        if (!StringUtils.hasText(announcementTemplate)) {
            return;
        }
        String announcement = new AnnouncementPlaceholderConverter(
                phase, dualColoredBallResult, winnerString, participantCount).convert(announcementTemplate);
        new LotteryActivityDao().updateResult(activityId, winnerString, announcement);
    }
}
