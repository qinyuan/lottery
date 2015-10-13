package com.qinyuan15.lottery.mvc.account;

import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.mvc.security.UserRole;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

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
        // build sql
        String orderedRecordTable = "SELECT * FROM login_record ";
        if (!includeAdmin) {
            orderedRecordTable += "WHERE user_id in (SELECT id FROM user WHERE role='" + UserRole.NORMAL + "') ";
        } else {
            orderedRecordTable += "WHERE user_id in (SELECT id FROM user)";
        }
        orderedRecordTable += "ORDER BY login_time ASC";
        String groupedRecordTable = "SELECT * FROM (" + orderedRecordTable + ") t GROUP BY user_id";
        String sql = "SELECT location,COUNT(*) FROM (" + groupedRecordTable + ") r GROUP BY location";

        // execute sql
        List<Object[]> rows = new HibernateListBuilder().buildBySQL(sql);

        // fetch data
        Map<String, Integer> map = new HashMap<>();
        int allUserCount = 0;
        for (Object[] row : rows) {
            String location = simplifyLocation((String) row[0]);
            int count = ((Number) row[1]).intValue();
            allUserCount += count;

            if (map.containsKey(location)) {
                map.put(location, map.get(location) + count);
            } else {
                map.put(location, count);
            }
        }

        // sort data
        List<Pair<String, Integer>> list = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            list.add(Pair.of(entry.getKey(), entry.getValue()));
        }
        Collections.sort(list, new Comparator<Pair<String, Integer>>() {
            @Override
            public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
                return o2.getRight() - o1.getRight();
            }
        });

        // calculate user haven't login in
        int expectedAllUserCount = includeAdmin ? new UserDao().countAllUsers() : new UserDao().countNormalUsers();
        int noLoginUserCount = expectedAllUserCount - allUserCount;
        if (noLoginUserCount > 0) {
            list.add(Pair.of("注册但未登录", noLoginUserCount));
        }

        return list;
    }

    public List<Pair<String, Integer>> count() {
        return count(false);
    }

    private String simplifyLocation(String location) {
        if (StringUtils.isBlank(location)) {
            LOGGER.error("location is empty: {}", location);
            return location;
        }

        if (location.matches(".+省.+市\\s+.+")) { // if location matches format such as '广东省深圳市'
            return location.replaceAll(".+省", "").replaceAll("市\\s+.*", "");
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
