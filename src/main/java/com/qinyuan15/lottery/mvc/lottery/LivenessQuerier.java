package com.qinyuan15.lottery.mvc.lottery;

import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.User;
import com.qinyuan15.utils.hibernate.HibernateListBuilder;

import java.util.List;

public class LivenessQuerier {
    public LivenessInfo queryMax(LotteryActivity activity) {
        // liveness parameter
        /*Integer liveness = user.getLiveness();
        if (liveness == null) {
            liveness = 0;
        }
        result.put("liveness", liveness);*/

        // maxLiveness parameter
        Integer virtualLiveness = activity.getVirtualLiveness();
        if (virtualLiveness == null) {
            virtualLiveness = 0;
        }

        List<User> maxLivenessUsers = new HibernateListBuilder()
                .addFilter("liveness=(SELECT MAX(liveness) FROM User)").build(User.class);
        Integer realMaxLiveness = maxLivenessUsers.size() == 0 ? null : maxLivenessUsers.get(0).getLiveness();
        if (realMaxLiveness == null || realMaxLiveness < 0) {
            realMaxLiveness = 0;
        }
        String realUsernames = getUsernames(maxLivenessUsers);

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

    private String getUsernames(List<User> users) {
        String names = "";
        for (User user : users) {
            if (!names.isEmpty()) {
                names += ",";
            }
            names += user.getUsername();
        }
        return names;
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
