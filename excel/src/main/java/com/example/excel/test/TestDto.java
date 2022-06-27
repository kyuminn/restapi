package com.example.excel.test;

import com.example.excel.column.HeaderColumn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestDto {

    @HeaderColumn(headerName = "testcol1")
    private String testcol1;
    @HeaderColumn(headerName = "testcol2")
    private String testcol2;
}
