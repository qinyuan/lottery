package com.qinyuan15.lottery.mvc.login;

import com.qinyuan15.lottery.mvc.dao.User;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import com.qinyuan15.utils.hibernate.HibernateListBuilder;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Count user by their register location
 * Created by qinyuan on 15-8-15.
 */
public class RegisterLocationCounter {
    private final static Logger LOGGER = LoggerFactory.getLogger(RegisterLocationCounter.class);

    /**
     * @return user number group by register location
     */
    public List<Pair<String, Integer>> count(boolean includeAdmin) {
        String orderedRecordTable = "SELECT * FROM login_record ";
        if (!includeAdmin) {
            orderedRecordTable += "WHERE user_id in (SELECT id FROM user WHERE role='" + User.NORMAL + "') ";
        }
        orderedRecordTable += "ORDER BY login_time ASC";
        String groupedRecordTable = "SELECT * FROM (" + orderedRecordTable + ") t GROUP BY user_id";
        String sql = "SELECT location,COUNT(*) FROM (" + groupedRecordTable + ") r GROUP BY location";

        List<Object[]> rows = new HibernateListBuilder().buildBySQL(sql);
        List<Pair<String, Integer>> result = new ArrayList<>();
        int allUserCount = 0;
        for (Object[] row : rows) {
            String location = (String) row[0];
            int count = ((Number) row[1]).intValue();
            allUserCount += count;
            result.add(Pair.of(simplifyLocation(location), count));
        }

        int expectedAllUserCount = includeAdmin ? new UserDao().countAllUsers() : new UserDao().countNormalUsers();
        int noLoginUserCount = expectedAllUserCount - allUserCount;
        if (noLoginUserCount > 0) {
            result.add(Pair.of("注册但未登录", noLoginUserCount));
        }

        return result;
    }

    public List<Pair<String, Integer>> count() {
        return count(false);
    }

    private String simplifyLocation(String location) {
        if (!StringUtils.hasText(location)) {
            LOGGER.error("location is empty: {}", location);
            return location;
        }

        location = location.trim();
        int provinceIndex = location.lastIndexOf("省 ");
        if (provinceIndex >= 0 && provinceIndex < location.length() - 2) {
            location = location.substring(provinceIndex + 2);
        }
        if (location.endsWith("市")) {
            location = location.substring(0, location.length() - 1);
        }
        return location;
    }
}
