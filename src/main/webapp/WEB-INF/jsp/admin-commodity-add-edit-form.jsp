<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<q:multipart-form id="commodityForm" cssClass="float-panel" action="admin-commodity-submit">
    <input type="hidden" name="id"/>
    <input type="hidden" name="pageNumber"/>
    <table>
        <tbody>
        <tr>
            <td class="content">
                <div class="basic">
                    <span class="title">名称</span>
                    <input type="text" name="name" class="form-control" placeholder="在此输入商品名称"/>
                </div>
                <div class="basic">
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
            <td class="buttons" colspan="2">
                <button type="button" class="btn btn-primary ok">确定</button>
                <button type="button" class="btn btn-cancel cancel">取消</button>
            </td>
        </tr>
        </tbody>
    </table>
</q:multipart-form>
