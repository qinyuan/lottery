<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-taglib.jsp" %>
<form class="float-panel shadow edit-form" id="mailForm">
    <table>
        <tbody>
        <tr>
            <td class="title">发件箱</td>
            <td class="mail-account">
                <c:forEach var="mailAccount" items="${mailAccounts}">
                    <button data-options="id:${mailAccount.id}" type="button">${mailAccount.username}</button>
                </c:forEach>
            </td>
        </tr>
        <tr class="comment">
            <td style="text-align:right;">(说明)</td>
            <td>在邮件标题或正文中，可以用{{user}}指代收件人的用户名</td>
        </tr>
        <tr>
            <td class="title">标题</td>
            <td><input type="text" class="form-control" name="subject"/></td>
        </tr>
        <tr>
            <td class="title">正文</td>
            <td><textarea name="content" id="mailContent"></textarea></td>
        </tr>
        </tbody>
    </table>
    <div class="submit">
        <button type="button" id="previewMailButton" class="btn btn-primary">预览</button>
        <button type="button" id="submitMail" class="btn btn-success">确定</button>
        <button type="button" id="cancelMail" class="btn btn-default">取消</button>
    </div>
</form>
<form class="float-panel shadow edit-form" id="systemInfoForm">
    <div style="margin-bottom:10px;">
        <div class="comment">在邮件标题或正文中，可以用{{user}}指代收件人的用户名</div>
        <textarea name="content" id="systemInfoContent"></textarea>
    </div>
    <div class="submit">
        <button type="button" id="previewSystemInfoButton" class="btn btn-primary">预览</button>
        <button type="button" id="submitSystemInfo" class="btn btn-success">确定</button>
        <button type="button" id="cancelSystemInfo" class="btn btn-default">取消</button>
    </div>
</form>
<div class="float-panel shadow preview-panel" id="mailPreview">
    <div class="subject"></div>
    <div class="content"></div>
    <div class="button">
        <button class="btn btn-primary" id="cancelMailPreview">返回</button>
    </div>
</div>
<div class="float-panel shadow preview-panel" id="systemInfoPreview">
    <div class="content"></div>
    <div class="button">
        <button class="btn btn-primary" id="cancelSystemInfoPreview">返回</button>
    </div>
</div>
<form class="float-panel shadow edit-form" id="lotteryActivityFilterForm">
    <div class="activities">
        <c:forEach var="activity" items="${lotteryActivities}">
            <div class="activity" title="单击选择" data-options="id:${activity.id}">
                <div class="term">第${activity.term}期</div>
                <div class="commodity">奖品：${activity.commodity.name}</div>
            </div>
        </c:forEach>
    </div>
    <div class="submit">
        <button type="button" id="submitLotteryActivityFilter" class="btn btn-success">确定</button>
        <button type="button" id="cancelLotteryActivityFilter" class="btn btn-default">取消</button>
    </div>
</form>
