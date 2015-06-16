<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="page-width form">
    <q:multipart-form action="admin-submit">
        <div class="edit-unit">
            <div class="title">主页页头设置</div>
            <div class="content">
                <table>
                    <tbody>
                    <tr>
                        <td class="title">左图标</td>
                        <td class="input">
                            <jsp:include page="widget-upload-image.jsp">
                                <jsp:param name="id" value="indexHeaderLeftLogo"/>
                                <jsp:param name="value" value="${indexHeaderLeftLogo}"/>
                                <jsp:param name="snapshot" value="true"/>
                            </jsp:include>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">右图标</td>
                        <td class="input">
                            <jsp:include page="widget-upload-image.jsp">
                                <jsp:param name="id" value="indexHeaderRightLogo"/>
                                <jsp:param name="value" value="${indexHeaderRightLogo}"/>
                                <jsp:param name="snapshot" value="true"/>
                            </jsp:include>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">右侧宣传图片</td>
                        <td class="input">
                            <jsp:include page="widget-upload-image.jsp">
                                <jsp:param name="id" value="indexHeaderSlogan"/>
                                <jsp:param name="value" value="${indexHeaderSlogan}"/>
                                <jsp:param name="snapshot" value="true"/>
                            </jsp:include>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">链接</td>
                        <td class="input">
                            <table id="linkTable">
                                <colgroup>
                                    <col class="text"/>
                                    <col class="target"/>
                                    <col class="action"/>
                                </colgroup>
                                <thead>
                                <tr>
                                    <th>内容</th>
                                    <th>链接目标</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                            </table>
                            <img class="link" id="addLink" src="resources/css/images/add.png"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="submit">
            <button id="submitButton" type="submit" class="btn btn-success">确定</button>
        </div>
    </q:multipart-form>
</div>
<q:handlebars-template id="link-template">
    <tr>
        <td><input type="type" name="headerLinkText" class="form-control"/></td>
        <td><input type="type" name="headerLinkTarget" class="form-control"/></td>
        <td>
            <img onclick="return rankUpLink(this);" src="resources/css/images/arrow_up.png"/>
            <img onclick="return rankDownLink(this);" src="resources/css/images/arrow_down.png"/>
            <img onclick="return deleteLink(this);" src="resources/css/images/delete.png"/>
        </td>
    </tr>
</q:handlebars-template>
<%@include file="inc-footer.jsp" %>
