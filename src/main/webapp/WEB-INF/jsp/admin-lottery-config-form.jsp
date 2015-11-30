<%@ page import="com.qinyuan15.lottery.mvc.activity.InvalidLotteryLotPlaceholderConverter" %>
<%@ page import="com.qinyuan15.lottery.mvc.activity.LotteryAnnouncementPlaceholderConverter" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="edit-unit">
    <div class="title">新浪微博分享链接设置</div>
    <div class="content">
        <table>
            <tbody>
            <tr>
                <td class="title">文字内容</td>
                <td class="input">
                    <input type="text" class="form-control" name="sinaWeiboTitle" value="${sinaWeiboTitle}"
                           placeholder="在此输入分享文字"/>
                </td>
            </tr>
            <tr>
                <td class="title">是否分享图片</td>
                <td class="input boolean">
                    <span><input type="radio" name="sinaWeiboIncludePicture" value="true"
                                 <c:if test="${sinaWeiboIncludePicture}">checked</c:if>>是</span>
                    <span><input type="radio" name="sinaWeiboIncludePicture" value="false"
                                 <c:if test="${!sinaWeiboIncludePicture}">checked</c:if>>否</span>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<div class="edit-unit">
    <div class="title">QQ分享链接设置</div>
    <div class="content">
        <table>
            <tbody>
            <tr>
                <td class="title">标题</td>
                <td class="input">
                    <input type="text" class="form-control" name="qqTitle" value="${qqTitle}"
                           placeholder="在此输入标题"/>
                </td>
            </tr>
            <tr>
                <td class="title">详细描述</td>
                <td class="input">
                    <input type="text" class="form-control" name="qqSummary" value="${qqSummary}"
                           placeholder="在此输入详细描述"/>
                </td>
            </tr>
            <tr>
                <td class="title">是否分享图片</td>
                <td class="input boolean">
                    <span><input type="radio" name="qqIncludePicture" value="true"
                                 <c:if test="${qqIncludePicture}">checked</c:if>>是</span>
                    <span><input type="radio" name="qqIncludePicture" value="false"
                                 <c:if test="${!qqIncludePicture}">checked</c:if>>否</span>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<div class="edit-unit">
    <div class="title">QQ空间分享链接设置</div>
    <div class="content">
        <table>
            <tbody>
            <tr>
                <td class="title">标题</td>
                <td class="input">
                    <input type="text" class="form-control" name="qzoneTitle" value="${qzoneTitle}"
                           placeholder="在此输入标题"/>
                </td>
            </tr>
            <tr>
                <td class="title">详细描述</td>
                <td class="input">
                    <input type="text" class="form-control" name="qzoneSummary" value="${qzoneSummary}"
                           placeholder="在此输入详细描述"/>
                </td>
            </tr>
            <tr>
                <td class="title">是否分享图片</td>
                <td class="input boolean">
                    <span><input type="radio" name="qzoneIncludePicture" value="true"
                                 <c:if test="${qzoneIncludePicture}">checked</c:if>>是</span>
                    <span><input type="radio" name="qzoneIncludePicture" value="false"
                                 <c:if test="${!qzoneIncludePicture}">checked</c:if>>否</span>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<div class="edit-unit">
    <div class="title">用户爱心增加后的通知设置</div>
    <div class="content">
        <table>
            <tbody>
            <tr>
                <td class="title">通知方式</td>
                <td class="input">
                    <input type="checkbox" name="remindLivenessIncreaseBySystemInfo"
                           <c:if test="${remindLivenessIncreaseBySystemInfo}">checked</c:if>/>系统消息
                </td>
            </tr>
            <tr class="system-info-config"<c:if
                    test="${!remindLivenessIncreaseBySystemInfo}"> style="display:none;"</c:if>>
                <td class="title">系统消息模板
                    <div class="comment">
                        注：<br/>
                        {{user}}指代用户名；<br/>
                        {{invitee}}指代被邀请者；<br/>
                        {{add_l}}指代增加的爱心；<br/>
                        {{liveness}}指代总爱心；<br/>
                        {{url}}指代抽奖链接。
                    </div>
                </td>
                <td class="input">
                    <textarea name="livenessIncreaseSystemInfoTemplate"
                              class="ckeditor">${livenessIncreaseSystemInfoTemplate}</textarea>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<div class="edit-unit">
    <div class="title">用户获得新抽奖机会的通知设置</div>
    <div class="content">
        <table class="email-template">
            <tbody>
            <tr>
                <td class="title">通知方式</td>
                <td class="input">
                    <input type="checkbox" name="remindNewLotteryChanceByMail"
                           <c:if test="${remindNewLotteryChanceByMail}">checked</c:if>/>邮件
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="checkbox" name="remindNewLotteryChanceBySystemInfo"
                           <c:if test="${remindNewLotteryChanceBySystemInfo}">checked</c:if>/>系统消息
                </td>
            </tr>
            <tr class="mail-config"<c:if
                    test="${!remindNewLotteryChanceByMail}"> style="display:none;"</c:if>>
                <td class="title">发件箱</td>
                <td class="input">
                    <c:set var="selectFormItems" value="${mailSelectFormItems}"/>
                    <c:set var="selectFormId" value="new-lottery-chance-mail-select"/>
                    <c:set var="selectFormName" value="newLotteryChanceMailAccountId"/>
                    <%@include file="widget-select-form.jsp" %>
                </td>
            </tr>
            <tr class="mail-config"<c:if
                    test="${!remindNewLotteryChanceByMail}"> style="display:none;"</c:if>>
                <td class="title">邮件标题</td>
                <td class="input">
                    <input type="text" name="newLotteryChanceMailSubjectTemplate" class="form-control"
                           value="${newLotteryChanceMailSubjectTemplate}" placeholder="在此输入邮件的标题"/>
                </td>
            </tr>
            <tr class="mail-config"<c:if
                    test="${!remindNewLotteryChanceByMail}"> style="display:none;"</c:if>>
                <td class="title">邮件正文模板
                    <div class="comment">
                        注：<br/>
                        {{user}}指代用户名；<br/>
                        {{url}}指代抽奖链接。
                    </div>
                </td>
                <td class="input">
                    <textarea name="newLotteryChanceMailContentTemplate"
                              class="ckeditor">${newLotteryChanceMailContentTemplate}</textarea>
                </td>
            </tr>
            <tr class="system-info-config"<c:if
                    test="${!remindNewLotteryChanceBySystemInfo}"> style="display:none;"</c:if>>
                <td class="title">系统消息模板
                    <div class="comment">
                        注：<br/>
                        {{user}}指代用户名；<br/>
                        {{url}}指代抽奖链接。
                    </div>
                </td>
                <td class="input">
                    <textarea name="newLotteryChanceSystemInfoTemplate"
                              class="ckeditor">${newLotteryChanceSystemInfoTemplate}</textarea>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<div class="edit-unit">
    <div class="title">无效抽奖号提醒设置</div>
    <div class="content">
        <table>
            <tbody>
            <tr>
                <td class="title">手机未填的提醒
                    <div class="comment">
                        注：<br/>
                        <%=InvalidLotteryLotPlaceholderConverter.PHASE_PLACEHOLDER%>指代抽奖的期数
                    </div>
                </td>
                <td class="input">
                    <textarea name="noTelInvalidLotSystemInfoTemplate"
                              class="ckeditor">${noTelInvalidLotSystemInfoTemplate}</textarea>
                </td>
            </tr>
            <tr>
                <td class="title">爱心不足提醒
                    <div class="comment">
                        注：<br/>
                        <%=InvalidLotteryLotPlaceholderConverter.PHASE_PLACEHOLDER%>指代抽奖的期数<br/>
                        <%=InvalidLotteryLotPlaceholderConverter.LIVENESS_PLACEHOLDER%>指代用户的爱心数<br/>
                        <%=InvalidLotteryLotPlaceholderConverter.MIN_LIVENESS_PLACEHOLDER%>指代抽奖要求的最少爱心数
                    </div>
                </td>
                <td class="input">
                    <textarea name="insufficientLivenessInvalidLotSystemInfoTemplate"
                              class="ckeditor">${insufficientLivenessInvalidLotSystemInfoTemplate}</textarea>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<div class="edit-unit">
    <div class="title">手机号相关配置</div>
    <div class="content">
        <table>
            <tbody>
            <tr>
                <td class="title wide">支持超过指定值时必须填写手机号</td>
                <td class="input narrow">
                    <input type="text" class="form-control" name="noTelLiveness" value="${noTelLiveness}"/>
                </td>
            </tr>
            <tr>
                <td class="title wide">一年内可修改手机号的次数</td>
                <td class="input narrow">
                    <input type="text" class="form-control" name="maxTelModificationTimes"
                           value="${maxTelModificationTimes}"/>
                </td>
            </tr>
            <%--<tr>
                <td class="title wide">未填手机时最多可参与的抽奖次数</td>
                <td class="input narrow">
                    <input type="text" class="form-control" name="noTelLotteryLotCount"
                           value="${noTelLotteryLotCount}"/>
                </td>
            </tr>
            <tr>
                <td class="title wide">未填手机时最多可参与的抽奖金额</td>
                <td class="input narrow">
                    <input type="text" class="form-control" name="noTelLotteryLotPrice"
                           value="${noTelLotteryLotPrice}"/>
                </td>
            </tr>--%>
            </tbody>
        </table>
    </div>
