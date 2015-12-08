package com.qinyuan15.lottery.mvc.activity.tracker;

import com.qinyuan15.lottery.mvc.activity.dualcoloredball.DualColoredBallPhase;
import com.qinyuan15.lottery.mvc.dao.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class WinnerManager {
    private final static Logger LOGGER = LoggerFactory.getLogger(WinnerManager.class);

    public void setWinner(LotteryActivity activity) {
        if (activity == null) {
            LOGGER.error("activity is null");
            return;
        }

        DualColoredBallPhase phase = new DualColoredBallPhase(activity.getDualColoredBallTerm());
        String result = new DualColoredBallRecordDao().getResult(phase.year, phase.term);
        if (StringUtils.isBlank(result)) {
            LOGGER.info("result of activity is blank, activityId: {}, result: {}", activity.getId(), result);
            return;
        }

        int winnerNumber = Integer.parseInt(StringUtils.right(result, 6));
        LotterySameLotDao sameLotDao = new LotterySameLotDao();
        List<LotterySameLotDao.User> users = new LotterySameLotDao().getUsers(activity.getId(), winnerNumber);
        if (users.size() == 0) { /// there is no real or virtual user take the winner lot
            return;
        }

        LotterySameLotDao.User maxLivenessRealUser = sameLotDao.getMaxLivenessRealUser(users);
        if (maxLivenessRealUser == null) { // there is no real user take the winner lot
            return;
        }

        LotterySameLotDao.User maxLivenessVirtualUser = sameLotDao.getMaxLivenessVirtualUser(users);
        if (maxLivenessVirtualUser == null) {
            // TODO create a tracker and take a lot
            return;
        }

        if (maxLivenessVirtualUser.liveness > maxLivenessRealUser.liveness) {
            return;
        }

        LotteryLot lot = new LotteryLotDao().getInstance(maxLivenessVirtualUser.lotId);
        new Tracker(lot, true).exceed();
    }
}
