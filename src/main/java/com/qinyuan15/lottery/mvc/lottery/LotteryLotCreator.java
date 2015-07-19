package com.qinyuan15.lottery.mvc.lottery;

import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.dao.LotteryLivenessDao;
import com.qinyuan15.lottery.mvc.dao.LotteryLot;
import com.qinyuan15.lottery.mvc.dao.LotteryLotDao;
import com.qinyuan15.lottery.mvc.dao.User;
import com.qinyuan15.utils.IntegerUtils;

import java.util.List;

/**
 * Class to create lottery lot
 * Created by qinyuan on 15-7-12.
 */
public class LotteryLotCreator {
    private final Integer activityId;
    private final Integer continuousSerialLimit;
    private final User user;

    public LotteryLotCreator(Integer activityId, Integer continuousSerialLimit, User user) {
        this.activityId = activityId;
        this.continuousSerialLimit = continuousSerialLimit;
        this.user = user;
    }

    public CreateResult create() {
        List<LotteryLot> lots = LotteryLotDao.factory()
                .setActivityId(activityId)
                .setUserId(user.getId())
                .getInstances();

        boolean newLot;
        if (lots.size() < getAvailableLotCount()) {
            LotteryLotDao lotDao = new LotteryLotDao();
            Integer id = lotDao.add(activityId, user.getId(),
                    new LotteryLotSerialGeneratorImpl(activityId, continuousSerialLimit));
            lots.add(lotDao.getInstance(id));
            newLot = true;
        } else {
            newLot = false;
        }
        return new CreateResult(lots, newLot);
    }

    private int getAvailableLotCount() {
        int count = 1;

        Integer newLotLivness = AppConfig.getNewLotLiveness();
        if (!IntegerUtils.isPositive(newLotLivness)) {
            return count;
        }

        int livenesss = new LotteryLivenessDao().getLiveness(user.getId(), activityId);
        return count + livenesss / newLotLivness;
    }

    public static class CreateResult {
        private List<LotteryLot> lots;
        private boolean newLot;

        private CreateResult(List<LotteryLot> lots, boolean newLot) {
            this.lots = lots;
            this.newLot = newLot;
        }

        public List<LotteryLot> getLots() {
            return lots;
        }

        public boolean hasNewLot() {
            return newLot;
        }
    }
}
