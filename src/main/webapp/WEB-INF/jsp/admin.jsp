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
                                <tbody>
                                <c:forEach var="indexHeaderLink" items="${indexHeaderLinks}">
                                    <jsp:include page="admin-link-table-row.jsp">
                                        <jsp:param name="title" value="${indexHeaderLink.title}"/>
                                        <jsp:param name="href" value="${indexHeaderLink.href}"/>
                                    </jsp:include>
                                </c:forEach>
                                </tbody>
                            </table>
                            <img class="link" title="添加" id="addLink" src="resources/css/images/add.png"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="edit-unit">
            <div class="title">页尾设置</div>
            <div class="content">
                <table>
                    <tbody>
                    <tr>
                        <td class="title">页尾图片</td>
                        <td class="input">
                            <jsp:include page="widget-upload-image.jsp">
                                <jsp:param name="id" value="footerPoster"/>
                                <jsp:param name="value" value="${footerPoster}"/>
                                <jsp:param name="snapshot" value="true"/>
                            </jsp:include>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">页尾文字</td>
                        <td class="input">
                            <input type="text" class="form-control" name="footerText" value="${footerText}"
                                   placeholder="在些输入页面底部的文字"/>
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
    <jsp:include page="admin-link-table-row.jsp"/>
</q:handlebars-template>
<%@include file="inc-footer.jsp" %>
