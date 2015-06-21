package com.qinyuan15.lottery.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController extends IndexHeaderController {

    @RequestMapping("/login")
    public String index() {
        setHeaderParameters();

        setTitle("后台管理员登录");
        addCssAndJs("login");

        if (getLocalAddress().equals("127.0.0.1")) {
            addJs("auto-login");
        }

        addCss("resources/js/lib/bootstrap/css/bootstrap.min.css", false);
        return "login";
    }
}
