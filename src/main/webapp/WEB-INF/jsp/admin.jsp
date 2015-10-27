<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="page-width form">
    <q:multipart-form action="admin-submit">
        <%@include file="admin-form.jsp" %>
    </q:multipart-form>
</div>
<q:handlebars-template id="link-template">
    <jsp:include page="admin-link-table-row.jsp"/>
</q:handlebars-template>
<form class="float-panel shadow" id="emailAddEditForm">
    <input type="hidden" name="id"/>

    <div class="type">
        <span class="simple-mail">
            <input type="radio" name="type" value="SimpleMailAccount" checked>普通邮箱
        </span>
        <span class="send-cloud">
            <input type="radio" name="type" value="SendCloudAccount">SendCloud
        </span>
    </div>
    <table class="simple-mail">
        <tbody>
        <tr>
            <td class="title">发件箱服务器地址</td>
            <td class="content"><input type="text" class="form-control" name="host"
                                       placeholder="输入发件箱服务器地址，如smtp.sina.com"></td>
        </tr>
        <tr>
            <td class="title">发件箱用户名</td>
            <td class="content"><input type="text" class="form-control" name="username"
                                       placeholder="输入发件箱用户名，如test12345@sina.com"></td>
        </tr>
        <tr>
            <td class="title">发件箱密码</td>
            <td class="content"><input type="password" class="form-control" name="password"
                                       placeholder="输入发件箱密码"/></td>
        </tr>
        </tbody>
    </table>
    <table class="send-cloud">
        <tbody>
        <tr>
            <td class="title">用户名</td>
            <td class="content"><input type="text" class="form-control" name="user"
                                       placeholder="输入sendcloud帐号的用户名"></td>
        </tr>
        <tr>
            <td class="title">域名</td>
            <td class="content"><input type="text" class="form-control" name="domainName"
                                       placeholder="输入sendcloud帐号的域名"></td>
        </tr>
        <tr>
            <td class="title">apiKey</td>
            <td class="content"><input type="text" class="form-control" name="apiKey"
                                       placeholder="输入sendcloud帐号的apiKey"/></td>
        </tr>
        </tbody>
    </table>
    <div class="submit">
        <button type="button" class="btn btn-success" id="addEmailSubmit">确定</button>
        <button type="button" class="btn btn-default" id="addEmailCancel">取消</button>
    </div>
</form>
<%@include file="inc-footer.jsp" %>
