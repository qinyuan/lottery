package com.qinyuan15.lottery.mvc.activity.tracker;

import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.LotteryLot;
import com.qinyuan15.lottery.mvc.dao.LotteryLotDao;
import com.qinyuan15.lottery.mvc.dao.LotterySameLotDao;
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
        } else if (activity.getExpire()) {
            LOGGER.warn("activity is expired");
            return;
        }

        String winningNumber = activity.getWinningNumber();
        if (StringUtils.isBlank(winningNumber)) {
            return;
        }

        LotterySameLotDao sameLotDao = new LotterySameLotDao();
        List<LotterySameLotDao.User> users = new LotterySameLotDao().getUsers(activity.getId(), winningNumber);
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
