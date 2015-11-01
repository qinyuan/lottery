<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<q:multipart-form id="commodityImageForm" cssClass="float-panel" action="admin-commodity-image-submit">
    <q:hidden name="id"/>
    <q:hidden name="commodityId"/>
    <q:hidden name="pageNumber"/>
    <table>
        <tbody>
        <tr>
            <td class="content">
                <div class="detail">
                    <span class="title">前景图</span>

                    <div class="image">
                        <jsp:include page="widget-upload-image.jsp">
                            <jsp:param name="id" value="detailImage"/>
                            <jsp:param name="detailImage" value="true"/>
                        </jsp:include>
                    </div>
                </div>
                <div class="back">
                    <span class="title">背景图</span>

                    <div class="image">
                        <jsp:include page="widget-upload-image.jsp">
                            <jsp:param name="id" value="backImage"/>
                            <jsp:param name="backImage" value="true"/>
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
