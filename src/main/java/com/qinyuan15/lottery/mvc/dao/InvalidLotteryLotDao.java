package com.qinyuan15.lottery.mvc.dao;

import com.qinyuan.lib.database.hibernate.HibernateListBuilder;
import com.qinyuan.lib.lang.IntegerUtils;

import java.util.ArrayList;
import java.util.List;

public class InvalidLotteryLotDao {
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
            return 0;
        }

        return getListBuilder(activity).countBySQL("lottery_lot");
/*
        String noTelTable = "(SELECT id FROM user WHERE tel IS NULL OR tel='')";
        String filter = "activity_id=:activityId AND ((user_id IN " + noTelTable + ")";

        Integer minLivenessToParticipate = activity.getMinLivenessToParticipate();
        if (IntegerUtils.isPositive(minLivenessToParticipate)) {
            String insufficientLivenessTable = "(SELECT spread_user_id,SUM(liveness) AS liveness_sum FROM lottery_liveness " +
                    "GROUP BY spread_user_id HAVING liveness_sum<" + minLivenessToParticipate + ")";
            insufficientLivenessTable = "(SELECT spread_user_id FROM " + insufficientLivenessTable + " AS t)";
            filter += " OR (user_id IN " + insufficientLivenessTable + ")";
        }

        filter += ")";
        return new HibernateListBuilder().addFilter(filter).addArgument("activityId", activityId)
                .countBySQL("lottery_lot");*/
    }

    private HibernateListBuilder getListBuilder(LotteryActivity activity) {
        String noTelTable = "(SELECT id FROM user WHERE tel IS NULL OR tel='')";
        String filter = "activity_id=:activityId AND ((user_id IN " + noTelTable + ")";

        Integer minLivenessToParticipate = activity.getMinLivenessToParticipate();
        if (IntegerUtils.isPositive(minLivenessToParticipate)) {
            String insufficientLivenessTable = "(SELECT spread_user_id,SUM(liveness) AS liveness_sum FROM lottery_liveness " +
                    "GROUP BY spread_user_id HAVING liveness_sum<" + minLivenessToParticipate + ")";
            insufficientLivenessTable = "(SELECT spread_user_id FROM " + insufficientLivenessTable + " AS t)";
            filter += " OR (user_id IN " + insufficientLivenessTable + ")";
        }

        filter += ")";
        return new HibernateListBuilder().addFilter(filter).addArgument("activityId", activity.getId());
    }

    public List<Integer> getSerialNumbers(Integer activityId) {
        LotteryActivity activity = new LotteryActivityDao().getInstance(activityId);
        if (activity == null) {
            return new ArrayList<>();
        }

        List<Integer> serialNumbers = new ArrayList<>();
        List<Object[]> list = getListBuilder(activity).buildBySQL("SELECT id,serial_number FROM lottery_lot");
        for (Object[] objects : list) {
            serialNumbers.add((Integer) objects[1]);
        }
        return serialNumbers;
    }
}
