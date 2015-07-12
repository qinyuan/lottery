package com.qinyuan15.lottery.mvc.lottery;

import com.qinyuan15.lottery.mvc.dao.LotteryLot;
import com.qinyuan15.lottery.mvc.dao.LotteryLotDao;
import com.qinyuan15.lottery.mvc.dao.User;

import java.util.List;

/**
 * Class to create lottery lot
 * Created by qinyuan on 15-7-12.
 */
public class LotteryLotCreator {
    private final Integer activityId;
    private final User user;

    public LotteryLotCreator(Integer activityId, User user) {
        this.activityId = activityId;
        this.user = user;
    }

    public CreateResult create() {
        //LotteryLotDao dao = new Lottery
        List<LotteryLot> lots = LotteryLotDao.factory()
                .setActivityId(activityId)
                .setUserId(user.getId())
                .getInstances();

        boolean newLot;
        if (lots.size() < getAvailableLotCount()) {
            LotteryLotDao lotDao = new LotteryLotDao();
            Integer id = lotDao.add(activityId, user.getId(), new LotteryLotSerialGeneratorImpl(activityId));
            lots.add(lotDao.getInstance(id));
            newLot = true;
        } else {
            newLot = false;
        }
        return new CreateResult(lots, newLot);
    }

    private int getAvailableLotCount() {
        // TODO take liveness into consideration
        return 1;
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
