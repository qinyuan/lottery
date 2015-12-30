package com.qinyuan15.lottery.mvc.activity.lot;

import com.qinyuan15.lottery.mvc.dao.LotteryActivity;

/**
 * Class to generate serial number of lottery lot
 * Created by qinyuan on 15-7-8.
 */
public interface LotteryLotSerialGenerator {
    int next();

    void setActivity(LotteryActivity activity);
}
