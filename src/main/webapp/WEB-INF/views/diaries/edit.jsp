<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="action" value="${ForwardConst.ACT_DIARY.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commUpdate" value="${ForwardConst.CMD_UPDATE.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>id: <c:out value="${diary.id}" /> の日記　編集ページ</h2>

        <form method="POST" action="<c:url value='?action=${action}&command=${commUpdate}' />">
            <input type="hidden" name="id" value="${diary.id}">
            <c:import url="_form.jsp" />
        </form>

        <p><a href="<c:url value='?action=${action}&command=${commShow}&id=${diary.id}' />">詳細ページに戻る</a></p>
    </c:param>
</c:import>