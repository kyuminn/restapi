package com.example.excel.member;

import com.example.excel.column.ExcelColumn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public String member1(){

        MemberDto memberDto = new MemberDto();
        Field[] declaredFields = memberDto.getClass().getDeclaredFields();
        for (Field f : declaredFields) {
            System.out.println(f.getName());
            System.out.println(f.getAnnotation(ExcelColumn.class).headerName());
            System.out.println("================================================");
        }
        return "member/member";
    }

    @PostMapping
    public String member2(@ModelAttribute MemberDto memberDto){
        log.info("username={}, email={}, age={}", memberDto.getUsername(), memberDto.getEmail(), memberDto.getAge());
        memberService.addMember(memberDto);
        log.info("==========등록 성공!============");
        return "redirect:/";
    }

    // ResponseBody, RequestBody : 서버와 비동기 통신 (예:ajax) 할 때 사용하는 어노테이션.
    // 비동기통신 할 때 HttpBody에다가 원하는 데이터를 담아서 서버에 보내고, 서버에서 클라이언트로 응답을 보낼때도 HttpBody에 response를 담아서 보낸다
    // 이 때 HttpBody에 담기는 데이터 형식은 대부분 json 형태임.
    // @RequestBody, @ResponseBody 어노테이션이 json 형태의 데이터를 java 객체로 파싱해준다.
    // cf : @RestController : @ResponseBody+@Controller
    // https://cheershennah.tistory.com/179

    @PostMapping("/json")
    @ResponseBody
    public MemberDto member3(@RequestBody MemberDto memberDto){ //@RequestBody는 Http request body(post로 보낸 json data)를 java 객체로 전달받을 수 있다.
        log.info("==json format==>username={}, email={}, age={}", memberDto.getUsername(), memberDto.getEmail(), memberDto.getAge());
        return memberDto; //ajax의 success method로 전달.
    }

    @GetMapping("/list")
    public String board(Model model){
        List<MemberDto> memberList = memberService.getAllMember();
        model.addAttribute("list",memberList);
        return "member/board";
    }

    @GetMapping("/detail/{email}")
    public String detail(@PathVariable("email") String email , Model model){
        log.info("email={}",email);
        MemberDto memberDto = memberService.findByEmail(email);
        model.addAttribute("member",memberDto);
        return "member/detail";
    }

    @PostMapping("/edit")
    public String edit(@RequestParam("email") String email,Model model){
        log.info("edit email={}", email);
        MemberDto memberDto = memberService.findByEmail(email);
        model.addAttribute("member", memberDto);
        return "member/edit";
    }
    // 안쓰는 걸로 바꿔야함.
    @GetMapping("/excel")
    public void mkExcelFile(HttpServletResponse httpServletResponse) throws IOException {
        //excel 파일 생성
        Workbook workbook = new SXSSFWorkbook();
        //excel 파일 내부에 sheet 생성
        Sheet sheet = workbook.createSheet(); //sheet 이름 넣을 수 있음..

        // excel 파일에 넣을 값 가져오기
        List<MemberDto> list = memberService.getAllMember();

        // excel 파일 헤더 생성
        int rowIndex = 0;
        Row headerRow = sheet.createRow(rowIndex++);

        // Cell 생성
//        Cell cell1 = headerRow.createCell(0);
//        cell1.setCellValue("이름");
//
//        Cell cell2 = headerRow.createCell(1);
//        cell2.setCellValue("이메일");
//
//        Cell cell3 = headerRow.createCell(2);
//        cell3.setCellValue("나이");

        Field[] declaredFields = new MemberDto().getClass().getDeclaredFields();

        int cellIdx = 0;
        for (Field f : declaredFields) {
            Cell cell = headerRow.createCell(cellIdx++);
            cell.setCellValue(f.getDeclaredAnnotation(ExcelColumn.class).headerName());
        }

//        for(int i=0 ; i<declaredFields.length; i++){
//            Cell cell = headerRow.createCell(i);
//            String headerName = declaredFields[i].getDeclaredAnnotation(ExcelColumn.class).headerName();
//            cell.setCellValue(headerName);
//        }


        // 데이터 넣어주기
        for (MemberDto memberDto : list) {
            
            Row bodyRow = sheet.createRow(rowIndex++);

            Cell bodyCell1 = bodyRow.createCell(0);
            bodyCell1.setCellValue(memberDto.getUsername());

            Cell bodyCell2 = bodyRow.createCell(1);
            bodyCell2.setCellValue(memberDto.getEmail());

            Cell bodyCell3 = bodyRow.createCell(2);
            bodyCell3.setCellValue(memberDto.getAge());
        }


        httpServletResponse.setContentType("application/vnd.ms-excel");
        httpServletResponse.setHeader("Content-Disposition",String.format("attachment;filename=%s.xlsx", "excel"));
        workbook.write(httpServletResponse.getOutputStream()); //workbook은 HttpServletResponse의 OutPutStream을 이용하여 엑셀 내용을 출력함.
        workbook.close();
    }

}
