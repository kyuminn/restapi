<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Member</title>
    <!--jquery-->
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
    <form action="/member" method="post">
    이름: <input type="text" name="username" id="username"> <br>
    이메일 : <input type="text" name="email" id="email"> <br>
    나이 : <input type="number" name="age" id="age"> <br>
        <input type="submit" value="전송">
        <button type="button" class="popup">팝업 예제</button>
        <button type="button" class="ajaxReq">ajax 예제</button>
        <!--button type의 default 는 submit이라서 type을 명시해주지 않으면 페이지가 reload 된다-->
        <!-- submit을 하면 자동으로 페이지가 reload 되므로, 페이지의 일부분만을 바꿀 때는 ajax로 데이터를 주고받아야 한다.-->
        <button type="button" class="tolist">목록으로</button>
    </form>

    <div id="out">입력한 이름 출력되는 곳</div>
</body>

<script>
    // $("#username").val("tester");
    $(".popup").on("click",()=>{

        const username = $("#username").val();
        const email = $("#email").val();

        const data = {
            username, //data.username 으로 접근할수 있음 (js의 객체 타입)
            email
        }
        // js의 객체 타입(Object object)은 javascript의 내장함수인 JSON.stringify()로 JSON포맷 문자열로 바꿀 수 있다.
        // javascript에서 객체 타입은 그냥 {}로 알면 될 듯.
        alert(JSON.stringify(data));
        //alert(data.username);
    });

    $(".ajaxReq").on("click",()=>{
        const username = $("#username").val();
        const email = $("#email").val();
        const age = $("#age").val();

        const data = {
            username,
            email,
            age
        };

        const sendData = JSON.stringify(data);
        // ajax post 로 data를 보낼 때 json 포맷으로 보내면 controller에서 @RequestBody로 받을 수 있음.
        $.ajax({
            type: "post",
            contentType: "application/json",
            url : "/member/json",
            data : sendData,
            dataType : "json",
            timeout : 10000,
            success : (data)=>{ //controller에서 return한 memberDto가 온다.
                alert("이름:"+data.username+"이메일:"+data.email+"나이:"+data.age);
            },
            error :(e)=>{
                alert("실패!");
            }
        })
    });

    $(".tolist").on('click',()=>{
        $(location).attr("href",'/member/list');
    });

    // 입력한 값 실시간 출력 (명함에서 활용함)
    // jquery $(document.ready(function()){}) === $(function()) 임.
    // html dom이 올라오면 바로 실행되는 함수

    // jquery text() : getter, setter 함수 통합본.
    $(()=>{
        $('#username').keyup(()=>{
            let value = $('#username').val();
            $('#out').text(value);
        })
    })

</script>
</html>