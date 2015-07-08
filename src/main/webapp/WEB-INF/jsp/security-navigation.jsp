<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-taglib.jsp" %>
<ul>
    <security:authorize ifAnyGranted="ROLE_NORMAL">
        <li><a class="text" href="index"><security:authentication property="name"/></a></li>
        <li><a class="text" href="j_spring_security_logout">退出</a></li>
        <li><a class="text" href="index">个人中心</a></li>
        <li><a class="text" href="index">系统消息</a></li>
        <li><a class="text" href="index">抽奖历史</a></li>
    </security:authorize>
    <security:authorize ifAnyGranted="ROLE_ADMIN">
        <li><a class="text" href="admin.html"><security:authentication property="name"/></a></li>
        <li><a class="text" href="j_spring_security_logout">退出</a></li>
        <li><a id="systemEditLink" class="text" href="admin.html">系统设置</a>
            <ul>
                <li><a iclass="text" href="admin.html">系统</a></li>
                <li><a class="text" href="admin-index-edit.html">主页</a></li>
                <li><a class="text" href="admin-help.html">帮助中心</a></li>
                <li></li>
            </ul>
        </li>
        <li><a id="commodityEditLink" class="text" href="admin-commodity-edit.html">编辑商品</a></li>
    </security:authorize>
    <security:authorize ifNotGranted="ROLE_NORMAL,ROLE_ADMIN">
        <li><a class="text emphasize" id="loginNavigationLink" href="javascript:void(0)">登录</a></li>
        <li><a class="text" id="registerNavigationLink" href="javascript:void(0)">注册</a></li>
    </security:authorize>
    <li><a class="text" id="helpNavigationLink" href="help.html">帮助中心</a></li>
</ul>
