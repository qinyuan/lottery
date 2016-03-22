package com.qinyuan15.lottery.mvc.config;

import com.qinyuan.lib.config.AppConfigDao;
import com.qinyuan.lib.lang.Cache;

/**
 * Class to deal with configuration recorded in database
 * Created by qinyuan on 16-3-22.
 */
class DatabaseConfig {
    protected final static AppConfigDao dao = new AppConfigDao();
    protected final static Cache cache = new Cache();

    protected String getValue(final String key) {
        return (String) cache.getValue(key, new Cache.Source() {
            @Override
            public Object getValue() {
                return dao.get(key);
            }
        });
    }

    protected void saveToDatabase(String key, String value) {
        dao.save(key, value);
        cache.addValue(key, value);
    }
}
