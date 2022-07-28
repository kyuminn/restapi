package com.mnsoft.upmu.common.util;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mnsoft.upmu.operationCar.vo.OperationCar;
import com.mnsoft.upmu.system.vo.WStatus;
 
public class ExcelView extends AbstractExcelPOIView {
 
    @SuppressWarnings({ "unchecked", "deprecation" })
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
 
        String type = (String) model.get("type");
        
        if(type.equals("carTax")){ //차량예약 국세청 양식
        	//target에 따라서 엑셀 문서 작성을 분기한다.
            //Object로 넘어온 값을 각 Model에 맞게 형변환 해준다.
            HashMap<Object, Object> listBooks = (HashMap<Object, Object>) model.get("excelList");
            System.out.println(listBooks);
            List<OperationCar> target = (List<OperationCar>) model.get("target");
	        for(int i=0; i<target.size(); i++){
	        	//셀 스타일 및 폰트 설정(굵게, 18)
	        	Font fontOfGothicBlackBold18 = workbook.createFont();
	        	//정렬
	        	fontOfGothicBlackBold18.setFontName("맑은고딕"); //글씨체
	        	fontOfGothicBlackBold18.setFontHeight((short)(18*20)); //사이즈
	        	fontOfGothicBlackBold18.setBoldweight(Font.BOLDWEIGHT_BOLD); //볼드 (굵게)
	        	
	        	//셀 스타일 및 폰트 설정(굵게, 11)
	        	Font fontOfGothicBlackBold11 = workbook.createFont();
	        	//정렬
	        	fontOfGothicBlackBold11.setFontName("맑은고딕"); //글씨체
	        	fontOfGothicBlackBold11.setFontHeight((short)(11*20)); //사이즈
	        	fontOfGothicBlackBold11.setBoldweight(Font.BOLDWEIGHT_BOLD); //볼드 (굵게)
	        	
	        	CellStyle styleOfBoardFontBlackBold18 = workbook.createCellStyle();
	        	//정렬
	        	styleOfBoardFontBlackBold18.setAlignment(CellStyle.ALIGN_CENTER); //가운데 정렬
	        	styleOfBoardFontBlackBold18.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //높이 가운데 정렬
	        	//테두리 선 (우,좌,위,아래)
	        	styleOfBoardFontBlackBold18.setBorderRight(HSSFCellStyle.BORDER_THIN);
	        	styleOfBoardFontBlackBold18.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	        	styleOfBoardFontBlackBold18.setBorderTop(HSSFCellStyle.BORDER_THIN);
	        	styleOfBoardFontBlackBold18.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	        	//폰트 설정 (위 폰트 사용)
	        	styleOfBoardFontBlackBold18.setFont(fontOfGothicBlackBold18);
	        	
	        	CellStyle styleOfBoardFontBlackBold11 = workbook.createCellStyle();
	        	//정렬
	        	styleOfBoardFontBlackBold11.setAlignment(CellStyle.ALIGN_LEFT); //가운데 정렬
	        	styleOfBoardFontBlackBold11.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //높이 가운데 정렬
	        	//폰트 설정 (위 폰트 사용)
	        	styleOfBoardFontBlackBold11.setFont(fontOfGothicBlackBold11);
	        	
	        	//3.셀 스타일 및 폰트 설정
	        	CellStyle styleOfBoardFillFontBlack11 = workbook.createCellStyle();
	        	//정렬
	        	styleOfBoardFillFontBlack11.setAlignment(CellStyle.ALIGN_CENTER); //가운데 정렬
	        	styleOfBoardFillFontBlack11.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //높이 가운데 정렬
	        	//배경색
	        	styleOfBoardFillFontBlack11.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	        	styleOfBoardFillFontBlack11.setFillPattern(CellStyle.SOLID_FOREGROUND);
	        	//테두리 선 (우,좌,위,아래)
	        	styleOfBoardFillFontBlack11.setBorderRight(HSSFCellStyle.BORDER_THIN);
	        	styleOfBoardFillFontBlack11.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	        	styleOfBoardFillFontBlack11.setBorderTop(HSSFCellStyle.BORDER_THIN);
	        	styleOfBoardFillFontBlack11.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	
	        	//폰트 설정
	        	Font Black10 = workbook.createFont();
	        	Black10.setFontName("맑은고딕"); //글씨체
	        	Black10.setFontHeight((short)(10*20)); //사이즈
	        	styleOfBoardFillFontBlack11.setFont(Black10);
	        	
	        	//4-1.셀 스타일 및 폰트 설정(일반 텍스트)
	        	CellStyle styleOfBoardFontBlack10 = workbook.createCellStyle();
	        	//정렬
	        	styleOfBoardFontBlack10.setAlignment(CellStyle.ALIGN_CENTER); //가운데 정렬
	        	styleOfBoardFontBlack10.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //높이 가운데 정렬
	        	//테두리 선 (우,좌,위,아래)
	        	styleOfBoardFontBlack10.setBorderRight(HSSFCellStyle.BORDER_THIN);
	        	styleOfBoardFontBlack10.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	        	styleOfBoardFontBlack10.setBorderTop(HSSFCellStyle.BORDER_THIN);
	        	styleOfBoardFontBlack10.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	        	//폰트 설정 (위 폰트 사용)
	        	styleOfBoardFontBlack10.setFont(Black10);
	        	
	        	//4-2.셀 스타일 및 폰트 설정(일반 텍스트 오른쪽 정렬)
	        	CellStyle styleOfBoardFontBlackRight10 = workbook.createCellStyle();
	        	//정렬
	        	styleOfBoardFontBlackRight10.setAlignment(CellStyle.ALIGN_RIGHT); //가운데 정렬
	        	styleOfBoardFontBlackRight10.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //높이 가운데 정렬
	        	//테두리 선 (우,좌,위,아래)
	        	styleOfBoardFontBlackRight10.setBorderRight(HSSFCellStyle.BORDER_THIN);
	        	styleOfBoardFontBlackRight10.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	        	styleOfBoardFontBlackRight10.setBorderTop(HSSFCellStyle.BORDER_THIN);
	        	styleOfBoardFontBlackRight10.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	        	//폰트 설정 (위 폰트 사용)
	        	styleOfBoardFontBlackRight10.setFont(Black10);
	        	
	        	//4-3.셀 스타일 및 폰트 설정(일반 텍스트 왼쪽정렬)
	        	CellStyle styleOfBoardFontBlackLeft10 = workbook.createCellStyle();
	        	//정렬
	        	styleOfBoardFontBlackLeft10.setAlignment(CellStyle.ALIGN_LEFT); //가운데 정렬
	        	styleOfBoardFontBlackLeft10.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //높이 가운데 정렬
	        	//테두리 선 (우,좌,위,아래)
	        	styleOfBoardFontBlackLeft10.setBorderRight(HSSFCellStyle.BORDER_THIN);
	        	styleOfBoardFontBlackLeft10.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	        	styleOfBoardFontBlackLeft10.setBorderTop(HSSFCellStyle.BORDER_THIN);
	        	styleOfBoardFontBlackLeft10.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	        	//폰트 설정 (위 폰트 사용)
	        	styleOfBoardFontBlack10.setFont(Black10);
	        	
	        	//5 셀 스타일 및 폰트 설정(하단 테두리 텍스트)
	        	CellStyle styleOfBottomFontBlack10 = workbook.createCellStyle();
	        	//정렬
	        	styleOfBottomFontBlack10.setAlignment(CellStyle.ALIGN_CENTER); //가운데 정렬
	        	styleOfBottomFontBlack10.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //높이 가운데 정렬
	        	//테두리 선 (우,좌,위,아래)
	        	styleOfBottomFontBlack10.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	        	//폰트 설정 (위 폰트 사용)
	        	styleOfBottomFontBlack10.setFont(Black10);
	        	
	        	//6.셀 스타일 및 폰트 설정(테두리x 텍스트)
	        	CellStyle styleOfBoardFontWhite10 = workbook.createCellStyle();
	        	//정렬
	        	styleOfBoardFontWhite10.setAlignment(CellStyle.ALIGN_CENTER); //가운데 정렬
	        	styleOfBoardFontWhite10.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //높이 가운데 정렬
	        	//폰트 설정 (위 폰트 사용)
	        	styleOfBoardFontWhite10.setFont(Black10);
	        	
	        	//7. 셀 스타일 금액 콤마
	        	CellStyle styleOfComma = workbook.createCellStyle();
	        	XSSFDataFormat format = (XSSFDataFormat) workbook.createDataFormat();
	        	styleOfComma.setDataFormat(format.getFormat("#,###"));
	        	//정렬
	        	styleOfComma.setAlignment(CellStyle.ALIGN_RIGHT); //오른쪽 정렬
	        	styleOfComma.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //높이 가운데 정렬
	        	//테두리 선 (우,좌,위,아래)
	        	styleOfComma.setBorderRight(HSSFCellStyle.BORDER_THIN);
	        	styleOfComma.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	        	styleOfComma.setBorderTop(HSSFCellStyle.BORDER_THIN);
	        	styleOfComma.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	        	//폰트 설정 (위 폰트 사용)
	        	styleOfComma.setFont(Black10);
	        	
	        	
		        //Sheet 생성
	        	Sheet sheet = workbook.getSheet("Sheet1");
	        	if(sheet == null){
	        		sheet = workbook.createSheet(URLDecoder.decode(target.get(i).getCar_name(),"utf-8").replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "").replaceAll("/", ""));
	        	}
	        	
	        	//열 너비
	        	sheet.setColumnWidth((short)0, (short)3000);
	        	sheet.setColumnWidth((short)1, (short)5000);
	        	sheet.setColumnWidth((short)2, (short)4000);
	        	sheet.setColumnWidth((short)3, (short)4000);
	        	sheet.setColumnWidth((short)4, (short)4000);
	        	sheet.setColumnWidth((short)6, (short)4000);
	        	sheet.setColumnWidth((short)7, (short)4000);
	        	sheet.setColumnWidth((short)8, (short)4300);
	        	sheet.setDisplayGridlines(true);
	        	
		        Row row = null;
		        Cell cell =  null;
		        int rowCount = 0;
		        int cellCount = 0;
		        
		        // 데이터 Cell 생성
		        List<OperationCar> carList =  (List<OperationCar>) listBooks.get(i);
		 
		        // 제목 Cell 생성
		        row = sheet.createRow(rowCount++);
		        sheet.addMergedRegion(new CellRangeAddress(0,3,0,0)); //열시작, 열종료, 행시작, 행종료 (자바배열과 같이 0부터 시작)
		        cell = row.createCell(0);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell.setCellValue("사업연도");
		        
		        sheet.addMergedRegion(new CellRangeAddress(0,0,1,2));
		        cell = row.createCell(1);
		        cell.setCellStyle(styleOfBoardFontWhite10);
		        if(!carList.isEmpty()){
		        	cell.setCellValue(carList.get(0).getFrom_dt());
		        }else{
		        	cell.setCellValue("");
		        }
		        
		        sheet.addMergedRegion(new CellRangeAddress(0,3,3,5));
		        cell = row.createCell(3);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell.setCellStyle(styleOfBoardFontBlackBold18);
		        cell.setCellValue("업무용승용차 운행기록부");
		        
		        sheet.addMergedRegion(new CellRangeAddress(0,1,6,7));
		        cell = row.createCell(6);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell.setCellValue("법인명");
		        
		        sheet.addMergedRegion(new CellRangeAddress(0,1,8,8));
		        cell = row.createCell(8);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell.setCellValue("현대엠엔소프트");
		        
		        row = sheet.createRow(rowCount++);
		        cell = row.createCell(0);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        sheet.addMergedRegion(new CellRangeAddress(1,2,1,2));
		        cell = row.createCell(1);
		        cell.setCellStyle(styleOfBoardFontWhite10);
		        cell.setCellValue("~");
		        cell = row.createCell(3);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(6);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(7);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(8);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        
		        row = sheet.createRow(rowCount++);
		        cell = row.createCell(0);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(3);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        sheet.addMergedRegion(new CellRangeAddress(2,3,6,7));
		        cell = row.createCell(6);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell.setCellValue("사업자등록번호");
		        cell = row.createCell(7);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        
		        sheet.addMergedRegion(new CellRangeAddress(2,3,8,8));
		        cell = row.createCell(8);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell.setCellValue("229-81-35114");
		        
		        row = sheet.createRow(rowCount++);
		        cell = row.createCell(0);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        sheet.addMergedRegion(new CellRangeAddress(3,3,1,2));
		        cell = row.createCell(1);
		        cell.setCellStyle(styleOfBottomFontBlack10);
		        if(!carList.isEmpty()){
		        	cell.setCellValue(carList.get(0).getTo_dt());
		        }else{
		        	cell.setCellValue("");
		        }
		        cell = row.createCell(2);
		        cell.setCellStyle(styleOfBottomFontBlack10);
		        cell = row.createCell(3);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(4);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(5);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(6);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(7);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(8);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        
		        row = sheet.createRow(rowCount++);
		        row = sheet.createRow(rowCount++);
		        row = sheet.createRow(rowCount++);
		        
		        cellCount = 0;
		        sheet.addMergedRegion(new CellRangeAddress(6,6,0,4));
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlackBold11);
		        cell.setCellValue("1. 기본정보");
		        
