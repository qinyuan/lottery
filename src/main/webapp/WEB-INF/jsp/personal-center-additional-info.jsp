<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="inc-taglib.jsp" %>
<div class="row">
    <span class="left">性别：</span>
    <span class="right">
        <q:gender-select id="genderSelect" name="gender" value="${user.gender}"/>
    </span>
</div>
<div class="row">
    <span class="left">出生日期：</span>
    <span class="right">
        <span class="birthday"><q:birthday-select prefix="birthday" value="${user.birthday}"/></span>
        <input type="checkbox" name="lunarBirthday" tabindex="-1" <c:if test="${user.lunarBirthday}"> checked</c:if>/>我过农历生日
    </span>
</div>
<div class="row">
    <span class="left">星座：</span>
    <span class="right">
        <q:constellation-select id="constellationSelect" name="constellation" value="${user.constellation}"/>
    </span>
</div>
<div class="row">
    <span class="left">家乡：</span>
    <span class="right">
        <input type="text" class="form-control" name="hometown" value="${user.hometown}"/>
    </span>
</div>
<div class="row">
    <span class="left">现居住地：</span>
    <span class="right">
        <input type="text" class="form-control" name="residence" value="${user.residence}"/>
    </span>
</div>
