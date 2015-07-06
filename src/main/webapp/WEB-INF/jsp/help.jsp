<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="main-body">
    <div class="page-width">
        <div class="left shadow">
            <div class="title">帮助中心</div>
            <c:forEach var="helpGroup" items="${helpGroups}">
                <%@include file="help-group-title.jsp" %>
            </c:forEach>
            <c:if test="${editMode}">
                <div class="add-help-group">
                    <img class="link" title="添加分组" src="resources/css/images/add.png"/>
                </div>
            </c:if>
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
<c:if test="${editMode}">
    <div id="groupItemFormDiv">
        <q:multipart-form action="admin-add-or-edit-help-item">
            <input type="hidden" name="id"/>
            <input type="hidden" name="groupId"/>
            <table>
                <tbody>
                <tr>
                    <td class="title"><label>标题图标<span style="color: #82c92f;">(可选)</span></label></td>
                    <td class="content">
                        <jsp:include page="widget-upload-image.jsp">
                            <jsp:param name="id" value="icon"/>
                        </jsp:include>
                    </td>
                </tr>
                <tr>
                    <td class="title"><label>标题</label></td>
                    <td class="content">
                        <input type="text" class="form-control title" placeholder="在此输入标题" name="title"/>
                    </td>
                </tr>
                <tr>
                    <td class="title"><label>正文</label></td>
                    <td class="content">
                        <textarea id="helpItemContent" name="content"></textarea>
                    </td>
                </tr>
                </tbody>
            </table>
            <div class="submit">
                <button type="submit" name="submitButton" class="btn btn-success">确定</button>
                <button type="button" name="cancelButton" class="btn btn-default">取消</button>
            </div>
        </q:multipart-form>
    </div>
    <q:handlebars-template id="help-group-template">
        <%@include file="help-group-title.jsp" %>
    </q:handlebars-template>
</c:if>
<%@include file="inc-footer.jsp" %>
