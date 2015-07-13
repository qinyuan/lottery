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
        <%@include file="admin-lottery-activity-list.jsp"%>
    </div>
</div>
<%@include file="admin-lottery-activity-add-edit-form.jsp" %>
<form id="announceEditForm" class="shadow float-panel">
    <input type="hidden" name="id"/>
    <table>
        <tbody>
        <tr>
            <td class="title">中奖号码</td>
            <td class="content">
                <input type="text" name="winners" class="form-control" placeholder="在此输入中奖号码，多个号码间用英文逗号分隔"/>
            </td>
        </tr>
        <tr>
            <td class="title">中奖公告</td>
            <td class="content">
                <textarea name="announcement" class="form-control" cols="35" rows="5"></textarea>
            </td>
        </tr>
        </tbody>
    </table>
    <div class="submit">
        <button type="submit" name="ok" class="btn btn-primary">保存</button>
        <button type="button" name="cancel" class="btn btn-default">取消</button>
    </div>
</form>
<%@include file="inc-footer.jsp" %>
