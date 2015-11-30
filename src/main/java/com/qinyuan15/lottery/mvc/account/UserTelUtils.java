package com.qinyuan15.lottery.mvc.account;

import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.dao.LotteryLivenessDao;
import com.qinyuan15.lottery.mvc.dao.User;
import org.apache.commons.lang3.StringUtils;

public class UserTelUtils {
    private UserTelUtils() {
    }

    public static boolean hasHighLivenessButNoTel(User user) {
        if (StringUtils.isNotBlank(user.getTel())) {
            return false;
        }

        Integer noTelLiveness = AppConfig.getNoTelLiveness();
        if (!IntegerUtils.isPositive(noTelLiveness)) {
            return false;
        }

        int liveness = new LotteryLivenessDao().getLiveness(user.getId());
        return liveness > noTelLiveness;
    }
}
