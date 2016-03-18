<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-taglib.jsp" %>
<%-- this include tag is necessary, because this page may be used directory --%>
<ul>
    <security:authorize ifAnyGranted="ROLE_NORMAL">
        <li><a class="text username" href="setting.html?index=0" title="个人中心"><security:authentication property="name"/></a></li>
        <li><a class="text" href="j_spring_security_logout">退出</a></li>
        <li><a class="text" id="systemInformationNavigation" href="setting.html?index=1">消息<c:if
                test="${unreadSystemInfoCount > 0}"> <span
                class="unread-count">${unreadSystemInfoCount}</span></c:if></a></li>
        <li><a class="text" id="lotteryHistoryNavigation" href="activity-history.html">活动历史 ${activityCount}</a></li>
    </security:authorize>
    <security:authorize ifAnyGranted="ROLE_ADMIN">
        <li><a class="text" href="admin.html"><security:authentication property="name"/></a></li>
        <li><a class="text" href="j_spring_security_logout">退出</a></li>
        <li><a id="systemEditLink" class="text" href="javascript:void(0)">基础设置</a>
            <ul>
                <li><a class="text" href="admin.html">系统设置</a></li>
                <li><a class="text" href="admin-index-edit.html">主页设置</a></li>
                <li><a class="text" href="admin-help.html">帮助设置</a></li>
                <li><a class="text" href="admin-subscribe-config.html">订阅设置</a></li>
                <li class="footer"></li>
            </ul>
        </li>
        <li><a id="commoditySeckillLink" class="text" href="javascript:void(0)">商品&活动</a>
            <ul>
                <li><a class="text" href="admin-commodity.html">商品管理</a></li>
                <li><a class="text" href="admin-lottery-config.html">抽奖配置</a></li>
                <li><a class="text" href="admin-lottery-activity.html">抽奖管理</a></li>
                <%--<li><a class="text" href="admin-seckill-config.html">秒杀配置</a></li>--%>
                <%--<li><a class="text" href="admin-seckill-activity.html">秒杀管理</a></li>--%>
                <li><a class="text" href="admin-virtual-user.html">虚拟用户</a></li>
                <li class="footer"></li>
            </ul>
        </li>
        <li><a id="statisticLink" class="text" href="javascript:void(0)">统计分析</a>
            <ul>
                <li><a class="text" href="admin-user-list.html">用户列表</a></li>
                <li><a class="text" href="admin-mail-list.html">邮件列表</a></li>
                <li><a class="text" href="admin-system-info-list.html">消息列表</a></li>
                <li><a class="text" href="admin-batch-register.html">批量注册</a></li>
                <li class="footer"></li>
            </ul>
        </li>
    </security:authorize>
    <security:authorize ifNotGranted="ROLE_NORMAL,ROLE_ADMIN">
        <li><a class="text emphasize" id="loginNavigationLink" href="javascript:void(0)">登录</a></li>
        <li><a class="text" id="registerNavigationLink" href="javascript:void(0)">注册</a></li>
    </security:authorize>
</ul>
