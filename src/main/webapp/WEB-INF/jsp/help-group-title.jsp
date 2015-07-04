<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-taglib.jsp" %>
<div class="help-list"<c:if test="${helpGroup.id != null}"> data-options="helpGroupId:${helpGroup.id}"</c:if>>
    <div class="title">
        <span class="icon"></span>
        <span class="content">${helpGroup.title == null ? '{{helpGroupTitle}}' : helpGroup.title }</span>
        <security:authorize ifAnyGranted="ROLE_ADMIN">
            <div class="action">
                <img class="link up" title="上移" src="resources/css/images/arrow_up.png"/>
                <img class="link down" title="下移" src="resources/css/images/arrow_down.png"/>
                <img class="link edit" title="编辑" src="resources/css/images/pencil.png"/>
                <img class="link delete" title="删除" src="resources/css/images/delete.png"/>
            </div>
        </security:authorize>
    </div>
    <ul>
        <c:forEach var="helpItem" items="${helpGroup.items}">
            <li><a href="#">${helpItem.title}</a></li>
        </c:forEach>
    </ul>
    <security:authorize ifAnyGranted="ROLE_ADMIN">
        <div class="add-help-item">
            <img class="link" title="添加条目" src="resources/css/images/add.png"/>
        </div>
    </security:authorize>
</div>
