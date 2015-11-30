package com.qinyuan15.lottery.mvc.account;

import com.qinyuan.lib.database.test.DatabaseTestCase;
import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.dao.LotteryLivenessDao;
import com.qinyuan15.lottery.mvc.dao.User;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTelUtilsTest extends DatabaseTestCase {
    @Test
    public void testHasHighLivenessButNoTel() throws Exception {
        User user = new UserDao().getInstance(4);

        assertThat(new LotteryLivenessDao().getLiveness(user.getId())).isEqualTo(13);
        assertThat(AppConfig.getNoTelLiveness()).isNull();
        assertThat(user.getTel()).isNull();
        assertThat(UserTelUtils.hasHighLivenessButNoTel(user)).isFalse();

        AppConfig.updateNoTelLiveness(10);      // user's liveness is greater than 10
        assertThat(UserTelUtils.hasHighLivenessButNoTel(user)).isTrue();

        user.setTel("13000000000");
        assertThat(UserTelUtils.hasHighLivenessButNoTel(user)).isFalse();

        user.setTel(null);
        AppConfig.updateNoTelLiveness(15);      // user's liveness is less than 15
        assertThat(UserTelUtils.hasHighLivenessButNoTel(user)).isFalse();

        user = new UserDao().getInstance(3);
        assertThat(new LotteryLivenessDao().getLiveness(user.getId())).isEqualTo(25);
        assertThat(UserTelUtils.hasHighLivenessButNoTel(user)).isTrue();
    }
}
