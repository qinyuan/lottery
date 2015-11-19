<%@ page import="com.qinyuan15.lottery.mvc.AppConfig" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="form">
    <div class="title">创建帐号</div>
    <c:choose><c:when test="${byQQ}">
        <q:hidden name="qqOpenId"/>
        <div class="prompt">请输入邮箱、昵称和密码，创建您的帐号</div>
        <div class="input">
            <c:choose>
                <c:when test="${numberMode}">
                    <input type="text" class="form-control" placeholder="QQ号码" name="qqNumber"/>
                </c:when>
                <c:otherwise>
                    <input type="text" class="form-control" placeholder="邮箱" name="email"/>
                </c:otherwise>
            </c:choose>
        </div>
    </c:when><c:otherwise>
        <div class="email">邮箱验证成功！您的邮箱是：<span class="email">${preUser.email}</span></div>
        <div class="prompt">请输入昵称和密码，创建您的帐号</div>
    </c:otherwise></c:choose>
    <div class="input">
        <input type="text" class="form-control" placeholder="昵称" name="username" maxlength="14"/>
    </div>
    <c:if test="${!byQQ}">
        <div class="input">
            <input type="password" class="form-control" placeholder="密码" name="password" maxlength="20"/>
        </div>
        <div class="input">
            <input type="password" class="form-control" placeholder="确认密码" name="password2" maxlength="20"/>
        </div>
    </c:if>
    <div class="subscribe">
        <input type="checkbox" name="subscribe"/>
        已经认真阅读并同意订阅 <a target="_blank" href="${websiteIntroductionLink}">《布迪网》</a>
    </div>
    <div class="submit">
        <q:qqlist nId="<%=AppConfig.getQQListId()%>" email="${preUser.email}" newPage="${!isMobileUserAgent}">
            <button type="submit" class="ok btn btn-success" disabled>创建帐号</button>
        </q:qqlist>
    </div>
</div>
<div class="register-success">
    <div class="title">邮箱 <span class="email">${preUser.email}</span> 已经注册成功！</div>
    <div class="skip">邮件订阅已发送，请到邮箱内确认</div>
    <div class="to-index"><a href="index.html">进入主页</a></div>
</div>
