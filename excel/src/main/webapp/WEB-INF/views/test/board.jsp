<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>test list 출력</title>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<style>
    table.memberList,tr,th,td {
        border: 1px solid blue;
    }


</style>
<body>
    <table class="memberList">
        <tr>
            <th>testcol1</th>
            <th>testcol2</th>
        </tr>
        <c:forEach var="test" items="${list}">
            <tr>
                <td>${test.testcol1}</td>
                <td>${test.testcol2}</td>
            </tr>
        </c:forEach>
    </table>
    <button type="button" class="excel">엑셀 다운로드</button>
</body>
    <script>
        $(".excel").on("click",()=>{
            $(location).attr('href','/member/excel');
        })
    </script>
</html>