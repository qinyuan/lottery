<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="page-width form">
    <div>
        <div class="buttons">
            <button class="btn btn-primary btn-sm" id="openMailForm" disabled>发送邮件</button>
        </div>
        <table class="normal">
            <colgroup>
                <col class="select"/>
                <col class="index"/>
                <c:forEach var="alias" items="${userTable.aliases}">
                    <col class="${alias}"/>
                </c:forEach>
            </colgroup>
            <thead>
            <th><input type="checkbox" class="select-all" title="全选/全不选"/></th>
            <th>序号</th>
            <c:forEach var="head" items="${userTable.heads}" varStatus="status">
                <th class="${userTable.aliases[status.index]}">${head}
                    <div title="排序筛选" class="filter">
                        <button class="${userTable.headStyles[status.index]}"></button>
                    </div>
                </th>
            </c:forEach>
            </thead>
            <tbody>
            <c:forEach var="user" items="${users}" varStatus="status">
                <tr data-options="id:${user.id}">
                    <td><input type="checkbox" class="select-user" name="userIds" value="${user.id}"/></td>
                    <td>${status.index + rowStartIndex}</td>
                    <c:forEach var="col" items="${user.cols}">
                        <td>${col}</td>
                    </c:forEach>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <%@include file="widget-pagination.jsp" %>
    </div>
</div>
<form class="float-panel" id="mailForm">
    <table>
        <tbody>
        <tr>
            <td class="title">发件箱</td>
            <td class="mail-account">
                <c:forEach var="mailAccount" items="${mailAccounts}">
                    <button data-options="id:${mailAccount.id}" type="button">${mailAccount.username}</button>
                </c:forEach>
            </td>
        </tr>
        <tr>
            <td class="title">标题</td>
            <td><input type="text" class="form-control" name="subject"/></td>
        </tr>
        <tr>
            <td class="title">正文</td>
            <td><textarea name="content" id="mailContent"></textarea></td>
        </tr>
        </tbody>
    </table>
    <div class="submit">
        <button type="button" id="submitMail" class="btn btn-success">确定</button>
        <button type="button" id="cancelMail" class="btn btn-default">取消</button>
    </div>
</form>
<%@include file="inc-footer.jsp" %>
