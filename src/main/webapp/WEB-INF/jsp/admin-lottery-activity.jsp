<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>

<div class="page-width form">
    <div>
        <form id="lotteryActivityForm">
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
                            <td class="title">预计结束时间</td>
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
                        <tr>
                            <td class="title">预计参加人数</td>
                            <td class="content">
                                <input type="text" name="expectParticipantCount" class="form-control"
                                       value="10000" placeholder="输入预计参数人数"/>
                            </td>
                            <td class="title"></td>
                            <td class="content">
                                <span style="color: #4EB13F">注：带“<span class="required">*</span>”的为必填项</span>
                            </td>
                        </tr>

                        </tbody>
                    </table>
                    <div style="padding-left: 35%;">
                        <button type="submit" name="ok" class="btn btn-primary">添加抽奖</button>
                        <button style="display:none;" type="button" name="cancel" class="btn btn-cancel">放弃修改</button>
                    </div>
                </c:when>
                <c:otherwise>
                    <h4>尚未添加商品，故无法添加抽奖</h4>
                </c:otherwise>
            </c:choose>
        </form>
        <table class="normal">
            <colgroup>
                <col class="index"/>
                <col class="commodity"/>
                <col class="start-time"/>
                <col class="expect-end-time"/>
                <col class="continuous-serial-limit"/>
                <col class="expect-participant-count"/>
                <col class="end-time"/>
                <col class="winners"/>
                <col class="announcement"/>
                <col class="action"/>
            </colgroup>
            <thead>
            <th>序号</th>
            <th>奖品</th>
            <th>开始时间</th>
            <th>预计结束时间</th>
            <th>抽奖号码最大连续个数</th>
            <th>预计参加人数</th>
            <th>实际结束时间</th>
            <th>中奖号</th>
            <th>中奖公告</th>
            <th></th>
            </thead>
            <tbody>
            <c:forEach var="activity" items="${activities}" varStatus="status">
                <tr data-options="id:${activity.id}">
                    <td class="index">${status.index + rowStartIndex}</td>
                    <td class="commodity"
                        data-options="commodityId: ${activity.commodity.id}">${activity.commodity.name}</td>
                    <td class="startTime">${activity.startTime}</td>
                    <td class="expectEndTime">${activity.expectEndTime}</td>
                    <td class="continuousSerialLimit">${activity.continuousSerialLimit}</td>
                    <td class="expectParticipantCount">${activity.expectParticipantCount}</td>
                    <td class="endTime">${activity.endTime}</td>
                    <td class="winners">${activity.winnerSerialNumbers}</td>
                    <td class="announcement">${activity.announcement}</td>
                    <td class="action">
                        <c:choose>
                            <c:when test="${activity.expire}">
                                <img class="link announce" title="编辑公告" src="resources/css/images/announcement.png"/>
                            </c:when>
                            <c:otherwise>
                                <img class="link stop" title="强行结束" src="resources/css/images/stop.png"/>
                                <jsp:include page="widget-edit-delete.jsp"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <%@include file="widget-pagination.jsp" %>
    </div>
</div>
<form id="announceEditForm" class="shadow">
    <input type="hidden" name="id"/>
    <table>
        <tbody>
        <tr>
            <td class="title">中奖号码</td>
            <td class="content">
                <input type="text" name="winners" class="form-control" placeholder="在此输入中奖号码，多个号码间用英文逗号分隔"/>
            </td>
        </tr>
        <tr>
            <td class="title">中奖公告</td>
            <td class="content">
                <textarea name="announcement" class="form-control" cols="35" rows="5"></textarea>
            </td>
        </tr>
        </tbody>
    </table>
    <div class="submit">
        <button type="submit" name="ok" class="btn btn-primary">保存</button>
        <button type="button" name="cancel" class="btn btn-default">取消</button>
    </div>
</form>
<%@include file="inc-footer.jsp" %>
