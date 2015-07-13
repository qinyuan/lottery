<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="page-width form">
    <div>
        <button type="button" id="addLotteryActivityButton" class="btn btn-success">添加抽奖</button>
        <div class="list-type">
            <label>抽奖类型选择：</label>
            <input type="radio" value="running" name="listType"<c:if test="${!listExpire}"> checked</c:if>/>正在运行
            <input type="radio" value="expire" name="listType"<c:if test="${listExpire}"> checked</c:if>/>已结束
        </div>
        <%@include file="admin-lottery-activity-list.jsp" %>
    </div>
</div>
<%@include file="admin-lottery-activity-add-edit-form.jsp" %>
<%@include file="admin-lottery-activity-announcement-form.jsp" %>
<%@include file="inc-footer.jsp" %>
