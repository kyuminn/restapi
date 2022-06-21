package com.example.excel.member;

import com.example.excel.column.ExcelColumn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {

    @ExcelColumn(headerName = "사용자")
    private String username;

    @ExcelColumn(headerName = "이메일")
    private String email;

    @ExcelColumn(headerName = "나이")
    private int age;
}
