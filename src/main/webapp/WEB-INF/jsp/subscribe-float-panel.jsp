<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-taglib.jsp" %>
<div class="float-panel shadow info" id="subscribe">
    <div class="title">
        <div class="text">邮件订阅</div>
        <div class="close-icon"></div>
    </div>
    <q:qqlist nId="${qqlistId}">
        <div class="body">${qqlistDescription}</div>
        <div class="button">
            <button type="submit">马上订阅</button>
        </div>
    </q:qqlist>
</div>
