package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.lottery.mvc.activity.LotCounter;
import com.qinyuan15.lottery.mvc.activity.SeckillLotCounter;

public class SeckillActivity extends AbstractActivity {
    @Override
    protected LotCounter getLotCounter() {
        return new SeckillLotCounter();
    }
}
