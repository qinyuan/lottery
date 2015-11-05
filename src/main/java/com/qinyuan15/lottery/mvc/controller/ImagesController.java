package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.config.ImageConfig;
import com.qinyuan.lib.lang.StringUtils;
import com.qinyuan.lib.mvc.controller.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ImagesController {
    @Autowired
    private ImageConfig imageConfig;

    @RequestMapping("/lottery/**")
    public void index(HttpServletResponse response, HttpServletRequest request) {
        String relativePath = StringUtils.replaceFirst(request.getServletPath(), "/lottery/", "");
        String absolutePath = imageConfig.getDirectory() + "/" + relativePath;
        System.out.println(absolutePath);
        ResponseUtils.outputImage(response, absolutePath);
    }
}
