package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.mvc.security.AjaxAuthenticationSuccessHandler;
import com.qinyuan15.lottery.mvc.LoginRecordAdder;
import org.springframework.security.core.Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxLoginSuccessHandler extends AjaxAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        super.onAuthenticationSuccess(request, response, authentication);
        new LivenessAdder(request.getSession()).addLiveness(true);
        new LoginRecordAdder().add(request);
        //new LoginRecordAdder().add(request.getRemoteAddr(), new UserAgent(request).getOS().toString());
    }
}
