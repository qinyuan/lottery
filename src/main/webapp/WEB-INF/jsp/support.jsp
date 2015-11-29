<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="register-header.jsp" %>
<div class="white-back">
    <div class="main-body page-width">
        <div class="support-button">
            <c:choose><c:when test="${alreadySupported}">
                <div class="text">你已经支持过${username}</div>
                <button class="blue mediumTransparent" disabled>支持一下</button>
            </c:when><c:otherwise>
                <div class="text">支持你的朋友${username}</div>
                <div class="text success">成功支持朋友${username}！</div>
                <button class="blue">支持一下</button>
            </c:otherwise></c:choose>
        </div>
    </div>
</div>
<%@include file="inc-footer.jsp" %>
