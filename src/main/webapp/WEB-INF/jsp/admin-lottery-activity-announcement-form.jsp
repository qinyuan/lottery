<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-taglib.jsp" %>
<form id="announceEditForm" class="shadow float-panel">
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
                <textarea name="announcement" id="announcementEditor"></textarea>
            </td>
        </tr>
        </tbody>
    </table>
    <div class="submit">
        <button type="submit" name="ok" class="btn btn-primary">保存</button>
        <button type="button" name="cancel" class="btn btn-default">取消</button>
    </div>
</form>