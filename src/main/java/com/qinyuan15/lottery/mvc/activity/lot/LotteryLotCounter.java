package com.qinyuan15.lottery.mvc.activity.lot;

import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan15.lottery.mvc.config.AppConfig;
import com.qinyuan15.lottery.mvc.activity.ExpectParticipantsDivider;
import com.qinyuan15.lottery.mvc.activity.VirtualParticipantCreator;
import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.LotteryActivityDao;
import com.qinyuan15.lottery.mvc.dao.LotteryLivenessDao;
import com.qinyuan15.lottery.mvc.dao.LotteryLotDao;

/**
 * Class to count lottery lot of certain lottery activity
 */
public class LotteryLotCounter implements LotCounter {
    /**
     * count the total lot number of certain lottery activity,
     * including virtual lot and real lot
     *
     * @param activity lottery activity to count
     * @return total lot number of the activity
     */
    public int count(LotteryActivity activity) {
        int count = countVirtual(activity) + countReal(activity.getId());

        if (activity.getExpire()) {
            return count;
        }

        ExpectParticipantsDivider participantsDivider = new ExpectParticipantsDivider(
                activity.getStartTime(), activity.getCloseTime(), activity.getExpectParticipantCount());
        int currentExpectParticipantCount = participantsDivider.getCurrentExpectValue();
        if (currentExpectParticipantCount > count && currentExpectParticipantCount <= activity.getExpectParticipantCount()
                && !new LotteryActivityDao().isExpire(activity.getId())) {
            new VirtualParticipantCreator().create(activity.getId(),
                    currentExpectParticipantCount - count);
            return currentExpectParticipantCount;
        } else {
            return count;
        }
    }

    /**
     * Calculate how many lots has one person taken
     *
     * @param userId id of user
     * @return how many lots that use has taken
     */
    public int countByUser(Integer userId) {
        return LotteryLotDao.factory().setUserId(userId).getCount();
    }

    private int countVirtual(LotteryActivity activity) {
        Integer count = activity.getVirtualParticipants();
        if (!IntegerUtils.isPositive(count)) {
            count = 0;
        }
        count += LotteryLotDao.factory().setVirtual(true).setActivityId(activity.getId()).getCount();
        return count;
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
    public int countReal(int activityId, int userId) {
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
        LotteryActivity activity = new LotteryActivityDao().getInstance(activityId);
        if (activity == null) {
            return 0;
        }

        if (AppConfig.props.allocateLotterySerialInAdvance()) {
            return 1;
        } else {
            Integer minLivenessToParticipate = activity.getMinLivenessToParticipate();
            if (!IntegerUtils.isPositive(minLivenessToParticipate)) {
                return 1;
            }

            int livenesss = new LotteryLivenessDao().getLiveness(userId);
            return livenesss >= minLivenessToParticipate ? 1 : 0;
        }
    }
}
