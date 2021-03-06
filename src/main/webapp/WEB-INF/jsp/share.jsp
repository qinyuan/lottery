<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="register-header.jsp" %>
<div class="white-back">
    <div class="main-body page-width">
        <div class="left">
            <div class="title">
                <security:authentication property="name"/>，
                您当前所获得的支持为
                ${liveness}
            </div>
            <div class="copy">
                <div class="title">把下面的链接通过QQ等方式发送给您的好友</div>
                <div class="input"><input class="form-control" type="text" value="${finalTargetUrl}"/></div>
                <div class="submit">
                    <button class="blue">复制链接</button>
                    <span class="info">链接已经成功复制到剪贴板！</span>
                </div>
            </div>
            <div class="share">
                <div class="title">通过转发等方式发给您的好友或粉丝</div>
                <ul>
                    <li><a class="sina" href="${sinaWeiboShareUrl}" target="_blank">
                        <span class="icon">&nbsp;</span>新浪微博</a></li>
                    <li><a class="qq" href="${qqShareUrl}" target="_blank">
                        <span class="icon">&nbsp;</span>QQ好友</a></li>
                    <li><a class="qzone" href="${qzoneShareUrl}" target="_blank">
                        <span class="icon">&nbsp;</span>QQ空间</a></li>
                </ul>
            </div>
        </div>
        <div class="right">
            <div class="title"></div>
            <div class="body">
                <c:choose><c:when test="${fn:length(receiveUsers)>0}">
                    <table>
                        <colgroup>
                            <col class="nickname"/>
                            <col class="liveness"/>
                            <col class="action"/>
                        </colgroup>
                        <thead>
                        <tr>
                            <td class="nickname">昵称</td>
                            <td class="liveness">支持数</td>
                            <td class="action"></td>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="receiveUser" items="${receiveUsers}">
                            <tr>
                                <td class="nickname">${receiveUser.username}</td>
                                <td class="liveness">${receiveUser.liveness}</td>
                                <td class="action">
                                    <a href="support.html?activityId=${activityId}&serial=${receiveUser.serial}&medium=initiative"
                                       target="_blank">
                                        <img class="link add-liveness" title="为ta增加支持"
                                             src="resources/css/images/add-liveness.png"/>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:when><c:otherwise>
                    <div class="no-liveness">暂无好友支持你！</div>
                </c:otherwise></c:choose>
            </div>
            <div class="bottom"></div>
        </div>
    </div>
</div>
<%@include file="inc-footer.jsp" %>
