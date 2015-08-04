package com.qinyuan15.lottery.mvc.lottery;

import com.qinyuan15.lottery.mvc.dao.LotteryLot;
import com.qinyuan15.lottery.mvc.dao.LotteryLotDao;

import java.util.List;

/**
 * Class to create lottery lot
 * Created by qinyuan on 15-7-12.
 */
public class LotteryLotCreator {
    private final Integer activityId;
    private final Integer continuousSerialLimit;
    private final int userId;

    public LotteryLotCreator(Integer activityId, Integer continuousSerialLimit, int userId) {
        this.activityId = activityId;
        this.continuousSerialLimit = continuousSerialLimit;
        this.userId = userId;
    }

    public CreateResult create() {
        List<LotteryLot> lots = LotteryLotDao.factory()
                .setActivityId(activityId)
                .setUserId(userId)
                .getInstances();

        boolean newLot;
        if (lots.size() < new LotteryLotCounter().getAvailableLotCount(activityId, userId)) {
            LotteryLotDao lotDao = new LotteryLotDao();
            Integer id = lotDao.add(activityId, userId,
                    new LotteryLotSerialGeneratorImpl(activityId, continuousSerialLimit));
            lots.add(lotDao.getInstance(id));
            newLot = true;
        } else {
            newLot = false;
        }
        return new CreateResult(lots, newLot);
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
