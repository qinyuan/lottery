package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.lottery.mvc.dao.LotteryActivity;
import com.qinyuan15.lottery.mvc.dao.LotteryActivityDao;
import com.qinyuan15.lottery.mvc.dao.User;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import com.qinyuan15.utils.IntegerUtils;
import com.qinyuan15.utils.mvc.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller about lottery
 * Created by qinyuan on 15-7-10.
 */
@Controller
public class LotteryController extends BaseController {
    private final static Logger LOGGER = LoggerFactory.getLogger(LotteryController.class);
    private final static String SUCCESS = "success";
    private final static String DETAIL = "detail";

    @RequestMapping("/take-lottery.json")
    @ResponseBody
    public String takeLottery(@RequestParam(value = "commodityId", required = true) Integer commodityId) {
        if (!IntegerUtils.isPositive(commodityId)) {
            return failByInvalidParam();
        }

        LotteryActivity activity = new LotteryActivityDao().getActiveInstanceByCommodityId(commodityId);
        if (activity == null) {
            return fail("noLottery");
        }

        if (securitySearcher.getUsername() == null) {
            return fail("noLogin");
        }

        if (!securitySearcher.hasAuthority(User.NORMAL)) {
            return fail("noPrivilege");
        }

        Map<String, Object> result = new HashMap<>();
        User user = new UserDao().getInstance(securitySearcher.getUserId());
        result.put("username", user.getUsername());

        if (!StringUtils.hasText(user.getTel())) {
            result.put(SUCCESS, false);
            result.put(DETAIL, "noTel");
            return toJson(result);
        }

        result.put(SUCCESS, true);
        return toJson(result);
    }

    @RequestMapping("/update-tel.json")
    @ResponseBody
    public String updateTel(@RequestParam(value = "tel", required = true) String tel,
                            @RequestParam(value = "identityCode", required = true) String identityCode) {
        if (!validateIdentityCode(identityCode)) {
            return fail("验证码输入错误！");
        }

        try {
            new UserDao().updateTel(securitySearcher.getUserId(), tel);
            return success();
        } catch (Exception e) {
            LOGGER.error("Fail to update tel");
            return failByDatabaseError();
        }
    }
}
