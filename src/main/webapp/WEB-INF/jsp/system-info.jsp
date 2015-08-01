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
                <a href="lottery-history.html">抽奖历史</a>
            </div>
        </div>
        <div class="right">
            <div class="login-history">
                <h4>您目前的爱心(活跃度)：${livenessCount}</h4>

                <c:choose>
                    <c:when test="${livenessCount>0}">
                        <div style="font-size: 10pt;color:#999;">
                            以下是您提升个人爱心(活跃度)的历程
                        </div>
                        <table class="normal liveness">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>您的好友</th>
                                <th>传播途径</th>
                                <th>传播效果</th>
                                <th>增加的爱心数</th>
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
                            您还需要通过努力传播来提高自己的爱心(活跃度)
                        </div>
                    </c:otherwise>
                </c:choose>


            </div>

            <%--
            <table class="user-info">
                <tbody>
                <tr>
                    <td><span class="user-icon">&nbsp;</span>用户昵称：${user.username}</td>
                    <td>
                        <a href="javascript:void(0)" id="changePassword">
                            <span class="password-icon">&nbsp;</span>修改密码
                        </a>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <span class="email-icon">&nbsp;</span>Email地址：<span class="email">${user.email}</span>
                        <a href="javascript:void(0)" id="changeEmail">修改</a>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <span class="tel-icon">&nbsp;</span>手机号码：<span
                            class="tel">${(user.tel == null || fn:length(user.tel) == 0) ? '(未设置)' : user.tel}</span>
                        <a href="javascript:void(0)" id="changeTel">修改</a>
                    </td>
                </tr>
                <tr>
                    <td colspan="2"><span class="liveness-icon">&nbsp;</span>爱心(活跃度)：${liveness}</td>
                </tr>
                </tbody>
            </table>
            <div class="login-history">
                <h4>登录历史</h4>

                <div style="font-size: 10pt;color:#999;">
                    以下为您最近20次登录记录，若存在异常情况，请在核实后尽快
                    <a id="changePassword2" href="javascript:void(0)">修改密码</a>
                </div>
                <table class="normal">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>登录时间</th>
                        <th>登录地点</th>
                        <th>IP</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="loginRecord" items="${loginRecords}" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td>${loginRecord.loginTime}</td>
                            <td>${loginRecord.location}</td>
                            <td>${loginRecord.ip}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>--%>
        </div>
    </div>
</div>
<%--
<q:handlebars-template id="changePasswordTemplate">
    <tr class="change-password temporary">
        <td colspan="2">
            <form>
                <div>
                    <div class="title">现密码：</div>
                    <div class="input"><input type="password" name="oldPassword" class="form-control"
                                              placeholder="输入您现在的密码" maxlength="20"/></div>
                </div>
                <div>
                    <div class="title">新密码：</div>
                    <div class="input"><input type="password" name="newPassword" class="form-control"
                                              placeholder="输入您的新密码" maxlength="20"/></div>
                </div>
                <div>
                    <div class="title">确认新密码：</div>
                    <div class="input"><input type="password" name="newPassword2" class="form-control"
                                              placeholder="输入您的新密码" maxlength="20"/></div>
                </div>
                <div class="submit">
                    <button type="button" class="btn btn-success" id="changePasswordSubmit" name="ok">确定</button>
                    <button type="button" class="btn btn-default" name="cancel">取消</button>
                </div>
            </form>
        </td>
    </tr>
</q:handlebars-template>
<q:handlebars-template id="changeTelTemplate">
    <tr class="change-tel temporary">
        <td colspan="2">
            <form>
                <div>
                    <div class="title">新手机号码：</div>
                    <div class="input"><input type="text" name="tel" class="form-control"
                                              placeholder="输入您的新手机号码" maxlength="11"/></div>
                </div>
                <div class="submit">
                    <button type="submit" class="btn btn-success" id="changeTelSubmit" name="ok">确定</button>
                    <button type="button" class="btn btn-default" name="cancel">取消</button>
                </div>
            </form>
        </td>
    </tr>
</q:handlebars-template>
<q:handlebars-template id="changeEmailTemplate">
    <tr class="change-email temporary">
        <td colspan="2">
            <form>
                <div>
                    <div class="title">新邮箱地址：</div>
                    <div class="input"><input type="text" name="email" class="form-control"
                                              placeholder="输入您的新邮箱地址"/></div>
                </div>
                <div class="submit">
                    <button type="submit" class="btn btn-success" id="changeEmailSubmit" name="ok">确定</button>
                    <button type="button" class="btn btn-default" name="cancel">取消</button>
                </div>
            </form>
        </td>
    </tr>
</q:handlebars-template>--%>
<%@include file="inc-footer.jsp" %>
