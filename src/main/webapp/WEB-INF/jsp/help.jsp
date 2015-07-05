<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="main-body">
    <div class="page-width">
        <div class="left shadow">
            <div class="title">帮助中心</div>
            <!--
            <div class="help-list">
                <div class="title"><span class="icon"></span><span class="content">厂商合作</span></div>
                <ul>
                    <li><a href="#">什么是360商城</a></li>
                    <li class="selected"><a href="#">什么是360商城</a></li>
                    <li><a href="#">什么是360商城</a></li>
                    <li><a href="#">什么是360商城</a></li>
                    <li><a href="#">什么是360商城</a></li>
                    <li><a href="#">什么是360商城</a></li>
                </ul>
            </div>
            -->
            <c:forEach var="helpGroup" items="${helpGroups}">
                <%@include file="help-group-title.jsp" %>
            </c:forEach>
            <security:authorize ifAnyGranted="ROLE_ADMIN">
                <div class="add-help-group">
                    <img class="link" title="添加分组" src="resources/css/images/add.png"/>
                </div>
            </security:authorize>
        </div>
        <div class="right shadow">
            <div class="title"><h3>厂商合作</h3></div>
            <div class="item">
                <div class="title"><strong>什么是360商城</strong></div>
                <div class="content">
                    360商城是中国最大智能硬件体验平台。这里有免费试用智能硬件的机会、最真实的试用报告，以及全方位的专业评测。同时，用户还有机会预约和抢购并体验到最新最酷的智能产品。
                </div>
            </div>
            <div class="item">
                <div class="title"><strong>什么是360商城</strong></div>
                <div class="content">
                    360商城是中国最大智能硬件体验平台。这里有免费试用智能硬件的机会、最真实的试用报告，以及全方位的专业评测。同时，用户还有机会预约和抢购并体验到最新最酷的智能产品。
                </div>
            </div>
        </div>
    </div>
</div>
<div id="groupItemFormDiv">
    <q:multipart-form action="admin-add-help-item">
        <input type="hidden" name="id"/>
        <input type="hidden" name="groupId"/>
        <table>
            <col class="title"/>
            <col class="content"/>
            <tbody>
            <tr>
                <td>标题图标</td>
                <td>
                    <jsp:include page="widget-upload-image.jsp">
                        <jsp:param name="id" value="icon"/>
                    </jsp:include>
                </td>
            </tr>
            <tr>
                <td>标题</td>
                <td>
                    <input type="text" class="form-control" name="title"/>
                </td>
            </tr>
            <tr>
                <td>正文</td>
                <td>
                    <textarea class="ckeditor" name="title"></textarea>
                </td>
            </tr>
            </tbody>
        </table>

    </q:multipart-form>
</div>
<q:handlebars-template id="help-group-template">
    <%@include file="help-group-title.jsp" %>
</q:handlebars-template>
<%@include file="inc-footer.jsp" %>
