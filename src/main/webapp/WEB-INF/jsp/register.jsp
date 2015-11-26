<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="register-header.jsp" %>
<div class="white-back">
    <div class="main-body page-width shadow">
        <c:choose><c:when test="${byQQ}">
            <%@include file="register-qq.jsp" %>
        </c:when><c:otherwise>
            <%@include file="register-normal.jsp" %>
        </c:otherwise></c:choose>
    </div>
</div>
<%@include file="inc-footer.jsp" %>
