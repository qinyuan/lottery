<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="page-width outer-login">
    <div class="login shadow">
        <div class="title">
            <span>后台管理员登录</span>
        </div>
        <form action="j_spring_security_check" method="post">
            <div class="title">
                <label>用户名</label>
            </div>
            <div class="input">
                <input type="text" id="username" name="j_username" class="form-control" placeholder="在此输入用户名"/>
            </div>

            <div class="title">
                <label>密码</label>
            </div>
            <div class="input">
                <input type="text" id="password" name="j_password" class="form-control" placeholder="在此输入密码"/>
            </div>
            <div class="submit">
                <input type="checkbox" id="rememberMe" name="_spring_security_remember_me"/>
                <span id="rememberMeLabel">记住我</span>
                <button type="submit" class="btn btn-primary" id="loginSubmit">登录</button>
            </div>
        </form>
    </div>
</div>
<%@include file="inc-footer.jsp" %>
