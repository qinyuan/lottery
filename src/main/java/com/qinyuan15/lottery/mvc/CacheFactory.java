package com.qinyuan15.lottery.mvc;

import com.qinyuan.lib.lang.Cache;

public class CacheFactory {
    private final static Cache INSTANCE = new Cache();

    public static Cache getInstance() {
        return INSTANCE;
    }
}
