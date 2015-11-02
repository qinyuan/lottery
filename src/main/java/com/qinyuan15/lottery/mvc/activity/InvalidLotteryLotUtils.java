package com.qinyuan15.lottery.mvc.activity;

import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.User;
import org.apache.commons.lang3.StringUtils;

public class InvalidLotteryLotUtils {
    private InvalidLotteryLotUtils(){
    }

    public static boolean isNoTelInvalidLot(User user, LotteryActivity activity) {
        if (StringUtils.isNotBlank(user.getTel())) {
            return false;
        }

        int noTelLotteryLotCount = AppConfig.getNoTelLotteryLotCountValue();
        double noTelLotteryLotPrice = AppConfig.getNoTelLotteryLotPriceValue();

        return new LotteryLotCounter().countByUser(user.getId()) > noTelLotteryLotCount ||
                activity.getCommodity().getPrice() > noTelLotteryLotPrice;
    }
}
