package com.qinyuan15.lottery.mvc.controller;

import com.google.common.base.Joiner;
import com.qinyuan.lib.contact.mail.MailAddressValidator;
import com.qinyuan.lib.mvc.controller.ImageController;
import com.qinyuan15.lottery.mvc.account.PreUserSerialKeyBuilder;
import com.qinyuan15.lottery.mvc.dao.PreUser;
import com.qinyuan15.lottery.mvc.dao.PreUserDao;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import com.qinyuan15.lottery.mvc.mail.RegisterMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /*@RequestMapping("/admin-batch-register-validate.json")
    @ResponseBody
    public String validate(@RequestParam(value = "emails[]", required = true) String[] emails) {
        if (emails == null || emails.length == 0) {
            return failByInvalidParam();
        }

        List<String> invalidEmails = new ArrayList<>();
        List<String> registeredEmails = new ArrayList<>();
        MailAddressValidator validator = new MailAddressValidator();
        UserDao userDao = new UserDao();
        for (String email : emails) {
            if (!validator.validate(email)) {
                invalidEmails.add(email);
            } else if (userDao.hasEmail(email)) {
                registeredEmails.add(email);
            }
        }

        if (invalidEmails.size() == 0 && registeredEmails.size() == 0) {
            return success();
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("success", false);
            if (invalidEmails.size() > 0) {
                map.put("invalidEmails", Joiner.on(", ").join(invalidEmails));
            }
            if (registeredEmails.size() > 0) {
                map.put("registeredEmails", Joiner.on(", ").join(registeredEmails));
            }
            return toJson(map);
        }
    }*/

    @RequestMapping("/admin-batch-register-submit.json")
    @ResponseBody
    public String submit(@RequestParam(value = "emails[]", required = true) String[] emails) {
        if (emails == null || emails.length == 0) {
            return failByInvalidParam();
        }

        Validator validator = new Validator(emails);
        if (validator.isValid()) {
            PreUserDao dao = new PreUserDao();
            for (String email : validator.validEmails) {
                PreUser preUser = dao.getInstanceByEmail(email);
                String serialKey;
                if (preUser == null) {
                    serialKey = new PreUserSerialKeyBuilder().build();
                    dao.add(email, serialKey);
                } else {
                    serialKey = preUser.getSerialKey();
                }
                new RegisterMailSender().send(email, serialKey);
            }
            return success();
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("success", false);
            map.put("detail", "invalid");
            if (validator.invalidEmails.size() > 0) {
                map.put("invalidEmails", Joiner.on(", ").join(validator.invalidEmails));
            }
            if (validator.registeredEmails.size() > 0) {
                map.put("registeredEmails", Joiner.on(", ").join(validator.registeredEmails));
            }
            return toJson(map);
        }
    }

    private static class Validator {
        final List<String> invalidEmails = new ArrayList<>();
        final List<String> registeredEmails = new ArrayList<>();
        final List<String> validEmails = new ArrayList<>();

        private Validator(String[] emails) {
            UserDao userDao = new UserDao();
            MailAddressValidator validator = new MailAddressValidator();
            for (String email : emails) {
                if (!validator.validate(email)) {
                    invalidEmails.add(email);
                } else if (userDao.hasEmail(email)) {
                    registeredEmails.add(email);
                } else {
                    validEmails.add(email);
                }
            }
        }

        boolean isValid() {
            return invalidEmails.size() == 0 && registeredEmails.size() == 0;
        }
    }
}
