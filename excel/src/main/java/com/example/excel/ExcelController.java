package com.example.excel;

import com.example.excel.column.HeaderColumn;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class ExcelController {

    @PostMapping("/excel/download")
    public void excelDownload(@RequestBody Map<String,Object> map, SXSSFWorkbook workbook, HttpServletResponse httpServletResponse) throws IllegalAccessException, IOException {

        log.info("------excel controller --------");
        String json = map.get("paramList").toString();
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> list = mapper.readValue(json, new TypeReference<ArrayList<Map<String, Object>>>(){});

        Sheet sheet = workbook.createSheet();

        int rowIndex = 0;
        Row headerRow = sheet.createRow(rowIndex++);

        // 전달받은 List의 첫번재 인덱스 값으로 class를 가져오고, 거기서 필드값을 가져와서
        // 해당 필드에 선언된 어노테이션 값을 가져오기 !
        Field[] declaredFields = list.get(0).getClass().getDeclaredFields();

        //Field[] declaredFields = new MemberDto().getClass().getDeclaredFields();

        for(int i=0 ; i<declaredFields.length; i++){
            Cell cell = headerRow.createCell(i);
            String headerName = declaredFields[i].getDeclaredAnnotation(HeaderColumn.class).headerName();
            cell.setCellValue(headerName);

            // body 넣어주기
            Row bodyRow = sheet.createRow(i);
            Cell bodyCell = bodyRow.createCell(i);

            declaredFields[i].setAccessible(true);
            Object value = declaredFields[i].get((Object) declaredFields[i].getName());
            log.info("value:"+(String)value);
            bodyCell.setCellValue((String)value);
        }

        httpServletResponse.setContentType("application/vnd.ms-excel");
        httpServletResponse.setHeader("Content-Disposition",String.format("attachment;filename=%s.xlsx", "excel"));
        workbook.write(httpServletResponse.getOutputStream()); //workbook은 HttpServletResponse의 OutPutStream을 이용하여 엑셀 내용을 출력함.
        workbook.close();


//        // 데이터 넣어주기
//        int idx = 0;
//        for (Object o : list) {
//            Row bodyRow = sheet.createRow(rowIndex++);
//            Cell bodyCell = bodyRow.createCell(idx++);



//            https://goodgid.github.io/Java-Reflection-Field-Value/
//            getDeclaredFields().value() 식으로 ? reflection으로 특정 field 에 대한 value 접근 가능함.찾아보기


        }
    }

