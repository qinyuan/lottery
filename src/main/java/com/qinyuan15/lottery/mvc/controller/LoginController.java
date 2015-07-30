package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan15.utils.mvc.controller.ImageController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController extends ImageController {

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
}
