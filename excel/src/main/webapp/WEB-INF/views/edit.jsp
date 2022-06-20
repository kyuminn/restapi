<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Member detail</title>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
<table border="1">
    <tr>
        <td>${member.username}</td>
        <td>${member.email}</td>
        <td>${member.age}</td>
    </tr>
</table>
</body>
</html>