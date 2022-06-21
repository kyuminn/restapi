package com.example.excel;

import com.example.excel.column.ExcelColumn;
import com.example.excel.member.MemberDto;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Field;
import java.util.List;

@Controller
public class ExcelController {

    @PostMapping("/excel/download")
    public void excelDownload(@RequestBody List<?> list, SXSSFWorkbook workbook){

        Sheet sheet = workbook.createSheet();

        int rowIndex = 0;
        Row headerRow = sheet.createRow(rowIndex++);

        Field[] declaredFields = new MemberDto().getClass().getDeclaredFields();
        for(int i=0 ; i<declaredFields.length; i++){
            Cell cell = headerRow.createCell(i);
            String headerName = declaredFields[i].getDeclaredAnnotation(ExcelColumn.class).headerName();
            cell.setCellValue(headerName);
        }
    }
}
