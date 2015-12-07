package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LotterySameLotDao {
    private final static Logger LOGGER = LoggerFactory.getLogger(LotterySameLotDao.class);


    public int getMaxLiveness(int activityId, int serialNumber) {
        User user = getMaxLivenessUser(activityId, serialNumber);
        return user == null ? 0 : user.liveness;
    }

    public User getMaxLivenessRealUser(List<User> users) {
        User maxLivenessRealUser = null;
        for (User user : users) {
            if (user.virtual) {
                continue;
            }
            if (maxLivenessRealUser == null || user.liveness > maxLivenessRealUser.liveness) {
                maxLivenessRealUser = user;
            }
        }
        return maxLivenessRealUser;
    }

    public User getMaxLivenessRealUser(int activityId, int serialNumber) {
        return getMaxLivenessRealUser(getUsers(activityId, serialNumber));
    }

    public User getMaxLivenessVirtualUser(List<User> users) {
        User maxLivenessVirtualUser = null;
        for (User user : users) {
            if (!user.virtual) {
                continue;
            }
            if (maxLivenessVirtualUser == null || user.liveness > maxLivenessVirtualUser.liveness) {
                maxLivenessVirtualUser = user;
            }
        }
        return maxLivenessVirtualUser;
    }

    public User getMaxLivenessUser(List<User> users) {
        User maxLivenessUser = null;
        for (User user : users) {
            if (maxLivenessUser == null || user.liveness > maxLivenessUser.liveness) {
                maxLivenessUser = user;
            }
        }
        return maxLivenessUser;
    }

    public User getMaxLivenessUser(int activityId, int serialNumber) {
        return getMaxLivenessUser(new LotterySameLotDao().getUsers(activityId, serialNumber));
    }

    public List<SimpleUser> getSimpleUsers(int activityId, int serialNumber) {
        List<SimpleUser> simpleUsers = new ArrayList<>();
        for (User user : getUsers(activityId, serialNumber)) {
            simpleUsers.add(new SimpleUser(user.username, user.liveness));
        }
        return simpleUsers;
    }

    public List<SimpleUser> getSimpleUsers(int activityId, String serialNumber) {
        return getSimpleUsers(activityId, Integer.parseInt(serialNumber));
    }

    public List<User> getUsers(int activityId, int serialNumber) {
        List<User> users = new ArrayList<>();
        VirtualUserDao virtualUserDao = new VirtualUserDao();
        LotteryLivenessDao lotteryLivenessDao = new LotteryLivenessDao();

        for (Object[] objects : queryLots(activityId, serialNumber)) {
            int userId = (Integer) objects[0];
            boolean virtual = BooleanUtils.isTrue((Boolean) objects[3]);
            String username = virtual ? (String) objects[2] : (String) objects[1];
            if (StringUtils.isBlank(username)) {
                LOGGER.warn("no name use found, activityId: {}, userId: {}, virtual: {}", activityId, userId, virtual);
                continue;
            }
            int liveness = virtual ? virtualUserDao.getLiveness(userId) : lotteryLivenessDao.getLiveness(userId);
            users.add(new User(username, liveness, virtual));
        }

        return sort(users);
    }

    public List<User> getUsers(int activityId, String serialNumber) {
        return getUsers(activityId, Integer.parseInt(serialNumber));
    }

    private <T extends SimpleUser> List<T> sort(List<T> users) {
        // sort list
        Collections.sort(users, new Comparator<SimpleUser>() {
            @Override
            public int compare(SimpleUser o1, SimpleUser o2) {
                return -(o1.liveness - o2.liveness);
            }
        });
        return users;
    }

    private List<Object[]> queryLots(int activityId, int serialNumber) {
        // query data from database
        String sql = "SELECT l.user_id,u.username AS u_name,vu.username AS vu_name,virtual FROM lottery_lot AS l " +
                "LEFT JOIN user AS u ON (l.user_id=u.id AND (l.virtual IS NULL or l.virtual=false)) " +
                "LEFT JOIN virtual_user AS vu ON (l.user_id=vu.id AND (l.virtual=true))";
        return new HibernateListBuilder().addEqualFilter("activity_id", activityId)
                .addEqualFilter("serial_number", serialNumber)
                .buildBySQL(sql);
    }

    public static class SimpleUser {
        public final String username;
        public final int liveness;

        private SimpleUser(String username, int liveness) {
            this.username = username;
            this.liveness = liveness;
        }
    }

    public static class User extends SimpleUser {
        public final boolean virtual;

        private User(String username, int liveness, boolean virtual) {
            super(username, liveness);
            this.virtual = virtual;
        }
    }
}
