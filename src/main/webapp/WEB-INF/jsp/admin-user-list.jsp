<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="page-width form">
    <div>
        <table class="normal">
            <colgroup>
                <col class="select"/>
                <col class="index"/>
                <c:forEach var="alias" items="${userTable.aliases}">
                    <col class="${alias}"/>
                </c:forEach>
            </colgroup>
            <thead>
            <th>选择</th>
            <th>序号</th>
            <c:forEach var="head" items="${userTable.heads}">
                <th>${head}</th>
            </c:forEach>
            </thead>
            <tbody>
            <c:forEach var="user" items="${users}" varStatus="status">
                <tr data-options="id:${user.id}">
                    <td><input type="checkbox" name="user_${user.id}"/></td>
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
<%@include file="inc-footer.jsp" %>
