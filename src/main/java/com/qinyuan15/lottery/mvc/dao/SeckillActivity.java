package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.lottery.mvc.activity.lot.LotCounter;
import com.qinyuan15.lottery.mvc.activity.lot.SeckillLotCounter;

public class SeckillActivity extends AbstractActivity {
    @Override
    protected LotCounter getLotCounter() {
        return new SeckillLotCounter();
    }

    @Override
    public int getParticipantCount() {
        Integer expectCount = getExpectParticipantCount();
        int realCount = getRealParticipantCount();
        return expectCount == null || expectCount < realCount ? realCount : expectCount;
    }
}
