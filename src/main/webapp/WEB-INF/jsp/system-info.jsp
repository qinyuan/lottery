<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="gray-back">
    <div class="page-width main-body">
        <div class="left">
            <div class="image">
                <img src="resources/css/images/user.jpg"/>

                <div>qinyuan15</div>
            </div>
            <div class="link">
                <a href="personal-center.html">个人中心</a>
                <a href="activity-history.html">活动历史</a>
            </div>
        </div>
        <div class="right">
            <div class="title">系统消息</div>
            <div class="content">
                <c:forEach var="systemInfo" items="${systemInfoItems}">
                    <div>
                        <div class="icon"></div>
                        <div class="text">
                            <div class="time">${systemInfo.buildTime}</div>
                            <div class="info">${systemInfo.content}</div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <%@include file="widget-pagination.jsp"%>
            <%--
        <div>
            <h4 style="font-size:13pt;">您目前的支持(活跃度)：${livenessCount}</h4>
            <c:choose>
                <c:when test="${livenessCount>0}">
                    <div style="font-size: 10pt;color:#999;">
                        以下是您提升个人支持(活跃度)的历程
                    </div>
                    <table class="normal liveness">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>您的好友</th>
                            <th>传播途径</th>
                            <th>传播效果</th>
                            <th>增加的支持数</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="liveness" items="${livenesses}" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${liveness.receiveUsername}</td>
                                <td>${liveness.chineseSpreadWay}</td>
                                <td>${liveness.registerBefore ? '好友通过传播链接登录了网站' : '好友通过链接注册新帐号'}</td>
                                <td>${liveness.liveness}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <div style="font-size: 12pt;color:#999;">
                        您还需要通过努力传播来提高自己的支持(活跃度)
                    </div>
                </c:otherwise>
            </c:choose>
            </div>
            <div class="system-info">
                <h4>系统消息</h4>

                <div class="filter">
                    <div id="infoTypeSelector" class="switch switch-mini" data-on-label="未读" data-off-label="已读">
                        <input type="checkbox" checked/>
                    </div>
                </div>
                <div class="unread info"></div>
                <div class="read info"></div>
                --%>
        </div>
    </div>
</div>
</div>
<%--
<q:handlebars-template id="infoItemTemplate">
    {{#each items}}
    <div class="info-item" data-options="id:{{id}}">
        <div class="content">{{{content}}}</div>
        <div class="foot">
            <span class="time">{{buildTime}}</span>
            {{#if unread}}<a href="javascript:void(0)" class="mark-as-read">标记为已读</a>{{/if}}
        </div>
    </div>
    {{/each}}
</q:handlebars-template>
--%>
<%@include file="inc-footer.jsp" %>
