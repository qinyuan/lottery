package com.qinyuan15.lottery.mvc.activity.tracker;

import com.qinyuan15.lottery.mvc.dao.LotteryLot;
import com.qinyuan15.lottery.mvc.dao.LotterySameLotDao;
import com.qinyuan15.lottery.mvc.dao.VirtualUser;
import com.qinyuan15.lottery.mvc.dao.VirtualUserDao;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;

class Tracker {
    private final static int MAX_LIVENESS_OF_INACTIVE_TRACKER = 10;
    private LotteryLot lot;
    private final boolean active;

    Tracker(LotteryLot lot, boolean active) {
        this.lot = lot;
        this.active = active;
    }

    void follow() {
        LotterySameLotDao sameLotDao = new LotterySameLotDao();
        List<LotterySameLotDao.User> users = sameLotDao.getUsers(
                lot.getActivityId(), lot.getSerialNumber());
        LotterySameLotDao.User maxLivenessUser = sameLotDao.getMaxLivenessUser(users);

        // only follow real lot
        if (maxLivenessUser == null || maxLivenessUser.virtual) {
            return;
        }

        VirtualUserDao virtualUserDao = new VirtualUserDao();
        VirtualUser virtualUser = virtualUserDao.getInstance(lot.getUserId());
        if (active) {
            // only max liveness virtual user follow real lot
            LotterySameLotDao.User maxLivenessVirtualUser = sameLotDao.getMaxLivenessVirtualUser(users);
            if (maxLivenessVirtualUser == null ||
                    (!maxLivenessVirtualUser.username.equals(virtualUser.getUsername()))) {
                return;
            }

            int upperBound = (int) (maxLivenessUser.liveness * 0.8);
            int lowerBound = (int) (maxLivenessUser.liveness * 0.6);
            if (upperBound > 0) {
                virtualUserDao.changeLiveness(virtualUser, RandomUtils.nextInt(lowerBound, upperBound));
            }
        } else {
            if (virtualUser.getLiveness() == null) {
                int upperBound = Math.min(maxLivenessUser.liveness, MAX_LIVENESS_OF_INACTIVE_TRACKER);
                virtualUserDao.changeLiveness(virtualUser, RandomUtils.nextInt(0, upperBound));
            }
        }
    }

    void exceed() {

    }

    @Override
    public String toString() {
        return "activity:" + lot.getActivityId() + ",userId:" + lot.getUserId() + ",active:" + active;
    }
}
