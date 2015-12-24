<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="main-body">
    <div class="page-width shadow">
        <div class="title">找回密码</div>

        <form id="usernameInputForm">
            <table>
                <tbody>
                <tr>
                    <td class="title">您的帐号：</td>
                    <td class="input">
                        <input type="text" class="form-control username" name="resetPasswordUsername"
                               placeholder="手机号/登录邮箱/用户名"/>
                    </td>
                </tr>
                <tr>
                    <td class="title">验证码：</td>
                    <td class="input">
                        <jsp:include page="widget-identity-code.jsp">
                            <jsp:param name="loadImage" value="true"/>
                            <jsp:param name="placeholder" value="请输入图片中的数字或字母"/>
                            <jsp:param name="refreshImage" value="true"/>
                        </jsp:include>
                    </td>
                </tr>
                <tr>
                    <td class="title"></td>
                    <td class="input">
                        <button type="button" class="btn btn-success btn-lg" id="usernameInputSubmit">下一步</button>
                    </td>
                </tr>
                </tbody>
            </table>
        </form>
        <div class="reset-password-result">
            <div>您正在找回密码的帐号为：<span class="mail"></span></div>
            <div>设置新密码的链接已经发送至您的邮箱：<span class="mail" style="font-size: 16pt;"></span></div>
            <div style="color: #999;">请您在24小时内登录邮箱，点击邮件内链接为帐号设置新密码</div>
            <div>
                <a href="javascript:void(0)" target="_blank" class="btn btn-success btn-lg toLoginPage">去邮箱收信</a>
            </div>
            <div style="font-size: 10pt;margin-top: 20px;">
                <div style="font-weight: bold;">没有收到确认链接怎么办？</div>

                <div>1.看看是否在邮箱的回收站中，垃圾邮箱中</div>
                <div>2.确认没有收到，<a class="resend" href="javascript:void(0)">点此重发一封</a>
                    <span class="resend-success">发送成功！</span>
                    <span class="resend-fail">发送失败！</span>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="inc-footer.jsp" %>
