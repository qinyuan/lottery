<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form id="lotteryActivityForm" class="float-panel shadow">
    <c:choose>
        <c:when test="${fn:length(allCommodities)>0}">
            <input type="hidden" name="id"/>
            <table>
                <tbody>
                <tr>
                    <td class="title">奖品<span class="required">*</span></td>
                    <td class="input">
                        <%@include file="commodity-select-form.jsp" %>
                    </td>
                    <td class="title">开始时间<span class="required">*</span></td>
                    <td class="content" style="width: 300px;">
                        <input type="text" name="startTime" class="form-control"
                               placeholder="格式如'2015-01-01 09:00:00'" disabled/>
                        <span style="padding-left: 10px;">
                            <input type="checkbox" name="autoStartTime" checked>自动生成
                        </span>
                    </td>
                </tr>
                <tr>
                    <td class="title">预计结束时间<span class="required">*</span></td>
                    <td class="content">
                        <input type="text" name="expectEndTime" class="form-control"
                               placeholder="格式如'2015-03-03 19:00:00'"/>
                    </td>
                    <td class="title">抽奖号最大连续个数</td>
                    <td class="content">
                        <input type="text" name="continuousSerialLimit" class="form-control"
                               value="5" placeholder="输入抽奖号最大连续个数"/>
                    </td>
                </tr>
                <tr class="liveness">
                    <td class="title">最大爱心数</td>
                    <td class="content">
                        <input type="text" name="virtualLiveness" class="form-control"
                               placeholder="输入最大爱心数，格式为数字"/>
                    </td>
                    <td class="title">最大爱心用户</td>
                    <td class="content">
                        <input type="text" name="virtualLivenessUsers" class="form-control"
                               placeholder="若输入多个用户，以“,”作分隔"/>
                    </td>
                </tr>
                <tr>
                    <td class="title">预计参加人数</td>
                    <td class="content">
                        <input type="text" name="expectParticipantCount" class="form-control"
                               value="10000" placeholder="输入预计参数人数"/>
                    </td>
                </tr>
                </tbody>
            </table>
            <div class="submit">
                <button type="submit" name="ok" class="btn btn-primary">确定</button>
                <button type="button" name="cancel" class="btn btn-cancel">取消</button>
                <span style="color: #4EB13F">注：带“<span class="required">*</span>”的为必填项</span>
            </div>
        </c:when>
        <c:otherwise>
            <h4>尚未添加商品，故无法添加抽奖</h4>
        </c:otherwise>
    </c:choose>
</form>
<%@include file="inc-taglib.jsp" %>