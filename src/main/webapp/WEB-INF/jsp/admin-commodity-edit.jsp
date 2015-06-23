<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="page-width form">
    <div>
        <q:multipart-form action="admin-commodity-edit-submit">
            <input type="hidden" name="id"/>

            <div class="form-group">
                <div class="inline">
                    <label>名称</label>
                    <input type="text" name="name" class="form-control" placeholder="在此输入商品名称"/>
                </div>
                <div class="inline">
                    <label>价格</label>
                    <input type="text" name="price" class="form-control" placeholder="在此输入商品价格"/>
                </div>
                <div class="inline">
                    <input type="checkbox" name="own" checked/>
                    <label>自有商品</label>
                </div>
            </div>
            <table>
                <tbody>
                <tr>
                    <td class="title"><label>商品缩略图(小图片)</label></td>
                    <td>
                        <jsp:include page="widget-upload-image.jsp">
                            <jsp:param name="id" value="snapshot"/>
                            <jsp:param name="snapshot" value="true"/>
                        </jsp:include>
                    </td>
                </tr>
                <tr>
                    <td class="title"><label>商品详细图(大图片)</label></td>
                    <td>
                        <jsp:include page="widget-upload-image.jsp">
                            <jsp:param name="id" value="detailImage"/>
                            <jsp:param name="snapshot" value="true"/>
                        </jsp:include>
                    </td>
                </tr>
                </tbody>
            </table>
            <div>
                <button type="submit" name="ok" class="btn btn-primary">添加</button>
                <button style="display:none;" type="button" name="cancel" class="btn btn-cancel">取消</button>
            </div>
        </q:multipart-form>
        <table class="normal">
            <tbody>
            <c:forEach var="commodity" items="${commodities}" varStatus="status">
                <tr>
                    <td class="index">${status.index + rowStartIndex}</td>
                    <td class="name">${commodity.name}</td>
                    <td class="price">${commodity.price}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <%@include file="widget-pagination.jsp" %>
    </div>
</div>
<%@include file="inc-footer.jsp" %>
