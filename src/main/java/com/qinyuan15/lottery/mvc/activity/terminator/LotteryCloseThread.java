package com.qinyuan15.lottery.mvc.activity.terminator;

import com.qinyuan.lib.lang.time.DateUtils;
import com.qinyuan15.lottery.mvc.activity.LotteryActivityUtils;
import com.qinyuan15.lottery.mvc.activity.lot.LotteryLotCounter;
import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.LotteryActivityDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class LotteryCloseThread extends LotteryHandlerThread {
    private final static Logger LOGGER = LoggerFactory.getLogger(LotteryResultThread.class);

    public LotteryCloseThread(LotteryActivity activity, Map<Integer, ? extends Thread> threads) {
        super(activity, threads);
    }

    @Override
    protected boolean doLoop() {
        if (!DateUtils.isDateOrDateTime(activity.getCloseTime())) {
            return true;
        }

        try {
            if (LotteryActivityUtils.shouldBeClosed(activity)) {
                new LotteryActivityDao().close(activity);
                //closeThreads.remove(activity.getId());
                return false;
            }
            new LotteryLotCounter().count(activity);    // force program to update virtual participants
        } catch (Throwable e) {
            e.printStackTrace();
            LOGGER.error("Fail to close activity whose id is {}, info: {}", activity.getId(), e);
        }
        return true;
    }

    @Override
    protected long getSleepTime() {
        return LotteryActivityUtils.getRemainingTimeToClose(activity);
    }
}
