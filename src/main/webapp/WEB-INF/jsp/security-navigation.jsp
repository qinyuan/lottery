<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-taglib.jsp" %>
<security:authorize ifAnyGranted="ROLE_NORMAL">
    <a class="text" href="index"><security:authentication property="name"/></a>
    <a class="text" href="j_spring_security_logout">退出</a>
    <a class="text" href="index">个人中心</a>
    <a class="text" href="index">系统消息</a>
    <a class="text" href="index">抽奖历史</a>
</security:authorize>
<security:authorize ifAnyGranted="ROLE_ADMIN">
    <a class="text" href="admin.html"><security:authentication property="name"/></a>
    <a class="text" href="j_spring_security_logout">退出</a>
    <a id="commodityEditLink" class="text" href="admin-commodity-edit.html">编辑商品</a>
    <a id="indexEditLink" class="text" href="admin-index-edit.html">主页设置</a>
    <a id="systemEditLink" class="text" href="admin.html">系统设置</a>
    <a class="text" href="admin-help.html">编辑帮助中心</a>
</security:authorize>
<security:authorize ifNotGranted="ROLE_NORMAL,ROLE_ADMIN">
    <a class="text emphasize" id="loginNavigationLink" href="javascript:void(0)">登录</a>
    <a class="text" id="registerNavigationLink" href="javascript:void(0)">注册</a>
</security:authorize>
<a class="text" id="helpNavigationLink" href="help.html">帮助中心</a>
