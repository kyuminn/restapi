<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>member list 출력</title>
    <!--jQuery -->
    <script src="https://code.jquery.com/jquery-latest.min.js"></script>
    <!--DataTable-->
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.12.1/css/jquery.dataTables.css">
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.js"></script>
</head>
<style>
    table.memberList,tr,th,td {
        border: 1px solid blue;
    }


</style>
<body>
    <table id="memberList">
        <tr>
            <td>이름으로 검색하기 : </td>
            <td colspan="2">
                <input type="text" id="searchUsername">
                <button type="button" id="searchByUsername">조회</button>
            </td>
        </tr>
<%--이 부분 DataTables로 변경--%>
<%--        <c:forEach var="member" items="${list}">--%>
<%--            <tr>--%>
<%--                <td><a href="/member/detail/${member.email}">${member.username}</a></td>--%>
<%--                <td>${member.email}</td>--%>
<%--                <td>${member.age}</td>--%>
<%--            </tr>--%>
<%--        </c:forEach>--%>
    </table>
    <button type="button" class="excel">엑셀 다운로드</button>

    <table id="dataTable">
        <thead>
            <tr>
                <th>username</th>
                <th>email</th>
                <th>age</th>
            </tr>
        </thead>
<%--        DataTables 가 자동으로 tbody 부분 생성해줌.--%>
<%--        <tbody></tbody>--%>
    </table>
</body>
    <script>
        // 이전 방식
        // $(".excel").on("click",()=>{
        //     $(location).attr('href','/member/excel');
        // })


        $(()=>{
            search();
        });

        function search(){
            $("#dataTable").DataTable({
                destroy : true,
                serverSide : true,
                processing : true,
                ajax:{
                    "type":"post",
                    "url" : "/member/getList",
                    "dataSrc" : "", // url로 요청했을 때 받은 data 들어오는 곳, list를 바로 반환했으므로 key값을 없는것으로 초기화해줌
                    "data" : function (d){
                        d.username = $("#searchUsername").val() // 다른 데이터 옵션들과 같이 보내지기 때문에 d.변수명으로 접근.
                        return JSON.stringify(d);
                    },
                    "contentType" :"application/json",
                    "dataType" : "json",
                },
                columns : [
                    { data : 'username'}, // data.username으로 하는 듯. (map으로 반환한 경우 반환한 map의 이름이 data 여야만 함.)
                    { data : 'email'},
                    { data :'age'}
                ]
            });
        }

        $('#searchByUsername').on('click',()=>{
            search();
        });

        // enter key 옵션 추가
        $('#searchUsername').keydown((keyNum)=>{
            if(keyNum.keyCode === 13){
                search();
            }
        })

        // 구현한 것 : DataTables 연동, 조건에 따른 조회 검색 !

        // 해볼 것 : 제목 click 시 팝업에 세부 사항 표시하기 또는 detail.jsp로 이동 (a click event(. excel 다운로드 해보기.

        // 더 나아가서 : 여러 개의 parameter 로 검색 가능하고. 만약 파라미터가 다 들어가있지 않다면 쿼리문을 어떻게 짤 것인지? (if)
        //             DataTable option 공부.
    </script>
</html>