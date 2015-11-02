package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan15.lottery.mvc.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class InvalidLotteryLotDao {
    private final static Logger LOGGER = LoggerFactory.getLogger(InvalidLotteryLotDao.class);

    /**
     * count invalid lot number of certain lottery activity
     *
     * @param activityId id of activity to count
     * @return invalid lot number of the lottery activity
     */
    public int count(Integer activityId) {
        // validate activity
        LotteryActivity activity = new LotteryActivityDao().getInstance(activityId);
        if (activity == null) {
            LOGGER.error("no activity with id {}", activityId);
            return 0;
        }

        return getListBuilder(activity).countBySQL("lottery_lot");
    }

    private HibernateListBuilder getListBuilder(LotteryActivity activity) {
        String filter = "((" + getNoTelFilter(activity) + ")";
        Integer minLivenessToParticipate = activity.getMinLivenessToParticipate();
        if (IntegerUtils.isPositive(minLivenessToParticipate)) {
            filter += " OR (" + getInsufficientLivenessFilter(minLivenessToParticipate) + ")";
        }

        filter += ")";
        return new HibernateListBuilder().addEqualFilter("activity_id", activity.getId()).addFilter(filter);
    }

    private String getNoTelFilter(LotteryActivity activity) {
        String filter = "user_id IN (SELECT id FROM user WHERE tel IS NULL OR tel='')";

        if (activity.getCommodity().getPrice() <= AppConfig.getNoTelLotteryLotPriceValue()) {
            filter += " AND ";
            int noTelLotteryLotCount = AppConfig.getNoTelLotteryLotCountValue();
            filter += " user_id IN (SELECT user_id FROM lottery_lot GROUP BY user_id HAVING count(*)>"
                    + noTelLotteryLotCount + ")";
        }

        return filter;
    }

    private String getInsufficientLivenessFilter(int minLivenessToParticipate) {
        String filter = "(SELECT spread_user_id,SUM(liveness) AS liveness_sum FROM lottery_liveness " +
                "GROUP BY spread_user_id HAVING liveness_sum<" + minLivenessToParticipate + ")";
        filter = "(SELECT spread_user_id FROM " + filter + " AS t)";
        filter = "user_id IN (" + filter + ")";
        return filter;
    }

    /**
     * get ids of user who take part in certain activity
     *
     * @param activity lottery activity
     * @return ids of user who take part in certain activity
     */
    public List<Integer> getNoTelUserIds(LotteryActivity activity) {
        if (activity == null) {
            return new ArrayList<>();
        }
        return new HibernateListBuilder().addEqualFilter("activity_id", activity.getId())
                .addFilter(getNoTelFilter(activity))
                .buildBySQL("SELECT DISTINCT(user_id) FROM lottery_lot", Integer.class);
    }

    public List<Integer> getInsufficientLivenessUserIds(Integer activityId) {
        LotteryActivity activity = new LotteryActivityDao().getInstance(activityId);
        if (activity == null) {
            LOGGER.error("no activity with id {}", activityId);
            return new ArrayList<>();
        }
        return getInsufficientLivenessUserIds(activity);
    }

    public List<Integer> getInsufficientLivenessUserIds(LotteryActivity activity) {
        if (activity == null) {
            LOGGER.error("activity is null");
            return new ArrayList<>();
        }
        return new HibernateListBuilder().addEqualFilter("activity_id", activity.getId())
                .addFilter(getInsufficientLivenessFilter(activity.getMinLivenessToParticipate()))
                .buildBySQL("SELECT DISTINCT(user_id) FROM lottery_lot", Integer.class);
    }

    public List<Integer> getSerialNumbers(Integer activityId) {
        LotteryActivity activity = new LotteryActivityDao().getInstance(activityId);
        if (activity == null) {
            LOGGER.error("no activity with id {}", activityId);
            return new ArrayList<>();
        }

        return getListBuilder(activity).buildBySQL("SELECT serial_number FROM lottery_lot", Integer.class);
    }
}