		        row = sheet.createRow(rowCount++);
		        cellCount = 0;
		        sheet.addMergedRegion(new CellRangeAddress(7,7,0,2));
		        sheet.addMergedRegion(new CellRangeAddress(7,7,3,4));
		        
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell.setCellValue("차종");
		        
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell.setCellValue("자동차등록번호");
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        
		        row = sheet.createRow(rowCount++);
		        cellCount = 0;
		        sheet.addMergedRegion(new CellRangeAddress(8,8,0,2));
		        sheet.addMergedRegion(new CellRangeAddress(8,8,3,4));
		        
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell.setCellValue(URLDecoder.decode(target.get(i).getCar_name(),"utf-8").replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", ""));
		        
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
	//	        cell.setCellValue("2232234848");
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        
		        row = sheet.createRow(rowCount++);
		        row = sheet.createRow(rowCount++);
		        cellCount = 0;
		        sheet.addMergedRegion(new CellRangeAddress(10,10,0,4));
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlackBold11);
		        cell.setCellValue("2. 업무용 사용비율 계산");
		        
		        row = sheet.createRow(rowCount++);
		        cellCount = 0;
		        sheet.addMergedRegion(new CellRangeAddress(11,13,0,0));
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        
		        String use_dt = "사용\n일자\n(요일)";
		        use_dt.replaceAll("\n","<br style='mso-data-placement:same-cell;'>");
		        cell.setCellValue(URLDecoder.decode(use_dt,"utf-8"));
		        CellStyle cs = workbook.createCellStyle();
		        Font ft = workbook.createFont();
		        cs.setWrapText(true);
		        ft.setFontHeight((short)(10*20));
		        cs.setAlignment(CellStyle.ALIGN_CENTER);
		        cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		        cs.setBorderTop(HSSFCellStyle.BORDER_THIN);
		        cs.setFont(ft);
		        cell.setCellStyle(cs);
		        row.setHeightInPoints((2*sheet.getDefaultRowHeightInPoints()));
	//	        sheet.autoSizeColumn(2);
		        
