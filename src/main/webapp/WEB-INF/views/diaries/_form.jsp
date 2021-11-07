<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="action" value="${ForwardConst.ACT_DIARY.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />

<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>
<label for="${AttributeConst.DIARY_NAME.getValue()}">名前</label><br />
<input type="text" name="${AttributeConst.DIARY_NAME.getValue()}" value="${diary.name}" id="${AttributeConst.DIARY_NAME.getValue()}" />
<br /><br />

<fmt:parseDate value="${diary.reportDate}" pattern="yyyy-MM-dd" var="reportDay" type="date" />
<label for="${AttributeConst.DIARY_REP_DATE.getValue()}">日付</label><br />
<input type="date" name="${AttributeConst.DIARY_REP_DATE.getValue()}" value="<fmt:formatDate value='${reportDay}' pattern='yyyy-MM-dd' />" id="${AttributeConst.DIARY_REP_DATE.getValue()}"/>
<br /><br />

<label for="${AttributeConst.DIARY_TITLE.getValue()}">タイトル</label><br />
<input type="text" name="${AttributeConst.DIARY_TITLE.getValue()}" value="${diary.title}" id="${AttributeConst.DIARY_TITLE.getValue()}" />
<br /><br />

<label for="${AttributeConst.DIARY_CONTENT.getValue()}">内容</label><br />
<textarea name="${AttributeConst.DIARY_CONTENT.getValue()}" rows="10" cols="50">${diary.content}</textarea>
<br /><br />


<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">投稿</button>