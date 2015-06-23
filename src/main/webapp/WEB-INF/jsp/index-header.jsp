<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="header">
    <div class="top-back"></div>
    <div class="bottom-back"></div>
    <div class="content page-width">
        <div class="left-logo">
            <a href="index.html"><img src="${indexHeaderLeftLogo}"/></a>
        </div>
        <div class="navigation">
            <c:forEach var="indexHeaderLink" items="${indexHeaderLinks}" varStatus="status">
               <c:if test="${status.index>0}">|</c:if> <a href="${indexHeaderLink.href}">${indexHeaderLink.title}</a>
            </c:forEach>
        </div>
        <div class="right-logo">
            <security:authorize ifAnyGranted="ROLE_USER">
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
            </security:authorize>
            <security:authorize ifNotGranted="ROLE_USER,ROLE_ADMIN">
                <a class="text emphasize" href="javascript:void(0)">登录</a>
                <a class="text" href="javascript:void(0)">注册</a>
            </security:authorize>
            <a class="image" href="index.html"><img src="${indexHeaderRightLogo}"/></a>
        </div>
        <div class="slogan">
            <img src="${indexHeaderSlogan}"/>
        </div>
    </div>
</div>
