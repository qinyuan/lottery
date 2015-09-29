package com.qinyuan15.lottery.mvc.controller;

import com.qinyuan.lib.config.ImageConfig;
import com.qinyuan.lib.lang.IntegerUtils;
import com.qinyuan.lib.mvc.controller.ImageUrlAdapter;
import com.qinyuan.lib.mvc.security.SecuritySearcher;
import com.qinyuan.lib.mvc.security.SecurityUtils;
import com.qinyuan.lib.mvc.security.UserRole;
import com.qinyuan15.lottery.mvc.AppConfig;
import com.qinyuan15.lottery.mvc.dao.LotteryActivityDao;
import com.qinyuan15.lottery.mvc.dao.SeckillActivityDao;
import com.qinyuan15.lottery.mvc.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Global Interceptor
 * Created by qinyuan on 15-6-21.
 */
public class CommonInterceptor implements HandlerInterceptor {
    @Autowired
    private ImageConfig imageConfig;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    private boolean isRequestToResources(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.contains("/resources/css/") || uri.contains("/resources/js/");
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if (isRequestToResources(httpServletRequest)) {
            return;
        }

        ImageUrlAdapter imageUrlAdapter = new ImageUrlAdapter(imageConfig, httpServletRequest.getServerName());

        httpServletRequest.setAttribute("footerPoster", imageUrlAdapter.pathToUrl(AppConfig.getFooterPoster()));
        httpServletRequest.setAttribute("footerText", AppConfig.getFooterText());
        httpServletRequest.setAttribute("favicon", imageUrlAdapter.pathToUrl(AppConfig.getFavicon()));

        UserDao userDao = new UserDao();
        SecuritySearcher searcher = new SecuritySearcher(userDao);
        if (SecurityUtils.hasAuthority(UserRole.NORMAL)) {
            /*User user = userDao.getInstance(searcher.getUserId());
            if (!user.getActive()) {
                httpServletRequest.setAttribute("unactivatedEmail", user.getEmail());
            }*/
            Integer userId = searcher.getUserId();
            httpServletRequest.setAttribute("activityCount", countActivity(userId));
        }
    }

    private int countActivity(Integer userId) {
        if (!IntegerUtils.isPositive(userId)) {
            return 0;
        }

        int lotteryActivityCount = LotteryActivityDao.factory().setUserId(userId).getCount();
        int seckillActivityCount = SeckillActivityDao.factory().setUserId(userId).getCount();
        return lotteryActivityCount + seckillActivityCount;
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
