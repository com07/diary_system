<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actDiary" value="${ForwardConst.ACT_DIARY.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdit" value="${ForwardConst.CMD_EDIT.getValue()}" />
<c:set var="commDestroy" value="${ForwardConst.CMD_DESTROY.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <h2>id : ${diary.id} の日記情報 詳細ページ</h2>

        <table>
            <tbody>
                <tr>
                    <th>日記番号</th>
                    <td><c:out value="${diary.id}" /></td>
                </tr>
                <tr>
                    <th>氏名</th>
                    <td><c:out value="${diary.name}" /></td>
                </tr>
                <tr>
                    <th>日付</th>
                    <fmt:parseDate value="${diary.reportDate}" pattern="yyyy-MM-dd" var="reportDay" type="date" />
                    <td><fmt:formatDate value='${reportDay}' pattern='yyyy-MM-dd' /></td>

                </tr>
                <tr>
                    <th>タイトル</th>
                    <td><c:out value="${diary.title}" /></td>
                </tr>
                <tr>
                    <th>内容</th>
                    <td><c:out value="${diary.content}" /></td>
                </tr>

                <tr>
                    <th>登録日時</th>
                    <fmt:parseDate value="${diary.createdAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="createDay" type="date" />
                    <td><fmt:formatDate value="${createDay}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
                <tr>
                    <th>更新日時</th>
                    <fmt:parseDate value="${diary.updatedAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="updateDay" type="date" />
                    <td><fmt:formatDate value="${updateDay}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
            </tbody>
        </table>

        <p>
            <a href="<c:url value='?action=${actDiary}&command=${commEdit}&id=${diary.id}' />">この日記情報を編集する</a>
        </p>

        <p>
            <a href="#" onclick="confirmDestroy();">この日記を削除する</a>
        </p>
        <form method="POST"
            action="<c:url value='?action=${actDiary}&command=${commDestroy}' />">
            <input type="hidden" name="${AttributeConst.DIARY_ID.getValue()}" value="${diary.id}" />
            <input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
        </form>
        <script>
            function confirmDestroy() {
                if (confirm("本当に削除してよろしいですか？")) {
                    document.forms[0].submit();
                }
            }
        </script>

        <p>
            <a href="<c:url value='?action=${actDiary}&command=${commIdx}' />">一覧に戻る</a>
        </p>
    </c:param>
</c:import>