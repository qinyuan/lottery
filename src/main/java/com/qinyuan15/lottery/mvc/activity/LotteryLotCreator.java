package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.LotteryLot;
import com.qinyuan15.lottery.mvc.dao.LotteryLotDao;

import java.util.List;

/**
 * Class to create lottery lot
 * Created by qinyuan on 15-7-12.
 */
public class LotteryLotCreator {
    private final LotteryActivity activity;
    private final int userId;

    public LotteryLotCreator(LotteryActivity activity, int userId) {
        this.activity = activity;
        this.userId = userId;
    }

    public CreateResult create() {
        boolean newLot;
        if (new LotteryLotCounter().getAvailableLotCount(activity.getId(), userId) > 0) {
            new LotteryLotDao().add(activity.getId(), userId,
                    new LotteryLotSerialGeneratorImpl(activity));
            newLot = true;
        } else {
            newLot = false;
        }
        return new CreateResult(LotteryLotDao.factory().setActivityId(activity.getId()).setUserId(userId).getInstances(),
                newLot);
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
