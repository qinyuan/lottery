<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div id="email">
    <div class="text">
        <span class="title">当前邮箱</span>
        <span class="old-email">${user.email}</span>
    </div>
    <div class="text">
        <span class="title">新邮箱</span>
        <input type="text" class="form-control" name="email"/>
        <span class="error"></span>
    </div>
    <div class="text">
        <span class="title">当前密码</span>
        <input type="password" class="form-control" name="password"/>
        <span class="error"></span>
    </div>
    <div class="edit-submit" style="margin-left:100px;">
        <button class="ok">发送验证邮件</button>
        <span class="result">
            验证邮件已发送至<a class="email" href="javascript:void(0)" target="_blank"></a>，
            <a href="javascript:void(0)" target="_blank">立即登录</a>
        </span>
    </div>
</div>
