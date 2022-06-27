package com.example.excel.member;

import com.example.excel.column.HeaderColumn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {

    @HeaderColumn(headerName = "사용자")
    private String username;

    @HeaderColumn(headerName = "이메일")
    private String email;

    @HeaderColumn(headerName = "나이")
    private int age;


}
