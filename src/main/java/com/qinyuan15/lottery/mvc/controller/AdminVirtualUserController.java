package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan15.lottery.mvc.account.NewUserValidator;
import com.qinyuan15.lottery.mvc.dao.VirtualUserDao;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminVirtualUserController extends ImageController {

    @RequestMapping("/admin-virtual-user")
    public String index() {
        IndexHeaderUtils.setHeaderParameters(this);

        setAttribute("users", new VirtualUserDao().getInstances());
        setTitle("虚拟用户");

        addCss("resources/js/lib/font-awesome/css/font-awesome.min", false);
        addCss("resources/js/lib/buttons/buttons.min", false);
        addCss("admin-form");
        addCssAndJs("admin-virtual-user");
        return "admin-virtual-user";
    }

    @RequestMapping("/admin-virtual-user-add-update.json")
    @ResponseBody
    public String addOrUpdate(@RequestParam(value = "id", required = false) Integer id,
                              @RequestParam(value = "username", required = true) String username,
                              @RequestParam(value = "telPrefix", required = true) String telPrefix,
                              @RequestParam(value = "telSuffix", required = true) String telSuffix,
                              @RequestParam(value = "mailPrefix", required = true) String mailPrefix,
                              @RequestParam(value = "mailSuffix", required = true) String mailSuffix) {
        if (!StringUtils.hasText(username)) {
            return fail("用户名不能为空！");
        }

        if (!StringUtils.hasText(telPrefix)) {
            return fail("手机号前两位不能为空！");
        }

        if (!telPrefix.matches("\\d{2}")) {
            return fail("手机号前两位必须是两位数字！");
        }

        if (!StringUtils.hasText(telSuffix)) {
            return fail("手机号后四位不能为空！");
        }

        if (!telSuffix.matches("\\d{4}")) {
            return fail("手机号后四位必须为四位数字！");
        }

        if (!StringUtils.hasText(mailPrefix)) {
            return fail("邮箱前两个字符不能为空！");
        }

        if (!mailPrefix.matches("\\w{2}")) {
            return fail("邮箱前两位字符格式错误！");
        }

        if (!StringUtils.hasText(mailSuffix)) {
            return fail("邮箱后缀名不能为空！");
        }

        if (!mailSuffix.matches("^@\\w+\\.\\w+.*$")) {
            return fail("邮箱后缀的正确格式为'@**.**'");
        }

        try {
            if (!(IntegerUtils.isPositive(id) && new VirtualUserDao().getInstance(id).getUsername().equals(username))) {
                Pair<Boolean, String> validation = new NewUserValidator().validateUsername(username);
                if (!validation.getLeft()) {
                    return fail(validation.getRight());
                }
            }
            if (IntegerUtils.isPositive(id)) {
                new VirtualUserDao().update(id, username, telPrefix, telSuffix, mailPrefix, mailSuffix);
            } else {
                new VirtualUserDao().add(username, telPrefix, telSuffix, mailPrefix, mailSuffix);
            }
            return success();
        } catch (Exception e) {
            return failByDatabaseError();
        }
    }

    @RequestMapping("/admin-virtual-user-delete.json")
    @ResponseBody
    public String delete(@RequestParam(value = "id", required = true) Integer id) {
        try {
            new VirtualUserDao().delete(id);
            return success();
        } catch (Exception e) {
            return failByDatabaseError();
        }
    }
}
