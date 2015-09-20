<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-header.jsp" %>
<%@include file="index-header.jsp" %>
<div class="page-width form">
    <div>
        <form id="addForm" method="post" action="admin-batch-register-submit.json">
            <input type="hidden" name="prevent-auto-submit"/>

            <div class="input">
                <img class="link" title="添加" src="resources/css/images/add.png"/>
            </div>
            <div class="prompt">提示：按Tab键可以增加新邮箱地址，按回车键可完成输入。</div>
            <div class="submit">
                <button type="submit" class="btn btn-success ok">确定</button>
                <button type="button" class="btn btn-default cancel">清除</button>
            </div>
        </form>
    </div>
</div>
<%@include file="inc-footer.jsp" %>
