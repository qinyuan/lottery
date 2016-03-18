<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="main-body">
    <div class="page-width">
        <div class="left shadow">
            <div class="title">订阅活动及帮助中心</div>
            <c:if test="${editMode}"><div class="action-prompt">注：将鼠标移动到对应项目即出现编辑按钮</div></c:if>
            <c:forEach var="helpGroup" items="${helpGroups}">
                <%@include file="help-group-title.jsp" %>
            </c:forEach>
            <c:if test="${editMode}">
                <div class="add-help-group">
                    <img class="link" title="添加分组" src="resources/css/images/add.png"/>
                </div>
            </c:if>
        </div>
        <div class="right shadow"></div>
        <q:handlebars-template id="help-group-content-template">
            <div class="title"><h3>{{groupTitle}}</h3></div>
            {{#each helpItems}}
            <div class="item" id="d{{id}}">
                <div class="title">
                    {{#if icon}}<img src="{{icon}}"/>{{/if}}
                    <strong>{{title}}</strong>
                    <c:if test="${editMode}">
                        <a class="link deepTransparent" onclick="editHelpItemByRightDiv(this);"
                           href="javascript:void(0)">编辑</a>
                    </c:if>
                </div>
                <div class="content">{{{content}}}</div>
            </div>
            {{/each}}
        </q:handlebars-template>
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
