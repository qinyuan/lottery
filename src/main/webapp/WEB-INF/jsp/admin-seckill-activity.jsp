<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="page-width form">
    <div>
        <button type="button" id="addSeckillActivityButton" class="btn btn-success">添加秒杀</button>
        <div class="list-type">
            <label>活动类型选择：</label>
            <input type="radio" value="running" name="listType"<c:if test="${!listExpire}"> checked</c:if>/>正在运行
            <input type="radio" value="expire" name="listType"<c:if test="${listExpire}"> checked</c:if>/>已结束
        </div>
        <%@include file="admin-seckill-activity-list.jsp" %>
    </div>
</div>
<%@include file="admin-seckill-activity-add-edit-form.jsp" %>
<%@include file="admin-seckill-activity-announcement-form.jsp" %>
<%@include file="inc-footer.jsp" %>
