package com.qinyuan15.lottery.mvc.lottery;

import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.LotteryLivenessDao;
import org.apache.commons.lang3.tuple.Pair;

public class LivenessQuerier {
    /**
     * Query max liveness of certain activity, including real liveness and virtual liveness
     * @param activity activity to query
     * @return max liveness of activity
     */
    public LivenessInfo queryMax(LotteryActivity activity) {
        Integer virtualLiveness = activity.getVirtualLiveness();
        if (virtualLiveness == null) {
            virtualLiveness = 0;
        }

        Pair<String, Integer> realMaxLivenessPair = new LotteryLivenessDao().getMaxLivenessUsernames(activity.getId());
        Integer realMaxLiveness = realMaxLivenessPair == null ? 0 : realMaxLivenessPair.getRight();
        if (realMaxLiveness == null || realMaxLiveness < 0) {
            realMaxLiveness = 0;
        }
        String realUsernames = realMaxLivenessPair == null ? "" : realMaxLivenessPair.getLeft();

        if (virtualLiveness == 0 && realMaxLiveness == 0) {
            return new LivenessInfo(0, "");
        }

        if (realMaxLiveness < virtualLiveness) {
            return new LivenessInfo(virtualLiveness, activity.getVirtualLivenessUsers());
        } else if (realMaxLiveness.equals(virtualLiveness)) {
            if (realUsernames.isEmpty()) {
                return new LivenessInfo(virtualLiveness, activity.getVirtualLivenessUsers());
            } else {
                return new LivenessInfo(virtualLiveness, activity.getVirtualLivenessUsers() + "," + realUsernames);
            }
        } else {
            return new LivenessInfo(realMaxLiveness, realUsernames);
        }
    }

    public static class LivenessInfo {
        public final int liveness;
        public final String users;

        private LivenessInfo(int liveness, String users) {
            this.liveness = liveness;
            this.users = users;
        }
    }
}
