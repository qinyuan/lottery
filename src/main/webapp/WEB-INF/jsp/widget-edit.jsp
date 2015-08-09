<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-taglib.jsp" %>
<span<c:if test="${param.editAction!=null}"> ng-click="${param.editAction}"</c:if>>
    <img class="link edit" title="编辑" src="resources/css/images/pencil.png"/>
</span>
