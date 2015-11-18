<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<div class="header">
    <div class="logo page-width">
        <div class="left">
            <c:if test="${registerHeaderLeftLogo != null}"><img src="${registerHeaderLeftLogo}"/></c:if>
        </div>
        <div class="right">
            <c:if test="${registerHeaderRightLogo != null}"><img src="${registerHeaderRightLogo}"/></c:if>
        </div>
    </div>
    <div class="split"></div>
</div>
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
