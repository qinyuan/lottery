package com.qinyuan15.lottery.mvc.config;

import com.qinyuan.lib.config.AppConfigDao;
import com.qinyuan.lib.lang.Cache;
import com.qinyuan.lib.lang.IntegerUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to deal with configuration recorded in database
 * Created by qinyuan on 16-3-22.
 */
abstract class DatabaseConfig {
    private final static Logger LOGGER = LoggerFactory.getLogger(DatabaseConfig.class);

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

    protected Integer parseInteger(String value) {
        if (value != null && NumberUtils.isNumber(value)) {
            return Integer.parseInt(value);
        } else {
            return null;
        }
    }

    public Boolean parseBoolean(String value) {
        return value == null ? null : Boolean.parseBoolean(value);
    }

    protected Integer parsePositiveInteger(String value) {
        Integer integer = parseInteger(value);
        return IntegerUtils.isPositive(integer) ? integer : null;
    }

    protected void saveToDatabase(String key, String value) {
        if (value == null) {
            LOGGER.warn("value is null");
            value = "";
        }

        dao.save(key, value);
        cache.addValue(key, value);
    }

    protected void saveToDatabase(String key, Integer value) {
        if (value == null) {
            saveToDatabase(key, (String) null);
        } else {
            saveToDatabase(key, String.valueOf(value));
        }
    }

    public void saveToDatabase(String key, Boolean value) {
        if (value == null) {
            saveToDatabase(key, (String) null);
        } else if (value) {
            saveToDatabase(key, "true");
        } else {
            saveToDatabase(key, "false");
        }
    }
}
