<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-taglib.jsp" %>
<span<c:if test="${param.deleteAction!=null}"> ng-click="${param.deleteAction}"</c:if>>
    <img class="link delete" title="删除" src="resources/css/images/delete.png"/>
</span>