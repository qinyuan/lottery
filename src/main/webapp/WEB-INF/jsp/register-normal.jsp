<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<c:choose><c:when test="${preUser != null}">
    <c:choose><c:when test="${userInfoCompleted}">
        <div class="completed">邮箱 <span class="email">${preUser.email}</span> 已经成功注册</div>
        <div class="completed-link">
            <span style="margin-right:20px;">您现在可以： </span>
            <a href="javascript:void(0)" class="to-login">登录</a>
            或
            <a href="find-password.html" target="_blank">找回密码</a>
        </div>
    </c:when><c:otherwise>
        <%@include file="register-form.jsp" %>
    </c:otherwise></c:choose>
</c:when><c:otherwise>
    <div class="invalid-link">本链接已经失效！！！</div>
    <div class="re-register">
        您现在可以：<a style="margin-left:10px;" href="javascript:void(0)">重新注册</a>
    </div>
</c:otherwise></c:choose>
