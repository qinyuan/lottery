<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="gray-back">
    <div class="page-width main-body">
        <div class="left">
            <div class="title">设置</div>
            <div class="nav">
                <c:forEach var="title" items="${titles}" varStatus="status">
                    <div>
                        <c:choose>
                            <c:when test="${status.index == pageIndex}">
                                <span>${title}</span>
                            </c:when>
                            <c:otherwise>
                                <a href="setting.html?index=${status.index}">${title}</a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="right">
            <div class="title">${titles[pageIndex]}</div>
            <div class="body">
                <c:if test="${pageIndex == 0}">
                    <div class="text">昵称：
                        <span class="text">${user.username}
                            <a class="edit-icon" id="editUsername" href="javascript:void(0)">
                                <img src="resources/css/images/note_edit.png"/></a>
                        </span>
                        <span class="input" style="display:none;">
                            <input type="text" id="usernameInput" class="form-control" value="${user.username}"
                                   maxlength="14"/>
                        </span>
                    </div>
                    <div class="edit-submit" style="display:none;">
                        <button class="ok" type="button">保存</button>
                        <a class="cancel" href="javascript:void(0)">取消</a>
                    </div>
                    <div class="text">邮箱： ${user.email}</div>
                    <div class="text">手机号： ${user.tel}<a class="edit-icon" href="setting.html?index=4"><img
                            src="resources/css/images/bind-tel.png"/></a></div>
                    <div class="text">常用登录地区： ${location}</div>
                    <div class="text"><span class="liveness-icon lightTransparent"></span>： ${liveness}</div>
                </c:if>
                <c:if test="${pageIndex == 1}">
                    <c:forEach var="systemInfo" items="${systemInfoItems}">
                        <div>
                            <div class="system-info">
                                <div class="info break-word">${systemInfo.content}</div>
                                <div class="time">${systemInfo.buildTime}</div>
                            </div>
                        </div>
                    </c:forEach>
                    <%@include file="widget-pagination.jsp"%>
                </c:if>
            </div>
        </div>
    </div>
</div>
<q:handlebars-template id="page1">
    <div>昵称：{{name}}</div>
</q:handlebars-template>
<q:handlebars-template id="page2">
</q:handlebars-template>
<%@include file="inc-footer.jsp" %>
