<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div id="tel">
    <div class="text">
        <span class="title">当前号码</span>
        <span class="old-tel">${user.tel != null && fn:length(user.tel) > 0 ? user.tel : '未绑定'}</span>
    </div>
    <div class="text">
        <span class="title">新手机号</span>
        <input type="text" class="form-control" name="newTel" maxlength="11"/>
    </div>
    <div class="text">
        <span class="title">当前密码</span>
        <input type="password" class="form-control" name="password"/>
    </div>
    <div class="edit-submit" style="margin-left:105px;">
        <c:set var="changeable" value="${telModification.infiniteTimes || telModification.remainingTimes > 0}"/>

        <button type="button" class="${changeable ? 'ok' : 'ok deepTransparent'}"
                <c:if test="${!changeable}"> disabled</c:if>>
            确定
        </button>
        <div class="tel-modification-info">
            <c:if test="${!telModification.infiniteTimes && telModification.usedTimes > 0}">
                提示：
                您最近一年已经修改了${telModification.usedTimes}次号码，
                <c:choose> <c:when test="${telModification.remainingTimes > 0}">
                    还可以修改${telModification.remainingTimes}次
                </c:when><c:otherwise>
                    暂时不能再修改
                </c:otherwise></c:choose>
            </c:if>
        </div>
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
