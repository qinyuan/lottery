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
            <%@include file="security-navigation.jsp" %>
        </div>
        <div class="slogan">
            <img src="${indexHeaderSlogan}"/>
        </div>
    </div>
</div>
