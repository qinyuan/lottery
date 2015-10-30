<%@ page import="com.qinyuan15.lottery.mvc.AppConfig" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="form">
    <div class="title">创建帐号</div>
    <div class="email">邮箱验证成功！您的邮箱是：<span class="email">${preUser.email}</span></div>
    <div class="prompt">请输入密码和昵称，创建您的帐号</div>
    <div class="input">
        <input type="text" class="form-control" placeholder="昵称" name="username" maxlength="14"/>
    </div>
    <div class="input">
        <input type="password" class="form-control" placeholder="密码" name="password" maxlength="20"/>
    </div>
    <div class="input">
        <input type="password" class="form-control" placeholder="确认密码" name="password2" maxlength="20"/>
    </div>
    <div class="subscribe">
        <input type="checkbox" name="subscribe"/>
        已经认真阅读并同意订阅 <a target="_blank" href="${websiteIntroductionLink}">《布迪网》</a>
    </div>
    <div class="submit">
        <q:qqlist nId="<%=AppConfig.getQQListId()%>" email="${preUser.email}" newPage="${!isMobile}">
            <button type="submit" class="ok btn btn-success" disabled>创建帐号</button>
        </q:qqlist>
    </div>
</div>
<div class="register-success">
    <div class="title">邮箱 <span class="email">${preUser.email}</span> 已经注册成功！</div>
    <div class="skip">系统将在<span class="remain"></span>秒后跳转到主页，如果没有请 <a href="index.html">点击手动跳转</a></div>
</div>
