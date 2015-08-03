<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="main-body">
    <div class="shadow page-width">
        <h3>重置密码</h3>
        <c:choose>
            <c:when test="${serialKey == null}">
                <div class="url-expire">
                    <h1>链接已失效！</h1>
                    <a href="find-password.html"><span id="remainingRelocateSeconds">5</span>秒后自动跳转至找回密码页面</a>
                </div>
            </c:when>
            <c:otherwise>
                <form id="passwordInputForm">
                    <input type="hidden" name="serialKey" value="${serialKey}"/>
                    <table>
                        <tr>
                            <td>输入新密码：</td>
                            <td><input type="password" class="form-control" name="password" maxlength="20"
                                       placeholder="输入6-20个字符的密码，区分大小写"/></td>
                        </tr>
                        <tr>
                            <td>确认新密码：</td>
                            <td>
                                <input type="password" class="form-control" name="password2" maxlength="20"
                                       placeholder="再次输入密码"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2" style="text-align: center;">
                                <button type="button" id="resetPasswordSubmit" class="btn btn-success">更改密码</button>
                            </td>
                        </tr>
                    </table>
                </form>
                <div class="reset-password-result">
                    <h1>密码修改成功！</h1>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<%@include file="inc-footer.jsp" %>
