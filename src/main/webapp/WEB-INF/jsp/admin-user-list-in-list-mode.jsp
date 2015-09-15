<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-taglib.jsp" %>
<div class="count">
    <span>总注册用户数：<span class="number">${userCount}</span></span>
    <span>已激活用户数：<span class="number">${activeUserCount}</span></span>
    <span>直接注册用户数：<span class="number">${directlyRegisterUserCount}</span></span>
    <span>邀请注册用户数：<span class="number">${invitedRegisterUserCount}</span></span>
</div>
<div class="user-list">
    <div class="title">
        用户列表
        <span class="comment">列表格式：用户名 爱心数 最后登录时间</span>
    </div>
    <form class="search">
        <input type="text" class="form-control" name="keyword" placeholder="输入需要搜索的用户名、邮箱或密码" value="${keyword}"/>

        <div class="icon"><img class="link" title="确定" src="resources/css/images/search.png"/></div>
    </form>
    <div class="content">
        <div class="user-filter">
            <div class="title">筛选：</div>
            <div class="content">
                <div class="activity">
                    <c:forEach var="activity" items="${selectedLotteryActivities}">
                        <div class="item" data-options="id:${activity.id}">
                            <div class="text">第${activity.term}期</div>
                            <div class="icon"><img class="link" src="resources/css/images/close.gif"/></div>
                        </div>
                    </c:forEach>
                    <button class="button button-primary button-circle" title="添加活动"><i class="fa fa-plus"></i></button>
                </div>
                <div class="liveness">
                    爱心值 ≥ <input type="text" class="form-control min-liveness" value="${minLiveness}"/>
                    <button id="livenessFilterSubmit" class="btn btn-success">确定</button>
                </div>
            </div>
        </div>
        <div class="list-body">
            <c:forEach var="user" items="${users}">
                <div class="user">
                    <div class="id"><input type="checkbox" class="select-user list-mode" value="${user[0]}"></div>
                    <div class="username" title="姓名">${user[1]}</div>
                    <div class="liveness" title="爱心">${user[2] == null ? 0 : user[2]}</div>
                    <div class="login-time" title="最后一次登录时间">${user[3]}</div>
                    <div class="edit" title="编辑">
                        <a href="personal-center.html?id=${user[0]}" target="_blank">
                            <img class="mediumTransparent" src="resources/css/images/pencil.png"/>
                        </a>
                    </div>
                </div>
            </c:forEach>
            <c:choose>
                <c:when test="${fn:length(users)>0}">
                    <div class="buttons">
                        <input type="checkbox" id="selectOrUnselectUsers" class="select-all"/>全选/全不选
                        <button class="btn btn-primary" id="openSystemInfoForm" disabled>发送系统消息</button>
                        <button class="btn btn-info" id="openMailForm" disabled>发送邮件</button>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="no-user">没有符合条件的用户！</div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
<div class="location-count">
    <div class="title">注册区域人数统计(按第一次登录地区)</div>
    <div class="locations">
        <c:forEach var="locationCount" items="${locationCounts}">
            <div class="location">
                    ${locationCount.left}: ${locationCount.right}
            </div>
        </c:forEach>
        <c:if test="${fn:length(locationCounts) == 0}">
            <span class="no-register">无注册用户</span>
        </c:if>
    </div>
</div>
