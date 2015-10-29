<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div id="personalInfo">
    <div class="text">昵称：
        <span class="text">
            <span class="old-username">${user.username}</span>
            <a class="edit-icon" id="editUsername" href="javascript:void(0)">
                <img src="resources/css/images/note_edit.png"/>
            </a>
        </span>
        <span class="input" style="display:none;">
            <input type="text" id="usernameInput" class="form-control" value="${user.username}" maxlength="14"/>
        </span>
    </div>
    <div class="edit-submit" style="display:none;">
        <button class="ok" type="button">保存</button>
        <a class="cancel" href="javascript:void(0)">取消</a>
    </div>
    <div class="text">邮箱： ${user.email}</div>
    <div class="text">手机号： ${user.tel}
        <a class="edit-icon" href="setting.html?index=3"><img src="resources/css/images/bind-tel.png"/></a>
    </div>
    <div class="text">常用登录地区： ${location}</div>
    <div class="text"><span class="liveness-icon lightTransparent"></span>： ${liveness}</div>
</div>