</div>
<div class="edit-unit">
    <div class="title">支持页设置</div>
    <div class="content">
        <table>
            <tbody>
            <tr>
                <td class="title">图片</td>
                <td class="input">
                    <jsp:include page="widget-upload-image.jsp">
                        <jsp:param name="id" value="supportPageImage"/>
                        <jsp:param name="value" value="${supportPageImage}"/>
                        <jsp:param name="snapshot" value="true"/>
                    </jsp:include>
                </td>
            </tr>
            <tr>
                <td class="title">文字说明</td>
                <td class="input">
                    <textarea class="ckeditor" name="supportPageText">${supportPageText}</textarea>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<div class="edit-unit">
    <div class="title">其他设置</div>
    <div class="content">
        <table>
            <tbody>
            <tr>
                <td class="wide title">分享给好友且好友注册或登录后增加的爱心</td>
                <td class="input narrow">
                    <input type="text" class="form-control" name="shareSucceedLiveness"
                           value="${shareSucceedLiveness}" placeholder="在此输入分享成功增加的爱心数"/>
                </td>
            </tr>
            <tr>
                <td class="wide title">抽奖规则描述页面链接</td>
                <td class="input narrow">
                    <input type="text" class="form-control" name="lotteryRuleLink"
                           value="${lotteryRuleLink}" placeholder="在此输入帮助中心中的抽奖规则描述链接">
                </td>
            </tr>
            </tbody>
        </table>
        <table>
            <tbody>
            <%--<tr>
                <td class="title">抽奖规则</td>
                <td class="input">
                    <textarea class="ckeditor" name="lotteryRule">${lotteryRule}</textarea>
                </td>
            </tr>--%>
            <tr>
                <td class="title">抽奖结果公告
                    <div class="comment">
                        注：<br/>
                        <%=LotteryAnnouncementPlaceholderConverter.BALL_PHASE_PLACEHOLDER%>指代双色球期数<br/>
                        <%=LotteryAnnouncementPlaceholderConverter.BALL_NUMBER_PLACEHOLDER%>指代双色球开奖结果<br/>
                        <%=LotteryAnnouncementPlaceholderConverter.WINNER_NUMBER_PLACEHOLDER%>指代获奖者的抽奖号<br/>
                        <%=LotteryAnnouncementPlaceholderConverter.PARTICIPANT_COUNT_PLACEHOLDER%>指代总参与人数
                    </div>
                </td>
                <td class="input">
                    <textarea name="lotteryAnnouncementTemplate"
                              class="ckeditor">${lotteryAnnouncementTemplate}</textarea>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
<div class="submit">
    <button id="submitButton" type="submit" class="btn btn-success">确定</button>
</div>
