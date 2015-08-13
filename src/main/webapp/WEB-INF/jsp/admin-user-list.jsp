<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="page-width form">
    <div>
        <div class="display-mode">
            显示模式：
            <span style="padding:0 10px;"></span>
            <input type="radio" name="displayMode" value="list"<c:if test="${displayMode == 'list'}"> checked</c:if>/>
            列表
            <span style="padding:0 3px;"></span>
            <input type="radio" name="displayMode" value="table"<c:if test="${displayMode == 'table'}"> checked</c:if>/>
            表格
        </div>
        <c:if test="${displayMode == 'list' }">
            <%@include file="admin-user-list-in-list-mode.jsp" %>
        </c:if>
        <c:if test="${displayMode == 'table'}">
            <%@include file="admin-user-list-in-table-mode.jsp" %>
        </c:if>
    </div>
</div>
<%@include file="admin-user-list-float-panel.jsp" %>
<%@include file="inc-footer.jsp" %>
