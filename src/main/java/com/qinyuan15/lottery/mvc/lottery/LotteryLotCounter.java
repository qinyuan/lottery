package com.qinyuan15.lottery.mvc.lottery;

import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.LotteryActivityDao;
import com.qinyuan15.lottery.mvc.dao.LotteryLivenessDao;
import com.qinyuan15.lottery.mvc.dao.LotteryLotDao;
import com.qinyuan15.utils.IntegerUtils;

/**
 * Class to count lottery lot of certain lottery activity
 */
public class LotteryLotCounter {
    /**
     * count the total lot number of certain lottery activity,
     * including virtual lot and real lot
     *
     * @param activity lottery activity to count
     * @return total lot number of the activity
     */
    public int count(LotteryActivity activity) {
        Integer count = activity.getVirtualParticipants();
        if (count == null) {
            count = countReal(activity.getId());
        } else {
            count += countReal(activity.getId());
        }

        if (activity.getExpire()) {
            return count;
        }

        ExpectParticipantsDivider participantsDivider = new ExpectParticipantsDivider(
                activity.getStartTime(), activity.getExpectEndTime(), activity.getExpectParticipantCount());
        int currentExpectParticipantCount = participantsDivider.getCurrentExpectValue();
        if (currentExpectParticipantCount > count && !new LotteryActivityDao().isExpire(activity.getId())) {
            new VirtualParticipantCreator().create(activity.getId(),
                    currentExpectParticipantCount - count);
            return currentExpectParticipantCount;
        } else {
            return count;
        }
    }

    /**
     * count the real lot number of certain lottery activity
     *
     * @param activityId id of activity to count
     * @return real lot number of the lottery activity
     */
    public int countReal(Integer activityId) {
        return LotteryLotDao.factory().setActivityId(activityId).getCount();
    }

    /**
     * get the available lot number of certain activity and certain user
     *
     * @param activityId id of activity to count
     * @param userId     id of user to count
     * @return available lot number
     */
    public int getAvailableLotCount(int activityId, int userId) {
        int count = getMaxLotCount(activityId, userId) - countReal(activityId, userId);
        return Math.max(0, count);
    }

    /**
     * count the read lot number of certain lottery activity and certain user
     *
     * @param activityId id of activity to count
     * @param userId     id of user to count
     * @return real lot number of the lottery activity and user
     */
    private int countReal(int activityId, int userId) {
        return LotteryLotDao.factory().setActivityId(activityId).setUserId(userId).getCount();
    }

    /**
     * get the max lot number certain user can take in certain activity
     *
     * @param activityId id of activity to count
     * @param userId     id of user to count
     * @return available lot number
     */
    private int getMaxLotCount(int activityId, int userId) {
        Integer newLotLivness = AppConfig.getNewLotLiveness();
        if (!IntegerUtils.isPositive(newLotLivness)) {
            return 0;
        }

        int livenesss = new LotteryLivenessDao().getLiveness(userId);
        return livenesss >= newLotLivness ? 1 : 0;
        /*
        int count = 1;

        Integer newLotLivness = AppConfig.getNewLotLiveness();
        if (!IntegerUtils.isPositive(newLotLivness)) {
            return count;
        }

        int livenesss = new LotteryLivenessDao().getLiveness(userId);
        return count + livenesss / newLotLivness;
        */
    }
}
