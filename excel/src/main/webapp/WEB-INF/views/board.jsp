<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>member list 출력</title>
</head>
<style>
    table.memberList,tr,th,td {
        border: 1px solid blue;
    }


</style>
<body>
    <table class="memberList">
        <tr>
            <th>username</th>
            <th>email</th>
            <th>age</th>
        </tr>
        <c:forEach var="member" items="${list}">
            <tr>
                <td><a href="/member/detail/${member.email}">${member.username}</a></td>
                <td>${member.email}</td>
                <td>${member.age}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>