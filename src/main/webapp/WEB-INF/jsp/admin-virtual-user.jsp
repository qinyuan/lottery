<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="page-width form">
    <div style="padding:12px 20px;">
        <div class="users">
            <c:forEach var="user" items="${users}">
                <div class="user" data-options="id:${user.id}">
                    <div class="username" title="用户名">${user.username}</div>
                    <div class="tel" title="手机号码">${user.tel}</div>
                    <div class="mail" title="邮箱">${user.mail}</div>
                    <div class="edit" title="编辑用户">
                        <img class="edit" src="resources/css/images/pencil.png"/>
                    </div>
                    <div class="delete" title="删除用户">
                        <img class="delete" src="resources/css/images/delete.png"/>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div>
            <button class="button button-primary button-tiny button-circle" id="addVirtualUser"
                    title="添加用户"><i class="fa fa-plus"></i></button>
        </div>
    </div>
</div>
<form id="addEditForm" class="float-panel shadow">
    <input type="hidden" name="id"/>
    <table>
        <tbody>
        <tr>
            <td>用户名</td>
            <td><input type="text" class="form-control" name="username"/></td>
        </tr>
        <tr>
            <td>手机号前两位</td>
            <td><input type="text" class="form-control" name="telPrefix" maxlength="2"
                       placeholder="格式如：'15','13'"/></td>
        </tr>
        <tr>
            <td>手机号后四位</td>
            <td><input type="text" class="form-control" name="telSuffix" maxlength="4"
                       placeholder="格式如：'1234','4321'"/></td>
        </tr>
        <tr>
            <td>邮箱前两个字符</td>
            <td><input type="text" class="form-control" name="mailPrefix" maxlength="2"
                       placeholder="格式如：'ab','yi'"/></td>
        </tr>
        <tr>
            <td>邮箱后缀</td>
            <td><input type="text" class="form-control" name="mailSuffix"
                       placeholder="格式如：'@sina.com','@qq.com'"/></td>
        </tr>
        </tbody>
        <tr>
            <td class="buttons" colspan="2" style="text-align:center;">
                <button type="button" class="btn btn-primary" id="addEditOk">确定</button>
                <button type="button" class="btn btn-default" id="addEditCancel">取消</button>
            </td>
        </tr>
    </table>
</form>
<%@include file="inc-footer.jsp" %>
