<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="page-width form">
    <q:multipart-form action="admin-submit">
        <%@include file="admin-form.jsp"%>
    </q:multipart-form>
</div>
<q:handlebars-template id="link-template">
    <jsp:include page="admin-link-table-row.jsp"/>
</q:handlebars-template>
<form class="float-panel shadow" id="emailAddEditForm">
    <input type="hidden" name="id"/>
    <table>
        <tbody>
        <tr>
            <td>发件箱服务器地址</td>
            <td>
                <input type="text" class="form-control" name="mailHost" placeholder="输入发件箱服务器地址，如smtp.sina.com">
            </td>
        </tr>
        <tr>
            <td>发件箱用户名</td>
            <td>
                <input type="text" class="form-control" name="mailUsername" placeholder="输入发件箱用户名，如test12345@sina.com">
            </td>
        </tr>
        <tr>
            <td>发件箱密码</td>
            <td><input type="password" class="form-control" name="mailPassword" placeholder="输入发件箱密码"/></td>
        </tr>
        </tbody>
    </table>
    <div class="submit">
        <button type="button" class="btn btn-success" id="addEmailSubmit">确定</button>
        <button type="button" class="btn btn-default" id="addEmailCancel">取消</button>
    </div>
</form>
<%@include file="inc-footer.jsp" %>
