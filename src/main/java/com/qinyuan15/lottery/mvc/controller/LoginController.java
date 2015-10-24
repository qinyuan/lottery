package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan15.lottery.mvc.dao.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController extends ImageController {
    private final static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("/login")
    public String index() {
        IndexHeaderUtils.setHeaderParameters(this);

        setAttribute("noFooter", true);
        setTitle("用户登录");
        addCssAndJs("login");

        if (getLocalAddress().equals("127.0.0.1")) {
            addJs("auto-login");
        }

        return "login";
    }

    /**
     * validate is user is login by certain email
     *
     * @param email email to login
     * @return true if user is login by given email, else false
     */
    @RequestMapping("/isLogin.json")
    @ResponseBody
    public String isLogin(@RequestParam(value = "email", required = true) String email) {
        final String RESULT = "result";
        Map<String, Boolean> map = new HashMap<>();
        if (StringUtils.isBlank(email)) {
            map.put(RESULT, false);
            return toJson(map);
        }

        try {
            User user = (User) securitySearcher.getUser();
            map.put(RESULT, user != null && email.equals(user.getEmail()));
            return toJson(map);
        } catch (Exception e) {
            LOGGER.error("fail to validate login, email: {}, info: {}", email, e);
            map.put(RESULT, false);
            return toJson(map);
        }
    }
}
