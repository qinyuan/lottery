<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-taglib.jsp" %>
<div class="count">
    <span>总注册用户数：<span class="number">${userCount}</span></span>
    <span>已激活用户数：<span class="number">${activeUserCount}</span></span>
    <span>直接注册用户数：<span class="number">${directlyRegisterUserCount}</span></span>
    <span>邀请注册用户数：<span class="number">${invitedRegisterUserCount}</span></span>
</div>
