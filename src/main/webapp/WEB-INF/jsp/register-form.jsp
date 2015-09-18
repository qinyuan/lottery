<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form id="userInfo" method="post">
    <div class="left">完善个人信息</div>
    <div class="right">
        <div class="content setting">
            <q:hidden name="serialKey" value="${preUser.serialKey}"/>

            <div class="row">
                <span class="left">邮箱</span>
                <span class="right">${preUser.email}</span>
            </div>
            <div class="row">
                <span class="left">账号<span class="required">*</span></span>
                        <span class="right"><input type="text" class="form-control" name="username"
                                                   placeholder="2-14个字符: 英文、数字或中文" maxlength="14"/></span>
            </div>
            <div class="row">
                <span class="left">密码<span class="required">*</span></span>
                        <span class="right"><input type="password" class="form-control" name="password"
                                                   placeholder="6-20个字符，区分大小写" maxlength="20"/></span>
            </div>
            <div class="row">
                <span class="left">确认密码<span class="required">*</span></span>
                        <span class="right"><input type="password" class="form-control" name="password2"
                                                   placeholder="再次输入密码" maxlength="20"/></span>
            </div>
            <div class="row">
                <span class="left">姓名</span>
                        <span class="right"><input type="text" class="form-control" name="realName"
                                                   placeholder="输入您的真实姓名"/></span>
            </div>
            <div class="row">
                <span class="left">手机号</span>
                        <span class="right"><input type="text" class="form-control" name="tel"
                                                   placeholder="输入11位数字" maxlength="11"/></span>
            </div>
            <%@include file="personal-center-additional-info.jsp" %>
            <div class="submit">
                <button id="submitButton" type="submit">完成注册</button>
                <span class="comment">(注：带<span class="required">*</span>的为必填项)</span>
            </div>
        </div>
    </div>
</form>
