package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.config.ImageConfig;
import com.qinyuan.lib.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class ImagesController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ImagesController.class);

    @Autowired
    private ImageConfig imageConfig;

    @RequestMapping("/lottery/*")
    public void index(HttpServletResponse response, HttpServletRequest request) {
        String relativePath = StringUtils.replaceFirst(request.getServletPath(), "/lottery/", "");
        String absolutePath = imageConfig.getDirectory() + "/" + relativePath;
        printImage(response, absolutePath);
    }

    private void printImage(HttpServletResponse response, String imagePath) {
        try {
            File file = new File(imagePath);
            if (!file.isFile()) {
                return;
            }

            response.setContentType("image/jpeg;charset=UTF8");

            InputStream is = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            OutputStream output = response.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(output);

            byte data[] = new byte[4096];
            int size;
            while ((size = bis.read(data)) != -1) {
                bos.write(data, 0, size);
            }

            bis.close();
            is.close();
            bos.flush();
            bos.close();
            output.close();
        } catch (Exception e) {
            LOGGER.error("fail to print image,image path: {}, info: {}", imagePath, e);
        }
    }
}
