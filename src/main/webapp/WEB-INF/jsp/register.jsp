<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%--<%@include file="index-header.jsp" %>--%>
<div class="header">
    <div class="logo page-width">
        <div class="left">
            <c:if test="${registerHeaderLeftLogo != null}"><img src="${registerHeaderLeftLogo}"/></c:if>
        </div>
        <div class="right">
            <c:if test="${registerHeaderRightLogo != null}"><img src="${registerHeaderRightLogo}"/></c:if>
        </div>
    </div>
    <div class="split"></div>
</div>
<div class="white-back">
    <div class="main-body page-width shadow">
        <c:choose>
            <c:when test="${preUser != null}">
                <c:choose>
                    <c:when test="${userInfoCompleted}">
                        <div class="completed">邮箱 <span class="email">${preUser.email}</span> 已经成功注册</div>
                        <div class="completed-link">
                            <span style="margin-right:20px;">您现在可以： </span>
                            <a href="javascript:void(0)" class="to-login">登录</a>
                            或
                            <a href="find-password.html" target="_blank">找回密码</a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <%@include file="register-form.jsp" %>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <div class="invalid-link">本链接已经失效！！！</div>
                <div class="re-register">您现在可以：<a style="margin-left:10px;" href="javascript:void(0)">重新注册</a></div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<%@include file="inc-footer.jsp" %>
