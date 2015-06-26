<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-taglib.jsp" %>
<div class="footer-poster">
    <div class="page-width" style="background-image: url('${footerPoster}');"></div>
</div>
<div class="footer">
    <span>${footerText}</span>
</div>
<div class="shadow" id="springLoginForm">
    <q:spring-login-form>
        <div class="title">
            <div class="image"></div>
            <div class="text">欢迎登录</div>
            <div class="close-icon"></div>
        </div>
        <div class="body">
            <div class="input">
                <label>账号</label>
                <input type="text" class="form-control" name="j_username" placeholder="手机号/用户名/邮箱"/>
            </div>
            <div class="input">
                <label>密码</label>
                <input type="text" class="form-control" name="j_password" placeholder="请输入您的密码"/>
            </div>
            <div class="rememberLogin">
                <q:spring-remember-login/>
            </div>
            <div class="submit">
                <button type="submit" name="loginSubmit">立即登录</button>
            </div>
        </div>
    </q:spring-login-form>
</div>
</body>
<q:js src="lib/jquery-1.11.1.min"/>
<q:js src="lib/jquery.url"/>
<q:js src="lib/underscore-min"/>
<q:js src="lib/jsutils"/>
<q:js src="common"/>
<%---
<script src="resources/js/lib/jquery.cookie.js"></script>
<script src="resources/js/lib/jquery-form-3.51.0.js"></script>
<!--[if IE]>
<script src="resources/js/lib/angular/html5shiv.js"></script>
<script src="resources/js/lib/angular/json2.js"></script>
<script src="resources/js/lib/ie-patch.js"></script>
<![endif]-->
--%>
<c:forEach var="js" items="${moreJs}"><q:js src="${js.href}" version="${js.version}"/></c:forEach>
<!--[if IE]>
<c:forEach var="js" items="${ieJs}"><q:js src="${js.href}" version="${js.version}"/></c:forEach>
<![endif]-->
</html>
