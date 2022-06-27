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
<button type="button" class="memberList">멤버 게시판</button>
<button type="button" class="testList">test 게시판</button>
</body>

<script>
    $(".memberList").on("click",()=>{
        //jquery page 이동!
        $(location).attr("href","/member/memberList")
    })

    $(".testList").on("click",()=>{
        $(location).attr("href",'/test/list')
    })
</script>
</html>
