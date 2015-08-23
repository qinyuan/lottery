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
                <a href="system-info.html">系统消息</a>
                <a href="lottery-history.html">抽奖历史</a>
            </div>
        </div>
        <div class="right">
            <div class="title setting">帐户设置</div>
            <div class="content setting">
                <form id="additionalInfoForm" method="post" action="personal-center-update-additional-info">
                    <div class="row">
                        <span class="left">帐号：</span>
                        <span class="right">
                            ${user.username}
                            <span id="editPassword">[<a href="javascript:void(0)">修改密码</a>]</span>
                        </span>
                    </div>
                    <div class="row">
                        <span class="left">姓名：</span>
                        <span class="right">
                            <c:choose>
                                <c:when test="${user.realName != null}">
                                    <span class="real-name">${user.realName}</span>
                                    <span id="editRealName">[<a href="javascript:void(0)">修改</a>]</span>
                                </c:when>
                                <c:otherwise>
                                    [<a id="addRealName" href="javascript:void(0)">设置</a>]
                                </c:otherwise>
                            </c:choose>
                        </span>
                    </div>
                    <div class="row">
                        <span class="left">邮箱：</span>
                        <span class="right">${user.email}</span>
                    </div>
                    <div class="row">
                        <span class="left">电话：</span>
                        <span class="right">${user.tel}</span>
                    </div>
                    <div class="row">
                        <span class="left">性别：</span>
                    <span class="right">
                        <q:gender-select id="genderSelect" name="gender" value="${user.gender}"/>
                    </span>
                    </div>
                    <div class="row">
                        <span class="left">出生日期：</span>
                        <span class="right birthday"><q:birthday-select prefix="birthday"
                                                                        value="${user.birthday}"/></span>
                    </div>
                    <div class="row">
                        <span class="left">星座：</span>
                        <span class="right"><q:constellation-select id="constellationSelect" name="constellation"
                                                                    value="${user.constellation}"/></span>
                    </div>
                    <div class="row">
                        <span class="left">家乡：</span>
                    <span class="right">
                        <input type="text" class="form-control" name="hometown" value="${user.hometown}"/>
                    </span>
                    </div>
                    <div class="row">
                        <span class="left">现居住地：</span>
                    <span class="right">
                        <input type="text" class="form-control" name="residence" value="${user.residence}"/>
                    </span>
                    </div>
                    <div class="submit">
                        <button id="submitButton" type="submit">保存</button>
                    </div>
                </form>
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
            </table>--%>
            <div class="history title">登录历史</div>
            <div class="history content">
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
            </div>
        </div>
    </div>
</div>
<form class="float-panel" id="passwordEditForm">
    <table>
        <tbody>
        <tr>
            <td>现密码：</td>
            <td><input type="password" name="oldPassword" class="form-control" maxlength="20"/></td>
        </tr>
        <tr>
            <td>新密码：</td>
            <td><input type="password" name="newPassword" class="form-control" maxlength="20"/></td>
        </tr>
        <tr>
            <td>确认新密码：</td>
            <td><input type="password" name="newPassword2" class="form-control" maxlength="20"/></td>
        </tr>
        <tr>
            <td colspan="2" class="submit">
                <button type="button" class="btn btn-success ok">确定</button>
                <button type="button" class="btn btn-default cancel">取消</button>
            </td>
        </tr>
        </tbody>
    </table>
</form>
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
</q:handlebars-template>
<q:handlebars-template id="changeEmailResultTemplate">
    <div class="change-email-result">
        验证邮件已经发送至新邮箱：
        <a target="_blank" href="{{loginPage}}">{{email}}</a>
        <br/>您还需要通过该邮箱完成完成邮箱修改，
        <a target="_blank" href="{{loginPage}}">单击此处登录新邮箱</a>
        <br/><br/>未收到邮件？ <a href="javascript:void(0)" class="resend">点此重发一封</a>
        <span class="resend-success">发送成功！</span><span class="resend-fail"></span>
    </div>
</q:handlebars-template>--%>
<%@include file="inc-footer.jsp" %>
