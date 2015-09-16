package com.qinyuan15.lottery.mvc.controller;

import com.google.common.base.Joiner;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan15.lottery.mvc.account.NewUserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminBatchRegisterController extends ImageController {

    @RequestMapping("/admin-batch-register")
    public String index() {
        IndexHeaderUtils.setHeaderParameters(this);

        setTitle("批量注册新用户");
        addCss("admin-form");
        addCssAndJs("admin-batch-register");
        return "admin-batch-register";
    }

    @RequestMapping("/admin-batch-register-validate.json")
    @ResponseBody
    public String json(@RequestParam(value = "usernames[]", required = true) String[] usernames) {
        if (usernames == null || usernames.length == 0) {
            return failByInvalidParam();
        }

        List<String> registeredUsernames = new ArrayList<>();
        for (String username : usernames) {
            if (!new NewUserValidator().validateUsername(username).getLeft()) {
                registeredUsernames.add(username);
            }
        }

        if (registeredUsernames.size() > 0) {
            return fail(Joiner.on(", ").join(registeredUsernames));
        } else {
            return success();
        }
    }
}
