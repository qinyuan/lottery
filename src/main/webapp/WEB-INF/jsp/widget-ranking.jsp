<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-taglib.jsp" %>
<span <c:if test="${param.upAction!=null}">ng-click="${param.upAction}"</c:if>>
    <img class="link rank-up" title="上移" src="resources/css/images/arrow_up.png"/>
</span>
<span <c:if test="${param.downAction!=null}">ng-click="${param.downAction}"</c:if>>
    <img class="link rank-down" title="下移" src="resources/css/images/arrow_down.png"/>
</span>
