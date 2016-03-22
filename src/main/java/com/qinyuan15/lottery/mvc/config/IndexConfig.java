package com.qinyuan15.lottery.mvc.config;

import com.qinyuan.lib.lang.IntegerUtils;

public class IndexConfig extends DatabaseConfig {
    /////////////////////// index image cycle interval start ///////////////////
    private final static String INDEX_IMAGE_CYCLE_INTERVAL_KEY = "indexImageCycleInterval";
    private final static int DEFAULT_INDEX_IMAGE_CYCLE_INTERVAL = 10;

    public int getIndexImageCycleInterval() {
        String string = getValue(INDEX_IMAGE_CYCLE_INTERVAL_KEY);
        return IntegerUtils.isPositive(string) ? Integer.parseInt(string) : DEFAULT_INDEX_IMAGE_CYCLE_INTERVAL;
    }

    public void updateIndexImageCycleInterval(int indexImageCycleInterval) {
        saveToDatabase(INDEX_IMAGE_CYCLE_INTERVAL_KEY, String.valueOf(indexImageCycleInterval));
    }
    ////////////////////// index image cycle interval end ///////////////////////
}
