package com.qinyuan15.lottery.mvc.activity.tracker;

import com.qinyuan15.lottery.mvc.dao.LotteryLot;
import com.qinyuan15.lottery.mvc.dao.LotterySameLotDao;
import com.qinyuan15.lottery.mvc.dao.VirtualUser;
import com.qinyuan15.lottery.mvc.dao.VirtualUserDao;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;

class Tracker {
    private LotteryLot lot;
    private VirtualUserDao virtualUserDao = new VirtualUserDao();

    Tracker(LotteryLot lot) {
        this.lot = lot;
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

        VirtualUser virtualUser = getVirtualUser();
        // only max liveness virtual user follow real lot
        LotterySameLotDao.User maxLivenessVirtualUser = sameLotDao.getMaxLivenessVirtualUser(users);
        if (maxLivenessVirtualUser == null ||
                (!maxLivenessVirtualUser.username.equals(virtualUser.getUsername()))) {
            return;
        }

        int upperBound = (int) (maxLivenessUser.liveness * 0.9);
        int lowerBound = (int) (maxLivenessUser.liveness * 0.6);
        if (upperBound > 0) {
            virtualUserDao.changeLiveness(virtualUser, RandomUtils.nextInt(lowerBound, upperBound));
        }
    }

    void exceed() {
        LotterySameLotDao sameLotDao = new LotterySameLotDao();
        LotterySameLotDao.User maxLivenessRealUser = sameLotDao.getMaxLivenessRealUser(
                lot.getActivityId(), lot.getSerialNumber());

        if (maxLivenessRealUser == null || maxLivenessRealUser.virtual) {
            return;
        }

        VirtualUser virtualUser = getVirtualUser();
        if (virtualUser.getLiveness() != null && virtualUser.getLiveness() > maxLivenessRealUser.liveness) {
            return;
        }

        int liveness = RandomUtils.nextInt(maxLivenessRealUser.liveness + 1, maxLivenessRealUser.liveness + 4);
        virtualUserDao.changeLiveness(virtualUser, liveness);
    }

    private VirtualUser getVirtualUser() {
        return virtualUserDao.getInstance(lot.getUserId());
    }

    @Override
    public String toString() {
        return "activity:" + lot.getActivityId() + ",userId:" + lot.getUserId();
    }
}
