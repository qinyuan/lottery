<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="page-width outer-login">
    <div class="login shadow">
        <div class="title">
            <span>用户登录</span>
        </div>
        <q:spring-login-form>
            <div class="error-info">用户名或密码错误</div>
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
                <input type="password" id="password" name="j_password" class="form-control" placeholder="在此输入密码"/>
            </div>
            <div class="submit">
                <q:spring-remember-login/>
                <span id="rememberMeLabel">记住我</span>
                <button type="submit" class="btn btn-primary" id="loginSubmit">登录</button>
            </div>
            <div style="position:absolute;bottom:23px;right:92px;">
                <a target="_blank" style="font-size:10pt;" href="find-password.html">忘记密码?</a>
            </div>
        </q:spring-login-form>
    </div>
</div>
<%@include file="inc-footer.jsp" %>
