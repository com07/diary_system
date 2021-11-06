<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actDiary" value="${ForwardConst.ACT_DIARY.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>日記　一覧</h2>
        <table id="diary_list">
            <tbody>
                <tr>
                    <th>日記番号</th>
                    <th>氏名</th>
                    <th>日付</th>
                    <th>タイトル</th>
                    <th>日記内容</th>
                    <th>登録日時</th>
                    <th>更新日時</th>
                </tr>
                <c:forEach var="diary" items="${diaries}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td><a href="<c:url value='?action=${actDiary}&command=${commShow}&id=${diary.id}' />"><c:out value="${diary.id}" /></a></td>
                        <td><c:out value="${diary.name}" /></td>

                        <fmt:parseDate value="${diary.reportDate}" pattern="yyyy-MM-dd" var="reportDay" type="date" />
                        <td><fmt:formatDate value='${reportDay}' pattern='yyyy-MM-dd' /></td>

                        <td><c:out value="${diary.title}" /></td>
                        <td><c:out value="${diary.content}" /></td>

                        <fmt:parseDate value="${diary.createdAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="createDay" type="date" />
                        <td><fmt:formatDate value="${createDay}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                        <fmt:parseDate value="${diary.updatedAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="updateDay" type="date" />
                        <td><fmt:formatDate value="${updateDay}" pattern="yyyy-MM-dd HH:mm:ss" /></td>

                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${diaries_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((diaries_count - 1) / maxRow) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='?action=${actDiary}&command=${commIdx}&page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='?action=${actDiary}&command=${commNew}' />">新規日記の登録</a></p>

    </c:param>
</c:import>