		        sheet.addMergedRegion(new CellRangeAddress(11,11,1,2));
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell.setCellValue("사용자");
		        
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        sheet.addMergedRegion(new CellRangeAddress(11,11,3,8));
		        cell.setCellValue("운행내역");
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        
		        cellCount = 0;
		        row = sheet.createRow(rowCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        sheet.addMergedRegion(new CellRangeAddress(12,13,1,1));
		        cell.setCellValue("부서");
		        
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        sheet.addMergedRegion(new CellRangeAddress(12,13,2,2));
		        cell.setCellValue("성명");
		        
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        sheet.addMergedRegion(new CellRangeAddress(12,13,3,3));
		        String before_km = "주행 전 계기판의\n거리(km)";
		        before_km.replaceAll("\n","<br style='mso-data-placement:same-cell;'>");
		        cell.setCellValue(URLDecoder.decode(before_km,"utf-8"));
		        cs.setWrapText(true);
		        ft.setFontHeight((short)(10*20));
		        cs.setAlignment(CellStyle.ALIGN_CENTER);
		        cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		        cs.setBorderRight(HSSFCellStyle.BORDER_THIN);
		        cs.setFont(ft);
		        cell.setCellStyle(cs);
		        row.setHeightInPoints((2*sheet.getDefaultRowHeightInPoints()));
	//	        sheet.autoSizeColumn(2);
		        
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        sheet.addMergedRegion(new CellRangeAddress(12,13,4,4));
		        String after_km = "주행 후 계기판의\n거리(km)";
		        after_km.replaceAll("\n","<br style='mso-data-placement:same-cell;'>");
		        cell.setCellValue(URLDecoder.decode(after_km,"utf-8"));
		        cs.setWrapText(true);
		        ft.setFontHeight((short)(10*20));
		        cs.setAlignment(CellStyle.ALIGN_CENTER);
		        cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		        cs.setBorderRight(HSSFCellStyle.BORDER_THIN);
		        cs.setFont(ft);
		        cell.setCellStyle(cs);
		        row.setHeightInPoints((2*sheet.getDefaultRowHeightInPoints()));
	//	        sheet.autoSizeColumn(2);
		        
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        sheet.addMergedRegion(new CellRangeAddress(12,13,5,5));
		        String mileage = "주행거리\n(km)";
		        mileage.replaceAll("\n","<br style='mso-data-placement:same-cell;'>");
		        cell.setCellValue(URLDecoder.decode(mileage,"utf-8"));
		        cs.setWrapText(true);
		        ft.setFontHeight((short)(10*20));
		        cs.setAlignment(CellStyle.ALIGN_CENTER);
		        cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		        cs.setFont(ft);
		        cell.setCellStyle(cs);
		        row.setHeightInPoints((2*sheet.getDefaultRowHeightInPoints()));
	//	        sheet.autoSizeColumn(2);
		        
		        sheet.addMergedRegion(new CellRangeAddress(12,13,8,8));
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell.setCellValue("비고");
		        
		        sheet.addMergedRegion(new CellRangeAddress(12,12,6,7));
		        cell = row.createCell(6);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell.setCellValue("업무용 사용거리(km)");
		        
		        row = sheet.createRow(rowCount++);
		        cellCount = 0;
		        cell = row.createCell(0);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(1);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(2);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(3);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(4);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(5);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        
		        cell = row.createCell(6);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell.setCellValue("출.퇴근용(km)");
		        
		        cell = row.createCell(7);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell.setCellValue("일반 업무용(km)");
		        cell = row.createCell(8);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        
		        if(!carList.isEmpty()){
		        	for(int j=0; j<carList.size(); j++){
		        		row = sheet.createRow(rowCount++);
		    	        cellCount = 0;
			            
			            cell = row.createCell(0);
			            cell.setCellStyle(styleOfBoardFontBlack10);
			            cell.setCellValue(carList.get(j).getOc_dt()); //데이터를 가져와 입력
			            
			            cell = row.createCell(1);
			            cell.setCellStyle(styleOfBoardFontBlackLeft10);
			            cell.setCellValue(carList.get(j).getDept_nm());
			            
			            cell = row.createCell(2);
			            cell.setCellStyle(styleOfBoardFontBlackLeft10);
			            cell.setCellValue(carList.get(j).getUser_nm());
			            
			            cell = row.createCell(3);
			            cell.setCellStyle(styleOfComma);
			            if(StringUtil.isNullToString(carList.get(j).getDeparture_km()).equals("")){
			            	cell.setCellValue(carList.get(j).getDeparture_km());
			            }else{
			            	cell.setCellValue(carList.get(j).getDeparture_km());
			            }
			            cell = row.createCell(4);
			            cell.setCellStyle(styleOfComma);
			            if(StringUtil.isNullToString(carList.get(j).getDestination_km()).equals("")){
			            	cell.setCellValue(carList.get(j).getDestination_km());
			            }else{
			            	cell.setCellValue(carList.get(j).getDestination_km());
			            }
			            
			            cell = row.createCell(5);
			            cell.setCellStyle(styleOfComma);
			            if(StringUtil.isNullToString(carList.get(j).getMileage()).equals("")){
			            	cell.setCellValue(carList.get(j).getMileage());
			            }else{
			            	cell.setCellValue(carList.get(j).getMileage());
			            }
			            
			            cell = row.createCell(6);
			            cell.setCellStyle(styleOfComma);
			            if(StringUtil.isNullToString(carList.get(j).getCommuting_km()).equals("")){
			            	cell.setCellValue(carList.get(j).getCommuting_km());
			            }else{
			            	cell.setCellValue(carList.get(j).getCommuting_km());
			            }
			            
			            cell = row.createCell(7);
			            cell.setCellStyle(styleOfComma);
			            if(StringUtil.isNullToString(carList.get(j).getWork_km()).equals("")){
			            	cell.setCellValue(carList.get(j).getWork_km());
			            }else{
			            	cell.setCellValue(carList.get(j).getWork_km());
			            }
			            
			            cell = row.createCell(8);
			            cell.setCellStyle(styleOfBoardFontBlack10);
			            cell.setCellValue(carList.get(j).getBigo());
		        	}
		        }
		        
		        cellCount = 0;
		        row = sheet.createRow(rowCount++);
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFillFontBlack11);
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFillFontBlack11);
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        sheet.addMergedRegion(new CellRangeAddress(14+carList.size(),15+carList.size(),0,0));
		        sheet.addMergedRegion(new CellRangeAddress(14+carList.size(),15+carList.size(),1,2));
		        
		        sheet.addMergedRegion(new CellRangeAddress(14+carList.size(),14+carList.size(),3,5));
		        cell = row.createCell(3);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell.setCellValue("사업연도 총주행 거리(km)");
		        
		        sheet.addMergedRegion(new CellRangeAddress(14+carList.size(),14+carList.size(),6,7));
		        cell = row.createCell(6);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell.setCellValue("사업연도 업무용 사용거리(km)");
		        cell = row.createCell(8);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell.setCellValue("업무사용비율");
		        
		        cellCount = 0;
		        row = sheet.createRow(rowCount++);
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFillFontBlack11);
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFillFontBlack11);
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        cell = row.createCell(cellCount++);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        sheet.addMergedRegion(new CellRangeAddress(15+carList.size(),15+carList.size(),3,5));
		        cell = row.createCell(3);
		        cell.setCellStyle(styleOfBoardFontBlackRight10);
		        if(!carList.isEmpty()){
		        	if(StringUtil.isNullToString(carList.get(carList.size()-1).getSum_mileage()).equals("")){
		            	cell.setCellValue(carList.get(carList.size()-1).getSum_mileage());
		            }else{
		            	cell.setCellValue(Integer.parseInt(carList.get(carList.size()-1).getSum_mileage()));
		            }
		        }
		        
		        sheet.addMergedRegion(new CellRangeAddress(15+carList.size(),15+carList.size(),6,7));
		        cell = row.createCell(6);
		        cell.setCellStyle(styleOfBoardFontBlackRight10);
		        if(!carList.isEmpty()){
		        	if(StringUtil.isNullToString(carList.get(carList.size()-1).getSum_work_km()).equals("")){
		            	cell.setCellValue(carList.get(carList.size()-1).getSum_work_km());
		            }else{
		            	cell.setCellValue(Integer.parseInt(carList.get(carList.size()-1).getSum_work_km()));
		            }
		        }
		        
		        cell = row.createCell(8);
		        cell.setCellStyle(styleOfBoardFontBlack10);
		        if(!carList.isEmpty()){
			        if(!StringUtil.isNullToString(carList.get(carList.size()-1).getSum_mileage()).equals("") && !StringUtil.isNullToString(carList.get(carList.size()-1).getSum_work_km()).equals("")){
			        	if(!StringUtil.isNullToString(carList.get(carList.size()-1).getSum_mileage()).equals("0") && !StringUtil.isNullToString(carList.get(carList.size()-1).getSum_work_km()).equals("0")){
			        		//cell.setCellValue(Integer.toString((Integer.parseInt(carList.get(carList.size()-1).getSum_work_km()) / Integer.parseInt(carList.get(carList.size()-1).getSum_mileage()))*100)+"%");
			        		cell.setCellValue(carList.get(carList.size()-1).getWork_rate());
			        	}
			        }
		        }
		        
		        
		        cellCount = 0;
		        row = sheet.createRow(rowCount++);
		        sheet.addMergedRegion(new CellRangeAddress(16+carList.size(),16+carList.size(),0,8));
		        for(int k=0; k<9; k++){
		        	cell = row.createCell(k);
		        	cell.setCellStyle(styleOfBoardFontBlack10);
		        }
		 
	        }
    	}else if(type.equals("carCount")){
    		//폰트 설정
        	Font Black10 = workbook.createFont();
        	Black10.setFontName("맑은고딕"); //글씨체
        	Black10.setFontHeight((short)(10*20)); //사이즈
        	
        	//4-1.셀 스타일 및 폰트 설정(일반 텍스트)
        	CellStyle styleOfBoardFontBlack10 = workbook.createCellStyle();
        	//정렬
        	styleOfBoardFontBlack10.setAlignment(CellStyle.ALIGN_CENTER); //가운데 정렬
        	styleOfBoardFontBlack10.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //높이 가운데 정렬
        	//테두리 선 (우,좌,위,아래)
        	styleOfBoardFontBlack10.setBorderRight(HSSFCellStyle.BORDER_THIN);
        	styleOfBoardFontBlack10.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        	styleOfBoardFontBlack10.setBorderTop(HSSFCellStyle.BORDER_THIN);
        	styleOfBoardFontBlack10.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        	//폰트 설정 (위 폰트 사용)
        	styleOfBoardFontBlack10.setFont(Black10);
        	
        	Sheet sheet = workbook.getSheet("Sheet1");
        	if(sheet == null){
        		sheet = workbook.createSheet("Sheet1");
        	}
        	
        	Row row = null;
	        Cell cell =  null;
	        int rowCount = 0;
	        int cellCount = 0;
	        
	        List<OperationCar> carList = (List<OperationCar>) model.get("carList"); //리스트
	        List<String> recNames = (List<String>) model.get("recNames"); //컬럼Id
	        List<String> recTitles = (List<String>) model.get("recTitles"); //엑셀헤더
	        
	        sheet.setColumnWidth((short)0, (short)4500);
        	sheet.setColumnWidth((short)1, (short)4500);
        	sheet.setColumnWidth((short)2, (short)4500);
        	sheet.setColumnWidth((short)3, (short)4500);
        	sheet.setColumnWidth((short)4, (short)4500);
        	sheet.setColumnWidth((short)5, (short)4500);
        	sheet.setColumnWidth((short)6, (short)3000);
	        
	        row = sheet.createRow(rowCount++);
	        if(!recTitles.isEmpty()){
		        for(int i=0; i<recTitles.size(); i++){
		        	cell = row.createCell(i);
		        	cell.setCellStyle(styleOfBoardFontBlack10);
		        	String week = recTitles.get(i);
		        	week.replaceAll("\n","<br style='mso-data-placement:same-cell;'>");
		        	cell.setCellValue(URLDecoder.decode(week,"utf-8"));
		        	CellStyle cs = workbook.createCellStyle();
			        Font ft = workbook.createFont();
			        cs.setWrapText(true);
			        ft.setFontHeight((short)(10*20));
			        cs.setAlignment(CellStyle.ALIGN_CENTER);
			        cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			        cs.setBorderTop(HSSFCellStyle.BORDER_THIN);
			        cs.setFont(ft);
			        cell.setCellStyle(cs);
			        row.setHeightInPoints((2*sheet.getDefaultRowHeightInPoints()));
		        }
	        }
	        
	        if(!carList.isEmpty()){
	        	for(int i=0; i<carList.size(); i++){
	        		row = sheet.createRow(rowCount++);
	        		cellCount = 0;
	        		
	        		cell = row.createCell(0);
	        		cell.setCellStyle(styleOfBoardFontBlack10);
	        		cell.setCellValue(carList.get(i).getCar_name());
	        		
	        		cell = row.createCell(1);
	        		cell.setCellStyle(styleOfBoardFontBlack10);
	        		cell.setCellValue(Integer.parseInt(carList.get(i).getW1()));
	        		
	        		cell = row.createCell(2);
	        		cell.setCellStyle(styleOfBoardFontBlack10);
	        		cell.setCellValue(Integer.parseInt(carList.get(i).getW2()));
	        		
	        		cell = row.createCell(3);
	        		cell.setCellStyle(styleOfBoardFontBlack10);
	        		cell.setCellValue(Integer.parseInt(carList.get(i).getW3()));
	        		
	        		cell = row.createCell(4);
	        		cell.setCellStyle(styleOfBoardFontBlack10);
	        		cell.setCellValue(Integer.parseInt(carList.get(i).getW4()));
	        		
	        		cell = row.createCell(5);
	        		cell.setCellStyle(styleOfBoardFontBlack10);
	        		cell.setCellValue(Integer.parseInt(carList.get(i).getW5()));
	        		
	        		if(recTitles.size() == 8){
		        		cell = row.createCell(6);
		        		cell.setCellStyle(styleOfBoardFontBlack10);
		        		cell.setCellValue(Integer.parseInt(carList.get(i).getW6()));
		        		
		        		cell = row.createCell(7);
		        		cell.setCellStyle(styleOfBoardFontBlack10);
		        		cell.setCellValue(Integer.parseInt(carList.get(i).getSum()));
	        		}else{
	        			cell = row.createCell(6);
		        		cell.setCellStyle(styleOfBoardFontBlack10);
		        		cell.setCellValue(Integer.parseInt(carList.get(i).getSum()));
	        		}
	        		
	        	}
	        }
        	
    	}else if(type.equals("carTime")){
    		//폰트 설정
        	Font Black10 = workbook.createFont();
        	Black10.setFontName("맑은고딕"); //글씨체
        	Black10.setFontHeight((short)(10*20)); //사이즈
        	
        	//4-1.셀 스타일 및 폰트 설정(일반 텍스트)
        	CellStyle styleOfBoardFontBlack10 = workbook.createCellStyle();
        	//정렬
        	styleOfBoardFontBlack10.setAlignment(CellStyle.ALIGN_CENTER); //가운데 정렬
        	styleOfBoardFontBlack10.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //높이 가운데 정렬
        	//테두리 선 (우,좌,위,아래)
        	styleOfBoardFontBlack10.setBorderRight(HSSFCellStyle.BORDER_THIN);
        	styleOfBoardFontBlack10.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        	styleOfBoardFontBlack10.setBorderTop(HSSFCellStyle.BORDER_THIN);
        	styleOfBoardFontBlack10.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        	//폰트 설정 (위 폰트 사용)
        	styleOfBoardFontBlack10.setFont(Black10);
        	
        	Sheet sheet = workbook.getSheet("Sheet1");
        	if(sheet == null){
        		sheet = workbook.createSheet("Sheet1");
        	}
        	
        	Row row = null;
	        Cell cell =  null;
	        int rowCount = 0;
	        int cellCount = 0;
	        
	        List<OperationCar> carList = (List<OperationCar>) model.get("carList"); //리스트
	        List<String> recNames = (List<String>) model.get("recNames"); //컬럼Id
	        List<String> recTitles = (List<String>) model.get("recTitles"); //엑셀헤더
	        
	        sheet.setColumnWidth((short)0, (short)4500);
        	sheet.setColumnWidth((short)1, (short)4500);
        	sheet.setColumnWidth((short)2, (short)4500);
        	sheet.setColumnWidth((short)3, (short)4500);
        	sheet.setColumnWidth((short)4, (short)4500);
        	sheet.setColumnWidth((short)5, (short)4500);
        	sheet.setColumnWidth((short)6, (short)3000);
	        
	        row = sheet.createRow(rowCount++);
	        if(!recTitles.isEmpty()){
		        for(int i=0; i<recTitles.size(); i++){
		        	cell = row.createCell(i);
		        	cell.setCellStyle(styleOfBoardFontBlack10);
		        	String week = recTitles.get(i);
		        	week.replaceAll("\n","<br style='mso-data-placement:same-cell;'>");
		        	cell.setCellValue(URLDecoder.decode(week,"utf-8"));
		        	CellStyle cs = workbook.createCellStyle();
			        Font ft = workbook.createFont();
			        cs.setWrapText(true);
			        ft.setFontHeight((short)(10*20));
			        cs.setAlignment(CellStyle.ALIGN_CENTER);
			        cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			        cs.setBorderTop(HSSFCellStyle.BORDER_THIN);
			        cs.setFont(ft);
			        cell.setCellStyle(cs);
			        row.setHeightInPoints((2*sheet.getDefaultRowHeightInPoints()));
		        }
	        }
	        
	        if(!carList.isEmpty()){
	        	for(int i=0; i<carList.size(); i++){
	        		row = sheet.createRow(rowCount++);
	        		cellCount = 0;
	        		
	        		cell = row.createCell(0);
	        		cell.setCellStyle(styleOfBoardFontBlack10);
	        		cell.setCellValue(carList.get(i).getCar_name());
	        		
	        		cell = row.createCell(1);
	        		cell.setCellStyle(styleOfBoardFontBlack10);
	        		cell.setCellValue(carList.get(i).getW1());
	        		
	        		cell = row.createCell(2);
	        		cell.setCellStyle(styleOfBoardFontBlack10);
	        		cell.setCellValue(carList.get(i).getW2());
	        		
	        		cell = row.createCell(3);
	        		cell.setCellStyle(styleOfBoardFontBlack10);
	        		cell.setCellValue(carList.get(i).getW3());
	        		
	        		cell = row.createCell(4);
	        		cell.setCellStyle(styleOfBoardFontBlack10);
	        		cell.setCellValue(carList.get(i).getW4());
	        		
	        		cell = row.createCell(5);
	        		cell.setCellStyle(styleOfBoardFontBlack10);
	        		cell.setCellValue(carList.get(i).getW5());
	        		
	        		if(recTitles.size() == 8){
		        		cell = row.createCell(6);
		        		cell.setCellStyle(styleOfBoardFontBlack10);
		        		cell.setCellValue(carList.get(i).getW6());
		        		
		        		cell = row.createCell(7);
		        		cell.setCellStyle(styleOfBoardFontBlack10);
		        		cell.setCellValue(carList.get(i).getSum());
	        		}else{
	        			cell = row.createCell(6);
		        		cell.setCellStyle(styleOfBoardFontBlack10);
		        		cell.setCellValue(carList.get(i).getSum());
	        		}
	        		
	        	}
	        }
	        
    	}else if(type.equals("userInfo")){
    		//폰트 설정
        	Font Black10 = workbook.createFont();
        	Black10.setFontName("맑은고딕"); //글씨체
        	Black10.setFontHeight((short)(10*20)); //사이즈
        	
        	//4-1.셀 스타일 및 폰트 설정(일반 텍스트)
        	CellStyle styleOfBoardFontBlack10 = workbook.createCellStyle();
        	//정렬
        	styleOfBoardFontBlack10.setAlignment(CellStyle.ALIGN_CENTER); //가운데 정렬
        	styleOfBoardFontBlack10.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //높이 가운데 정렬
        	//테두리 선 (우,좌,위,아래)
        	styleOfBoardFontBlack10.setBorderRight(HSSFCellStyle.BORDER_THIN);
        	styleOfBoardFontBlack10.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        	styleOfBoardFontBlack10.setBorderTop(HSSFCellStyle.BORDER_THIN);
        	styleOfBoardFontBlack10.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        	//폰트 설정 (위 폰트 사용)
        	styleOfBoardFontBlack10.setFont(Black10);
        	
        	Sheet sheet = workbook.getSheet("Sheet1");
        	if(sheet == null){
        		sheet = workbook.createSheet("Sheet1");
        	}
        	
        	Row row = null;
	        Cell cell =  null;
	        int rowCount = 0;
	        int cellCount = 0;
	        
	        List<OperationCar> carList = (List<OperationCar>) model.get("carList"); //리스트
	        List<String> recNames = (List<String>) model.get("recNames"); //컬럼Id
	        List<String> recTitles = (List<String>) model.get("recTitles"); //엑셀헤더
    		
	        sheet.setColumnWidth((short)0, (short)5000);
        	sheet.setColumnWidth((short)1, (short)5000);

        	sheet.addMergedRegion(new CellRangeAddress(0,1,0,0));
        	sheet.addMergedRegion(new CellRangeAddress(0,1,1,1));
        	sheet.addMergedRegion(new CellRangeAddress(0,0,2,3));
        	sheet.addMergedRegion(new CellRangeAddress(0,0,4,5));
        	sheet.addMergedRegion(new CellRangeAddress(0,0,6,7));
        	sheet.addMergedRegion(new CellRangeAddress(0,0,8,9));
        	sheet.addMergedRegion(new CellRangeAddress(0,0,10,11));
        	if(recTitles.size() == 9){
        		sheet.addMergedRegion(new CellRangeAddress(0,0,12,13));
        	}
        	
        	row = sheet.createRow(rowCount++);
        	if(!recTitles.isEmpty()){
        		cell = row.createCell(0);
        		cell.setCellValue(recTitles.get(0));
        		cell = row.createCell(1);
        		cell.setCellValue(recTitles.get(1));
        		
        		int rowNum = 0;
 		        for(int i=2; i<=2*(recTitles.size()-3); i+=2){
 		        	cell = row.createCell(i);
 		        	cell.setCellStyle(styleOfBoardFontBlack10);
 		        	String week = recTitles.get(i-rowNum);
 		        	week.replaceAll("\n","<br style='mso-data-placement:same-cell;'>");
 		        	cell.setCellValue(URLDecoder.decode(week,"utf-8"));
 		        	CellStyle cs = workbook.createCellStyle();
 			        Font ft = workbook.createFont();
 			        cs.setWrapText(true);
 			        ft.setFontHeight((short)(10*20));
 			        cs.setAlignment(CellStyle.ALIGN_CENTER);
 			        cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
 			        cs.setBorderTop(HSSFCellStyle.BORDER_THIN);
 			        cs.setFont(ft);
 			        cell.setCellStyle(cs);
 			        row.setHeightInPoints((2*sheet.getDefaultRowHeightInPoints()));
 			        
 			        rowNum++;
 		        }
 		        
 		        if(recTitles.size() == 9){
 		        	sheet.addMergedRegion(new CellRangeAddress(0,0,14,15));
 		        	cell = row.createCell(14);
 		        	cell.setCellValue(recTitles.get(8));
 		        }else{
 		        	sheet.addMergedRegion(new CellRangeAddress(0,0,12,13));
 		        	cell = row.createCell(12);
 		        	cell.setCellValue(recTitles.get(7));
 		        }
 		        
 		        row = sheet.createRow(rowCount++);
 		        cellCount = 0;
 		        
 		        for(int j=2; j<=2*(recTitles.size()-2); j+=2){
 		        	cell = row.createCell(j);
 		        	cell.setCellValue("배차횟수");
 		        	cell = row.createCell(j+1);
 		        	cell.setCellValue("배차시간");
 		        }
 		        
 		       if(!carList.isEmpty()){
 		        	for(int i=0; i<carList.size(); i++){
 		        		row = sheet.createRow(rowCount++);
 		        		cellCount = 0;
 		        		
 		        		cell = row.createCell(0);
 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 		        		cell.setCellValue(carList.get(i).getName());
 		        		
 		        		cell = row.createCell(1);
 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 		        		cell.setCellValue(carList.get(i).getDeptname());
 		        		
 		        		cell = row.createCell(2);
 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 		        		cell.setCellValue(Integer.parseInt(carList.get(i).getC1()));
 		        		
 		        		cell = row.createCell(3);
 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 		        		cell.setCellValue(carList.get(i).getT1());
 		        		
 		        		cell = row.createCell(4);
 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 		        		cell.setCellValue(Integer.parseInt(carList.get(i).getC2()));
 		        		
 		        		cell = row.createCell(5);
 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 		        		cell.setCellValue(carList.get(i).getT2());
 		        		
 		        		cell = row.createCell(6);
 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 		        		cell.setCellValue(Integer.parseInt(carList.get(i).getC3()));
 		        		
 		        		cell = row.createCell(7);
 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 		        		cell.setCellValue(carList.get(i).getT3());
 		        		
 		        		cell = row.createCell(8);
 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 		        		cell.setCellValue(Integer.parseInt(carList.get(i).getC4()));
 		        		
 		        		cell = row.createCell(9);
 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 		        		cell.setCellValue(carList.get(i).getT4());
 		        		
 		        		cell = row.createCell(10);
 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 		        		cell.setCellValue(Integer.parseInt(carList.get(i).getC5()));
 		        		
 		        		cell = row.createCell(11);
 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 		        		cell.setCellValue(carList.get(i).getT5());
 		        		
 		        		if(recTitles.size() == 9){
 		        			cell = row.createCell(12);
 	 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 	 		        		cell.setCellValue(Integer.parseInt(carList.get(i).getC6()));
 	 		        		
 	 		        		cell = row.createCell(13);
 	 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 	 		        		cell.setCellValue(carList.get(i).getT6());
 	 		        		
 	 		        		cell = row.createCell(14);
 	 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 	 		        		cell.setCellValue(Integer.parseInt(carList.get(i).getSum_count()));
 	 		        		
 	 		        		cell = row.createCell(15);
 	 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 	 		        		cell.setCellValue(carList.get(i).getSum_time());
 		        		}else{
 		        			cell = row.createCell(12);
 	 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 	 		        		cell.setCellValue(Integer.parseInt(carList.get(i).getSum_count()));
 	 		        		
 	 		        		cell = row.createCell(13);
 	 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 	 		        		cell.setCellValue(carList.get(i).getSum_time());
 		        		}
 		        		
 		        	}
 		        	
 		        }
 		        
 	        }
        	
    	}else if(type.equals("deptInfo")){
    		//폰트 설정
        	Font Black10 = workbook.createFont();
        	Black10.setFontName("맑은고딕"); //글씨체
        	Black10.setFontHeight((short)(10*20)); //사이즈
        	
        	//4-1.셀 스타일 및 폰트 설정(일반 텍스트)
        	CellStyle styleOfBoardFontBlack10 = workbook.createCellStyle();
        	//정렬
        	styleOfBoardFontBlack10.setAlignment(CellStyle.ALIGN_CENTER); //가운데 정렬
        	styleOfBoardFontBlack10.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //높이 가운데 정렬
        	//테두리 선 (우,좌,위,아래)
        	styleOfBoardFontBlack10.setBorderRight(HSSFCellStyle.BORDER_THIN);
        	styleOfBoardFontBlack10.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        	styleOfBoardFontBlack10.setBorderTop(HSSFCellStyle.BORDER_THIN);
        	styleOfBoardFontBlack10.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        	//폰트 설정 (위 폰트 사용)
        	styleOfBoardFontBlack10.setFont(Black10);
        	
        	Sheet sheet = workbook.getSheet("Sheet1");
        	if(sheet == null){
        		sheet = workbook.createSheet("Sheet1");
        	}
        	
        	Row row = null;
	        Cell cell =  null;
	        int rowCount = 0;
	        int cellCount = 0;
	        
	        List<OperationCar> carList = (List<OperationCar>) model.get("carList"); //리스트
	        List<String> recNames = (List<String>) model.get("recNames"); //컬럼Id
	        List<String> recTitles = (List<String>) model.get("recTitles"); //엑셀헤더
    		
	        sheet.setColumnWidth((short)0, (short)5000);
        	sheet.setColumnWidth((short)1, (short)5000);

        	sheet.addMergedRegion(new CellRangeAddress(0,1,0,0));
        	sheet.addMergedRegion(new CellRangeAddress(0,0,1,2));
        	sheet.addMergedRegion(new CellRangeAddress(0,0,3,4));
        	sheet.addMergedRegion(new CellRangeAddress(0,0,5,6));
        	sheet.addMergedRegion(new CellRangeAddress(0,0,7,8));
        	sheet.addMergedRegion(new CellRangeAddress(0,0,9,10));
        	if(recTitles.size() == 8){
        		sheet.addMergedRegion(new CellRangeAddress(0,0,11,12));
        	}
        	
        	row = sheet.createRow(rowCount++);
        	if(!recTitles.isEmpty()){
        		cell = row.createCell(0);
        		cell.setCellValue(recTitles.get(0));
        		
        		int rowNum = 0;
 		        for(int i=1; i<=2*(recTitles.size()-2); i+=2){
 		        	cell = row.createCell(i);
 		        	cell.setCellStyle(styleOfBoardFontBlack10);
 		        	String week = recTitles.get(i-rowNum);
 		        	week.replaceAll("\n","<br style='mso-data-placement:same-cell;'>");
 		        	cell.setCellValue(URLDecoder.decode(week,"utf-8"));
 		        	CellStyle cs = workbook.createCellStyle();
 			        Font ft = workbook.createFont();
 			        cs.setWrapText(true);
 			        ft.setFontHeight((short)(10*20));
 			        cs.setAlignment(CellStyle.ALIGN_CENTER);
 			        cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
 			        cs.setBorderTop(HSSFCellStyle.BORDER_THIN);
 			        cs.setFont(ft);
 			        cell.setCellStyle(cs);
 			        row.setHeightInPoints((2*sheet.getDefaultRowHeightInPoints()));
 			        
 			        rowNum++;
 		        }
 		        
 		        if(recTitles.size() == 8){
 		        	sheet.addMergedRegion(new CellRangeAddress(0,0,13,14));
 		        	cell = row.createCell(13);
 		        	cell.setCellValue(recTitles.get(7));
 		        }else{
 		        	sheet.addMergedRegion(new CellRangeAddress(0,0,11,12));
 		        	cell = row.createCell(11);
 		        	cell.setCellValue(recTitles.get(6));
 		        }
 		        
 		        row = sheet.createRow(rowCount++);
 		        cellCount = 0;
 		        
 		        for(int j=1; j<=2*(recTitles.size()-1); j+=2){
 		        	cell = row.createCell(j);
 		        	cell.setCellValue("배차횟수");
 		        	cell = row.createCell(j+1);
 		        	cell.setCellValue("배차시간");
 		        }
 		        
 		       if(!carList.isEmpty()){
 		        	for(int i=0; i<carList.size(); i++){
 		        		row = sheet.createRow(rowCount++);
 		        		cellCount = 0;
 		        		
 		        		cell = row.createCell(0);
 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 		        		cell.setCellValue(carList.get(i).getDeptname());
 		        		
 		        		cell = row.createCell(1);
 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 		        		cell.setCellValue(Integer.parseInt(carList.get(i).getC1()));
 		        		
 		        		cell = row.createCell(2);
 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 		        		cell.setCellValue(carList.get(i).getT1());
 		        		
 		        		cell = row.createCell(3);
 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 		        		cell.setCellValue(Integer.parseInt(carList.get(i).getC2()));
 		        		
 		        		cell = row.createCell(4);
 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 		        		cell.setCellValue(carList.get(i).getT2());
 		        		
 		        		cell = row.createCell(5);
 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 		        		cell.setCellValue(Integer.parseInt(carList.get(i).getC3()));
 		        		
 		        		cell = row.createCell(6);
 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 		        		cell.setCellValue(carList.get(i).getT3());
 		        		
 		        		cell = row.createCell(7);
 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 		        		cell.setCellValue(Integer.parseInt(carList.get(i).getC4()));
 		        		
 		        		cell = row.createCell(8);
 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 		        		cell.setCellValue(carList.get(i).getT4());
 		        		
 		        		cell = row.createCell(9);
 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 		        		cell.setCellValue(Integer.parseInt(carList.get(i).getC5()));
 		        		
 		        		cell = row.createCell(10);
 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 		        		cell.setCellValue(carList.get(i).getT5());
 		        		
 		        		if(recTitles.size() == 8){
 		        			cell = row.createCell(11);
 	 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 	 		        		cell.setCellValue(Integer.parseInt(carList.get(i).getC6()));
 	 		        		
 	 		        		cell = row.createCell(12);
 	 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 	 		        		cell.setCellValue(carList.get(i).getT6());
 	 		        		
 	 		        		cell = row.createCell(13);
 	 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 	 		        		cell.setCellValue(Integer.parseInt(carList.get(i).getSum_count()));
 	 		        		
 	 		        		cell = row.createCell(14);
 	 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 	 		        		cell.setCellValue(carList.get(i).getSum_time());
 		        		}else{
 		        			cell = row.createCell(11);
 	 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 	 		        		cell.setCellValue(Integer.parseInt(carList.get(i).getSum_count()));
 	 		        		
 	 		        		cell = row.createCell(12);
 	 		        		cell.setCellStyle(styleOfBoardFontBlack10);
 	 		        		cell.setCellValue(carList.get(i).getSum_time());
 		        		}
 		        		
 		        	}
 		        	
 		        }
 		        
 	        }
    		
    	}else if(type.equals("weekPlan")){
    		//폰트 설정
        	Font Black10 = workbook.createFont();
        	Black10.setFontName("맑은고딕"); //글씨체
        	Black10.setFontHeight((short)(10*20)); //사이즈
        	
        	//4-1.셀 스타일 및 폰트 설정(일반 텍스트)
        	CellStyle styleOfBoardFontBlack10 = workbook.createCellStyle();
        	//정렬
        	styleOfBoardFontBlack10.setAlignment(CellStyle.ALIGN_CENTER); //가운데 정렬
        	styleOfBoardFontBlack10.setVerticalAlignment(CellStyle.VERTICAL_CENTER); //높이 가운데 정렬
        	//테두리 선 (우,좌,위,아래)
        	styleOfBoardFontBlack10.setBorderRight(HSSFCellStyle.BORDER_THIN);
        	styleOfBoardFontBlack10.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        	styleOfBoardFontBlack10.setBorderTop(HSSFCellStyle.BORDER_THIN);
        	styleOfBoardFontBlack10.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        	//폰트 설정 (위 폰트 사용)
        	styleOfBoardFontBlack10.setFont(Black10);
        	
        	Sheet sheet = workbook.getSheet("Sheet1");
        	if(sheet == null){
        		sheet = workbook.createSheet("Sheet1");
        	}
        	
        	Row row = null;
	        Cell cell =  null;
	        int rowCount = 0;
	        int cellCount = 0;
	        
	        List<WStatus> weekList = (List<WStatus>) model.get("weekList"); //리스트
	        List<String> recNames = (List<String>) model.get("recNames"); //컬럼Id
	        List<String> recTitles = (List<String>) model.get("recTitles"); //엑셀헤더
    		
        	sheet.setColumnWidth((short)1, (short)4000);
        	sheet.setColumnWidth((short)2, (short)4000);
        	sheet.setColumnWidth((short)3, (short)4000);
        	sheet.setColumnWidth((short)5, (short)4000);
	        
        	row = sheet.createRow(rowCount++);
	        if(!recTitles.isEmpty()){
		        for(int i=0; i<recTitles.size(); i++){
		        	cell = row.createCell(i);
		        	cell.setCellStyle(styleOfBoardFontBlack10);
		        	String week = recTitles.get(i);
		        	week.replaceAll("\n","<br style='mso-data-placement:same-cell;'>");
		        	cell.setCellValue(URLDecoder.decode(week,"utf-8"));
		        	CellStyle cs = workbook.createCellStyle();
			        Font ft = workbook.createFont();
			        cs.setWrapText(true);
			        ft.setFontHeight((short)(10*20));
			        cs.setAlignment(CellStyle.ALIGN_CENTER);
			        cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			        cs.setBorderTop(HSSFCellStyle.BORDER_THIN);
			        cs.setFont(ft);
			        cell.setCellStyle(cs);
			        row.setHeightInPoints((2*sheet.getDefaultRowHeightInPoints()));
		        }
	        }
    	}

    }
    
    
 
        
 
        /*if(target.equals("booksDetail")){
 
            List<BooksDetail> booksDetailList = (List<BooksDetail>) model.get("excelList");
 
            //Sheet 생성
            Sheet sheet = workbook.createSheet(target);
            Row row = null;
            int rowCount = 0;
            int cellCount = 0;
 
            // 제목 Cell 생성
            row = sheet.createRow(rowCount++);
 
            row.createCell(cellCount++).setCellValue("detail_id");
            row.createCell(cellCount++).setCellValue("id");
            row.createCell(cellCount++).setCellValue("publish_date");
            row.createCell(cellCount++).setCellValue("best_seller_yn");
 
            // 데이터 Cell 생성
            for (BooksDetail bookDetail : booksDetailList) {
                row = sheet.createRow(rowCount++);
                cellCount = 0;
                row.createCell(cellCount++).setCellValue(bookDetail.getDetail_id());
                row.createCell(cellCount++).setCellValue(bookDetail.getId());
                row.createCell(cellCount++).setCellValue(bookDetail.getPublish_date());
                row.createCell(cellCount++).setCellValue(bookDetail.getBest_seller_yn());
 
            }
 
        }
 
    }*/
 
    @Override
    protected Workbook createWorkbook() {
        return new XSSFWorkbook();
    }
 
}