<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="main-body page-width">
    <div class="shadow">
        <c:choose>
            <c:when test="${email == null}">
                <span class="invalid">此链接已经失效！！！</span>
            </c:when>
            <c:otherwise>
                邮箱<span class="email">${email}</span>验证成功！
            </c:otherwise>
        </c:choose>
    </div>
</div>
<%@include file="inc-footer.jsp" %>
