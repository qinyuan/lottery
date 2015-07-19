<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="page-width form">
    <form action="admin-lottery-config-submit" method="post">
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
                    <span><input type="radio"
                                 <c:if test="${sinaWeiboIncludePicture}">checked</c:if>
                                 name="sinaWeiboIncludePicture" value="true">是</span>
                    <span><input type="radio"
                                 <c:if test="${!sinaWeiboIncludePicture}">checked</c:if>
                                 name="sinaWeiboIncludePicture" value="false">否</span>
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
                    <span><input type="radio"
                                 <c:if test="${qqIncludePicture}">checked</c:if> name="qqIncludePicture" value="true">是</span>
                    <span><input type="radio"
                                 <c:if test="${!qqIncludePicture}">checked</c:if> name="qqIncludePicture" value="false">否</span>
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
                    <span><input type="radio"
                                 <c:if test="${qzoneIncludePicture}">checked</c:if> name="qzoneIncludePicture"
                                 value="true">是</span>
                    <span><input type="radio"
                                 <c:if test="${!qzoneIncludePicture}">checked</c:if> name="qzoneIncludePicture"
                                 value="false">否</span>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="edit-unit">
            <div class="title">每次新抽奖机会所需爱心数设置</div>
            <div class="content">
                <table>
                    <tbody>
                    <tr>
                        <td class="title">抽奖机会所需爱心</td>
                        <td class="input">
                            <input style="width: 280px;" type="text" class="form-control" name="newLotLiveness"
                                   value="${newLotLiveness}" placeholder="在此输入每次新抽奖机会所需要的爱心数"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="edit-unit">
            <div class="title">分享给好友且好友注册或登录后增加的爱心数设置</div>
            <div class="content">
                <table>
                    <tbody>
                    <tr>
                        <td class="title">分享成功增加爱心</td>
                        <td class="input">
                            <input style="width: 280px;" type="text" class="form-control" name="shareSucceedLiveness"
                                   value="${shareSucceedLiveness}" placeholder="在此输入每次新抽奖机会所需要的爱心数"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="submit">
            <button id="submitButton" type="submit" class="btn btn-success">确定</button>
        </div>
    </form>
</div>
<%@include file="inc-footer.jsp" %>
