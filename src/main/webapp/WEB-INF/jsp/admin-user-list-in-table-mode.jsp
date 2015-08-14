<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-taglib.jsp" %>
<div class="buttons">
    <button class="btn btn-primary btn-sm" id="openSystemInfoForm" disabled>发送系统消息</button>
    <button class="btn btn-info btn-sm" id="openMailForm" disabled>发送邮件</button>
</div>
<table class="normal">
    <colgroup>
        <col class="select"/>
        <col class="index"/>
        <c:forEach var="alias" items="${table.aliases}">
            <col class="${alias}"/>
        </c:forEach>
    </colgroup>
    <thead>
    <th><input type="checkbox" class="select-all" title="全选/全不选"/></th>
    <th>序号</th>
    <c:forEach var="head" items="${table.heads}" varStatus="status">
        <th class="${table.aliases[status.index]}">${head}
            <div title="排序筛选" class="filter">
                <button class="${table.headStyles[status.index]}"></button>
            </div>
        </th>
    </c:forEach>
    </thead>
    <tbody>
    <c:forEach var="user" items="${paginationItems}" varStatus="status">
        <tr data-options="id:${user.id}">
            <td><input type="checkbox" class="select-user" name="userIds" value="${user.id}"/></td>
            <td>${status.index + rowStartIndex}</td>
            <c:forEach var="col" items="${user.cols}" varStatus="innerStatus">
                <td class="${table.aliases[innerStatus.index]}">${col}</td>
            </c:forEach>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%@include file="widget-pagination.jsp" %>
