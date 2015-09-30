<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-taglib.jsp" %>
<div class="float-panel shadow info" id="subscribe">
    <div class="title">邮件订阅
        <div class="close-icon"><span></span></div>
    </div>
    <q:qqlist nId="${qqlistId}">
        <div class="body">${qqlistDescription}</div>
        <div class="button">
            <button type="submit">马上订阅</button>
        </div>
    </q:qqlist>
</div>
