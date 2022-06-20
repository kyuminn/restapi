<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hello ~</title>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
Hello page 입니당.
<br><br>
<button type="button" class="list">멤버 게시판</button>
</body>

<script>
    $(".list").on("click",()=>{
        //jquery page 이동!
        $(location).attr("href","/member/list")
    })
</script>
</html>
