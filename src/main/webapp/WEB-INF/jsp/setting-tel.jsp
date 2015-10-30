<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div id="tel">
    <div class="text">
        <span class="title">当前号码</span>
        <span class="old-tel">${user.tel}</span>
    </div>
    <div class="text">
        <span class="title">新手机号</span>
        <input type="text" class="form-control" name="newTel" maxlength="11"/>
        <%--<span class="error"></span>--%>
    </div>
    <div class="text">
        <span class="title">当前密码</span>
        <input type="password" class="form-control" name="password"/>
        <%--<span class="error"></span>--%>
    </div>
    <div class="edit-submit" style="margin-left:105px;">
        <button type="button" class="ok">确定</button>
    </div>
    <div id="telValidate">
        <div class="text">
            <img src="resources/css/images/help.png"/>
            号码被占用，解决办法
        </div>
        <div class="text">
            若绑定新的号码号码已经被人绑定，通过发送你当前的手机箱发送
        </div>
        <div class="text">
            1、你当前的用户名<br/>
            2、你当前的注册邮箱
        </div>
        <div class="text">
            <a href="http://mail.10086.cn" target="_blank">移动号码手机邮箱 >></a><br/>
            <a href="http://mail.wo.cn" target="_blank">联通号码手机邮箱 >></a><br/>
            <a href="http://webmail30.189.cn" target="_blank">电信号码手机邮箱 >></a><br/>
        </div>
        <div class="text">
            备注：我们会在收到邮件后24小时内为你更新绑定，为了不影响抽奖活动的开奖，务必在开奖前24小时发送邮件。
        </div>
    </div>
</div>
