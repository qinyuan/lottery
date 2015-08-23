<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<security:authorize ifNotGranted="ROLE_NORMAL,ROLE_ADMIN">
    <div class="shadow float-panel" id="springLoginForm">
        <form>
            <div class="title">
                <div class="image"><img src="resources/css/images/login-title-image.png"/></div>
                <div class="text">欢迎登录</div>
                <div class="close-icon"></div>
            </div>
            <div class="body">
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
                    <a href="find-password" target="_blank">忘记密码?</a>
                </div>
                <div class="submit">
                    <button type="submit" name="loginSubmit">立即登录</button>
                    <a href="javascript:void(0)" id="switchToRegister">注册新帐号</a>
                </div>
                <div class="error-info"><!--<img src="resources/css/images/login-error.png"/>-->帐号或密码错误</div>
            </div>
        </form>
    </div>
    <div class="shadow float-panel" id="registerForm">
        <form action="register-submit.json" method="post">
            <div class="title">
                <div class="text">欢迎注册</div>
                <div class="close-icon"></div>
            </div>
            <div class="body">
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
                    <div class="input">
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
                    </div>
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
                        <button type="submit" name="loginSubmit" tabindex="6">立即注册</button>
                    </div>
                </div>
                <div class="email-icon"><span class="icon"></span><span>邮箱注册</span></div>
                <div class="switch-login">已有帐号，<a id="switchToLogin" href="javascript:void(0)">立即登录</a></div>
            </div>
        </form>
    </div>
    <div class="shadow float-panel activate-remind" id="registerSuccess">
        <div class="title">
            <div class="text">消息提示</div>
            <div class="close-icon"></div>
        </div>
        <div class="body">
            <div>验证邮件已经发送到<span class="email"></span></div>
            <div>您需要点击邮箱中的确认链接来完成</div>
            <div><a class="to-mail-page" target="_blank" href="javascript:void(0)">立即进入邮箱</a></div>
            <div>&nbsp;</div>
            <div><span class="no-reply">没有收到确认链接怎么办？</span></div>
            <div>1.看看是否在邮箱的回收站中，垃圾邮箱中</div>
            <div>2.确认没有收到，<a class="resend" href="javascript:void(0)">点此重发一封</a>
                <span class="resend-success">发送成功！</span>
                <span class="resend-fail">发送失败！</span>
            </div>
        </div>
    </div>
</security:authorize>
<security:authorize ifAnyGranted="ROLE_NORMAL,ROLE_ADMIN">
    <div class="shadow float-panel activate-remind" id="activateRemind">
        <div class="title">
            <div class="text">消息提示</div>
            <div class="close-icon"></div>
        </div>
        <div class="body">
            <div>您的邮箱<span class="email"></span>尚未完成验证</div>
            <div>您需要点击邮箱中的确认链接来完成</div>
            <div><a class="to-mail-page" target="_blank" href="javascript:void(0)">立即进入邮箱</a></div>
            <div>&nbsp;</div>
            <div><span class="no-reply">没有收到确认链接怎么办？</span></div>
            <div>1.看看是否在邮箱的回收站中，垃圾邮箱中</div>
            <div>2.确认没有收到，<a class="resend" href="javascript:void(0)">点此重发一封</a>
                <span class="resend-success">发送成功！</span>
                <span class="resend-fail">发送失败！</span>
            </div>
        </div>
    </div>
    <c:if test="${unactivatedEmail != null}">
        <script>var unactivatedEmail = "${unactivatedEmail}";</script>
    </c:if>
</security:authorize>
