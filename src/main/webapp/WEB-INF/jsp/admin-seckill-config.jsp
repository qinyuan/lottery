<%@ page import="com.qinyuan15.lottery.mvc.activity.SeckillAnnouncementPlaceholderConverter" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="page-width form">
    <q:multipart-form action="admin-seckill-config-submit">
        <div class="edit-unit">
            <div class="title">新浪微博分享链接设置</div>
            <div class="content">
                <table>
                    <tbody>
                    <tr>
                        <td class="title">文字内容</td>
                        <td class="input">
                            <input type="text" class="form-control" name="sinaWeiboTitle" value="${sinaWeiboTitle}"
                                   placeholder="在此输入分享文字"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">是否分享图片</td>
                        <td class="input boolean">
                    <span><input type="radio" name="sinaWeiboIncludePicture" value="true"
                                 <c:if test="${sinaWeiboIncludePicture}">checked</c:if>>是</span>
                    <span><input type="radio" name="sinaWeiboIncludePicture" value="false"
                                 <c:if test="${!sinaWeiboIncludePicture}">checked</c:if>>否</span>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="edit-unit">
            <div class="title">QQ分享链接设置</div>
            <div class="content">
                <table>
                    <tbody>
                    <tr>
                        <td class="title">标题</td>
                        <td class="input">
                            <input type="text" class="form-control" name="qqTitle" value="${qqTitle}"
                                   placeholder="在此输入标题"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">详细描述</td>
                        <td class="input">
                            <input type="text" class="form-control" name="qqSummary" value="${qqSummary}"
                                   placeholder="在此输入详细描述"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">是否分享图片</td>
                        <td class="input boolean">
                    <span><input type="radio" name="qqIncludePicture" value="true"
                                 <c:if test="${qqIncludePicture}">checked</c:if>>是</span>
                    <span><input type="radio" name="qqIncludePicture" value="false"
                                 <c:if test="${!qqIncludePicture}">checked</c:if>>否</span>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="edit-unit">
            <div class="title">QQ空间分享链接设置</div>
            <div class="content">
                <table>
                    <tbody>
                    <tr>
                        <td class="title">标题</td>
                        <td class="input">
                            <input type="text" class="form-control" name="qzoneTitle" value="${qzoneTitle}"
                                   placeholder="在此输入标题"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">详细描述</td>
                        <td class="input">
                            <input type="text" class="form-control" name="qzoneSummary" value="${qzoneSummary}"
                                   placeholder="在此输入详细描述"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">是否分享图片</td>
                        <td class="input boolean">
                            <span><input type="radio" name="qzoneIncludePicture" value="true"
                                         <c:if test="${qzoneIncludePicture}">checked</c:if>>是</span>
                            <span><input type="radio" name="qzoneIncludePicture" value="false"
                                         <c:if test="${!qzoneIncludePicture}">checked</c:if>>否</span>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="edit-unit">
            <div class="title">秒杀扑克牌设置</div>
            <div class="content">
                <table>
                    <tbody>
                    <tr>
                        <td class="title">图案1</td>
                        <td class="input">
                            <jsp:include page="widget-upload-image.jsp">
                                <jsp:param name="id" value="pokerFrontSide"/>
                                <jsp:param name="value" value="${pokerFrontSide}"/>
                                <jsp:param name="snapshot" value="true"/>
                            </jsp:include>
                        </td>
                    </tr>
                    <tr>
                        <td class="title">图案2</td>
                        <td class="input">
                            <jsp:include page="widget-upload-image.jsp">
                                <jsp:param name="id" value="pokerBackSide"/>
                                <jsp:param name="value" value="${pokerBackSide}"/>
                                <jsp:param name="snapshot" value="true"/>
                            </jsp:include>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="edit-unit">
            <div class="title">其他设置</div>
            <div class="content">
                <table>
                    <tbody>
                    <tr>
                        <td class="title">抽奖结果公告
                            <div class="comment">
                                注：<br/>
                                <%=SeckillAnnouncementPlaceholderConverter.WINNERS%>指代获奖者<br/>
                                <%=SeckillAnnouncementPlaceholderConverter.PARTICIPANT_COUNT%>指代总参与人数
                            </div>
                        </td>
                        <td class="input">
                            <textarea name="seckillAnnouncementTemplate"
                                      class="ckeditor">${seckillAnnouncementTemplate}</textarea>
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

<%@include file="inc-footer.jsp" %>
