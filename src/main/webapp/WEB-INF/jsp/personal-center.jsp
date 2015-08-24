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
                            <span>[<a id="editPassword" href="javascript:void(0)">修改密码</a>]</span>
                        </span>
                    </div>
                    <div class="row">
                        <span class="left">姓名：</span>
                        <span class="right">
                            <c:choose>
                                <c:when test="${user.realName != null}">
                                    <span class="real-name">${user.realName}</span>
                                    <span>[<a id="editRealName" href="javascript:void(0)">修改</a>]</span>
                                </c:when>
                                <c:otherwise>
                                    [<a id="addRealName" href="javascript:void(0)">设置</a>]
                                </c:otherwise>
                            </c:choose>
                        </span>
                    </div>
                    <div class="row">
                        <span class="left">邮箱：</span>
                        <span class="right">
                            <span class="email">${user.email}</span>
                            <span>[<a id="editEmail" href="javascript:void(0)">修改</a>]</span>
                        </span>
                    </div>
                    <div class="row">
                        <span class="left">电话：</span>
                        <span class="right">
                            <c:choose>
                                <c:when test="${user.tel != null}"     >
                                    <span class="tel">${user.tel}</span>
                                    <span>[<a id="editTel" href="javascript:void(0)">修改</a>]</span>
                                </c:when>
                                <c:otherwise>
                                    [<a id="addTel" href="javascript:void(0)">设置</a>]
                                </c:otherwise>
                            </c:choose>

                        </span>
                    </div>
                    <div class="row">
                        <span class="left">性别：</span>
                    <span class="right">
                        <q:gender-select id="genderSelect" name="gender" value="${user.gender}"/>
                    </span>
                    </div>
                    <div class="row">
                        <span class="left">出生日期：</span>
                        <span class="right">
                            <span class="birthday"><q:birthday-select prefix="birthday"
                                                                      value="${user.birthday}"/></span>
                            <input type="checkbox" name="lunarBirthday"<c:if
                                    test="${user.lunarBirthday}"> checked</c:if>/>我过农历生日
                        </span>
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
            <div class="history title">登录历史</div>
            <div class="history content">
                <div style="font-size: 10pt;color:#999;">
                    以下为您最近20次登录记录，若存在异常情况，请在核实后尽快
                    <a id="editPassword2" href="javascript:void(0)">修改密码</a>
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
<div class="float-panel" id="changeEmailResult">
    验证邮件已经发送至新邮箱：
    <a target="_blank" href="javascript:void(0)" class="target-email"></a>
    <br/>您还需要通过该邮箱完成完成邮箱修改，
    <a target="_blank" href="javascript:void(0)" class="to-login">单击此处登录新邮箱</a>
    <br/><br/>未收到邮件？ <a href="javascript:void(0)" class="resend">点此重发一封</a>
    <span class="resend-success">发送成功！</span><span class="resend-fail"></span>

    <div class="submit">
        <button type="button" class="btn btn-success">关闭</button>
    </div>
</div>
<form class="float-panel" id="changeTelForm">
    <div class="input">
        <span class="title">请输入新的联系手机号码：</span>
        <input type="text" class="form-control" maxlength="11"/>
    </div>
    <div class="submit">
        <button type="submit" class="btn btn-success ok">确定</button>
        <button type="button" class="btn btn-default cancel">取消</button>
    </div>
    <div class="conflict">
        <button type="button" class="btn btn-default clear">重新输入</button>
        <a href="javascript:void(0)" target="_blank" class="validate">
            <button type="button" class="btn btn-default validate">验证</button>
        </a>
    </div>
    <div class="wait-for-validation">正在验证...</div>
    <div class="validate-error">hello</div>
</form>
<%@include file="inc-footer.jsp" %>
