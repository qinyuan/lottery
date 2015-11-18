<%@ page import="com.qinyuan15.lottery.mvc.AppConfig" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<security:authorize ifNotGranted="ROLE_NORMAL,ROLE_ADMIN">
    <div class="shadow float-panel" id="springLoginForm">
        <div class="title">
            <div class="text">欢迎登录</div>
            <div class="close-icon"></div>
        </div>
        <div class="body">
            <form>
                <div class="input">
                    <label>帐号</label>
                    <input type="text" class="form-control" name="j_username" placeholder="手机号/用户名/邮箱"/>
                </div>
                <div class="input">
                    <label>密码</label>
                    <input type="password" class="form-control" name="j_password" placeholder="请输入您的密码"/>
                </div>
                <div class="rememberLogin">
                    <q:spring-remember-login/><span>下次自动登录</span>
                    <a href="find-password.html" target="_blank">忘记密码?</a>
                </div>
                <div class="submit">
                    <button type="submit" name="loginSubmit">立即登录</button>
                    <a href="javascript:void(0)" id="switchToRegister">注册新帐号</a>
                </div>
                <div class="error-info">帐号或密码错误</div>
            </form>
        </div>
    </div>
    <div class="shadow float-panel" id="registerForm">
        <div class="title">
            <div class="text">欢迎注册</div>
            <div class="close-icon"></div>
        </div>
        <div class="body">
            <form action="register-submit.json" method="post">
                <div class="right">
                    <div class="input">
                        <label>邮箱</label>
                        <input type="text" class="form-control" name="email" tabindex="1"/>
                        <span class="validate"></span>
                    </div>
                    <div class="comment">
                    <span class="info">请输入您的常用邮箱，<a href="http://reg.email.163.com/unireg/call.do?cmd=register.entrance"
                                                    target="_blank">没有邮箱？</a></span>
                        <span class="error"></span>
                    </div>
                        <%--<div class="input">
                            <label>用户名</label>
                            <input type="text" class="form-control" name="username" maxlength="14" tabindex="2"/>
                            <span class="validate"></span>
                        </div>
                        <div class="comment">
                            <span class="info">2-14个字符：英文、数字或中文</span>
                            <span class="error"></span>
                        </div>
                        <div class="input">
                            <label>密码</label>
                            <input type="password" class="form-control" name="password" maxlength="20" tabindex="3"/>
                            <span class="validate"></span>
                        </div>
                        <div class="comment">
                            <span class="info">6-20个字符，区分大小写</span>
                            <span class="error"></span>
                        </div>
                        <div class="input">
                            <label>确认密码</label>
                            <input type="password" class="form-control" name="password2" maxlength="20" tabindex="4"/>
                            <span class="validate"></span>
                        </div>
                        <div class="comment">
                            <span class="info">再次输入密码</span>
                            <span class="error"></span>
                        </div>--%>
                    <div class="input identity-code">
                        <label>验证码</label>
                        <jsp:include page="widget-identity-code.jsp">
                            <jsp:param name="tabindex" value="5"/>
                        </jsp:include>
                        <span class="validate"></span>
                    </div>
                    <div class="comment">
                        <span class="info">请输入图中的字母或数字，不区分大小写</span>
                        <span class="error"></span>
                    </div>
                    <div class="submit">
                        <button type="submit" name="registerSubmit" tabindex="6">立即注册</button>
                    </div>
                </div>
                <div class="email-icon"><span class="icon"></span><span>邮箱注册</span></div>
                <div class="login-by-qq"><span id="loginByQQIcon">
                   <%--<a href="https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=101264653&redirect_uri=http%3A%2F%2Fwww.bud-vip.com%2Fregister-by-qq.html&scope=get_user_info"
                           ><img src="http://qzonestyle.gtimg.cn/qzone/vas/opensns/res/img/Connect_logo_3.png"/></a>--%>
                   <a href="<%=AppConfig.getQQConnectAuthenticateUrl()%>"><img
                           src="http://qzonestyle.gtimg.cn/qzone/vas/opensns/res/img/Connect_logo_3.png"/></a>
                </span></div>
                <div class="switch-login">已有帐号，<a id="switchToLogin" href="javascript:void(0)">立即登录</a></div>
            </form>
            <div class="result">
                <div class="text">
                    已向您的邮箱
                    <span class="email"></span>
                    发送了一封验证邮件。
                    <br/>
                    <a target="_blank">立即进入邮箱 &gt;&gt;</a>
                </div>
                <div class="exception">
                    没收到？<br/>
                    1. 看是否在邮箱的垃圾箱中<br/>
                    2. 确认没有收到 <a class="resend" href="javascript:void(0)">再次发送验证邮件 &gt;&gt;</a><span
                        class="resend-result"></span>
                </div>
            </div>
        </div>
    </div>
</security:authorize>
