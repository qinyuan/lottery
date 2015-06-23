<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="footer-poster">
    <div class="page-width" style="background-image: url('${footerPoster}');"></div>
</div>
<div class="footer">
    <span>奇酷网@2013-2014 深圳奇虎健安智能科技有限公司版权所有 | 粤ICP备14094849号-1</span>
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
