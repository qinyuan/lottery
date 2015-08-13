<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-taglib.jsp" %>
<div class="count">
    <span>总注册用户数：<span class="number">${userCount}</span></span>
    <span>已激活用户数：<span class="number">${activeUserCount}</span></span>
    <span>直接注册用户数：<span class="number">${directlyRegisterUserCount}</span></span>
    <span>邀请注册用户数：<span class="number">${invitedRegisterUserCount}</span></span>
</div>
<div class="user-filter">
    <div class="title">用户筛选：</div>
    <div class="content">
        <div class="activity">
            <button class="button button-primary button-circle" title="添加活动"><i class="fa fa-plus"></i></button>
        </div>
        <div class="liveness">
            爱心值 ≥ <input type="text" class="form-control min-liveness" value="${minLiveness}"/>
            <button id="livenessFilterSubmit" class="btn btn-success">确定</button>
        </div>
    </div>
</div>
