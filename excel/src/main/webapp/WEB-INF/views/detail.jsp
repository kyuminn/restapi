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
    <br><br>
    <button type="button" class="tolist">목록으로</button>
    <button type="button" class="edit">수정하기</button>
</body>
<script>
    $(".tolist").on("click",()=>{
        $(location).attr("href","/member/list");
    });
    $(".edit").on('click',()=>{
        //popup 연습.
        //window.open("/member/list",'edit popup','width=700px,height=800px, scrollbar=yes');

        // kt function 가져다 써보기 (list- get 예시)
        // newPopWinGet("/member/list",700,800,"childForm","yes");

        // list -post 예시
        newPopWin("/member/edit?email="+${member.email},700,800,"childForm","yes");
    });

    function newPopWinGet(openUrl, cw, ch, winname, sb){
        var wd = 0;
        var ht = 0;
        var sb = typeof(sb) == "undefined" ? "no" : sb;
        var winOpt = "width="+cw+",height="+ch+"left="+wd+",top="+ht+",toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars="+sb+",resizable=yes";
        window.open(openUrl, winname, winOpt);
    }

    function newPopWin(openUrl, cw, ch, winname, sb){
        // post로 보내는 예시 : sk/hncis/technicalSupport/xts10_car_pop.gas?row_id=1&menuid=XTS06
        var wd = 0;
        var ht = 0;
        var sb = typeof(sb) == "undefined" ? "no" : sb;
        var winOpt = "width="+cw+",height="+ch+"left="+wd+",top="+ht+",toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars="+sb+",resizable=yes";

        var f ="";
        var sUrl = "";
        var arrParameters = "";

        if(openUrl.indexOf("?") > -1){
            var arrUrl = openUrl.split("?");
            sUrl = arrUrl[0];
            f = makeform(sUrl);

            arrParameters = removeTag(arrUrl[1]).split("&");
            for(var i = 0 ; i < arrParameters.length ; i++){
                var arrNameAndValue = arrParameters[i].split("=");
                f.appendChild(addData(arrNameAndValue[0], arrNameAndValue[1]));
            }

        }else{
            f = makeform(openUrl);
        }

        window.open("", winname, winOpt);
        f.setAttribute("target", winname);
        f.submit();
        document.body.removeChild(f);

    }

    // 스크립트 태그 제거
    function removeTag(str){
        var rtStr = str.replace(/</g,"&lt;").replace(/>/g,"&gt;");
        return rtStr;
    }

    function addData(name, value){
        var i = document.createElement("input");
        i.setAttribute("type", "hidden");
        i.setAttribute("name", name);
        i.setAttribute("value", value);
        return i;
    }

    function makeform(url)
    {
        var f = document.createElement("form");
        f.setAttribute("method", "post");
        f.setAttribute("action", url);
        document.body.appendChild(f);

        return f;
    }



</script>
</html>