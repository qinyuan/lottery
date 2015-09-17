<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="gray-back">
    <div class="main-body page-width shadow">
        <%--<c:choose>
            <c:when test="${email == null}">
                <span class="invalid">此链接已经失效！！！</span>
            </c:when>
            <c:otherwise>
                邮箱<span class="email">${email}</span>验证成功！
            </c:otherwise>
        </c:choose>--%>
        <div class="left">完善个人信息</div>
        <div class="right">
            <div class="content setting">
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
                    <span class="right"><input type="text" class="form-control" name="password"
                                               placeholder="6-20个字符，区分大小写" maxlength="20"/></span>
                </div>
                <div class="row">
                    <span class="left">确认密码<span class="required">*</span></span>
                    <span class="right"><input type="text" class="form-control" name="password"
                                               placeholder="再次输入密码" maxlength="20"/></span>
                </div>
                <div class="row">
                    <span class="left">手机号</span>
                    <span class="right"><input type="text" class="form-control" name="tel"
                                               placeholder="输入11位数字" maxlength="11"/></span>
                </div>
                <div class="submit">
                    <button id="submitButton" type="submit">完成注册</button>
                    <span class="comment">(注：带<span class="required">*</span>的为必填项)</span>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="inc-footer.jsp" %>
