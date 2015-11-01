<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<q:multipart-form id="commodityForm" cssClass="float-panel" action="admin-commodity-submit">
    <input type="hidden" name="id"/>
    <input type="hidden" name="pageNumber"/>
    <%--<div class="form-group">
        <div class="inline">
            <label>名称</label>
            <input type="text" name="name" class="form-control" placeholder="在此输入商品名称"/>
        </div>
        <div class="inline">
            <label>价格</label>
            <input type="text" name="price" class="form-control" placeholder="在此输入商品价格"/>
        </div>
    </div>
    <table>
        <tbody>
        <tr>
            <td class="title"><label>缩略图</label></td>
            <td>
                <jsp:include page="widget-upload-image.jsp">
                    <jsp:param name="id" value="snapshot"/>
                    <jsp:param name="snapshot" value="true"/>
                </jsp:include>
            </td>
        </tr>
        <tr>
            <td class="title"><label>详细图</label></td>
            <td>
                <jsp:include page="widget-upload-image.jsp">
                    <jsp:param name="id" value="detailImage"/>
                    <jsp:param name="snapshot" value="true"/>
                </jsp:include>
            </td>
        </tr>
        <tr>
            <td class="title"><label>背景图</label></td>
            <td>
                <jsp:include page="widget-upload-image.jsp">
                    <jsp:param name="id" value="backImage"/>
                    <jsp:param name="snapshot" value="true"/>
                </jsp:include>
            </td>
        </tr>
        </tbody>
    </table>--%>
    <table>
        <tbody>
        <tr>
            <td class="title">基本属性</td>
            <td class="content">
                <div class="basic">
                    <span class="title">名称</span>
                    <input type="text" name="name" class="form-control" placeholder="在此输入商品名称"/>
                    <span class="title">价格</span>
                    <input type="text" name="price" class="form-control" placeholder="在此输入商品价格"/>
                </div>
                <div class="snapshot">
                    <span class="title">缩略图</span>

                    <div class="image">
                        <jsp:include page="widget-upload-image.jsp">
                            <jsp:param name="id" value="snapshot"/>
                            <jsp:param name="snapshot" value="true"/>
                        </jsp:include>
                    </div>
                </div>
            </td>
        </tr>
        <tr>
            <td class="title">详细图片</td>
            <td class="content detail-image">
                <button type="button" class="add-detail btn btn-info btn-xs">添加</button>
            </td>
        </tr>
        <tr>
            <td class="buttons" colspan="2">
                <button type="button" class="btn btn-primary ok">确定</button>
                <button type="button" class="btn btn-cancel cancel">取消</button>
            </td>
        </tr>
        </tbody>
    </table>
</q:multipart-form>
<q:handlebars-template id="detailImageTemplate">
    <div>
        <div class="front">
            <span class="title">详细图</span>

            <div class="image">
                <jsp:include page="widget-upload-image.jsp">
                    <jsp:param name="id" value="detailImages"/>
                    <jsp:param name="snapshot" value="true"/>
                </jsp:include>
            </div>
        </div>
        <div class="back">
            <span class="title">背景图</span>

            <div class="image">
                <jsp:include page="widget-upload-image.jsp">
                    <jsp:param name="id" value="backImages"/>
                    <jsp:param name="snapshot" value="true"/>
                </jsp:include>
            </div>
        </div>
        <div class="delete"><img class="link" title="删除" src="resources/css/images/close.gif"/></div>
    </div>
</q:handlebars-template>
