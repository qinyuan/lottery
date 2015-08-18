package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan15.lottery.mvc.activity.LotCounter;
import com.qinyuan15.lottery.mvc.activity.SeckillLotCounter;

public class SeckillActivity extends AbstractActivity {
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    protected LotCounter getLotCounter() {
        return new SeckillLotCounter();
    }
}
