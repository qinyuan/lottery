<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="page-width form">
    <form id="mainForm">
        <div class="edit-unit">
            <div class="title">QQList相关设置</div>
            <div class="content">
                <table>
                    <tbody>
                    <tr>
                        <td class="title">ID</td>
                        <td class="input">
                            <input class="form-control" name="qqlistId" value="${qqlistId}">
                        </td>
                    </tr>
                    <tr>
                        <td class="title">描述</td>
                        <td class="input">
                            <textarea id="qqlistDescription" name="qqlistDescription">${qqlistDescription}</textarea>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="submit">
            <button id="submitButton" type="button" class="btn btn-success">确定</button>
        </div>
    </form>
</div>
<%@include file="inc-footer.jsp" %>
