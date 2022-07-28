package com.mnsoft.upmu.common.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.springframework.beans.factory.annotation.Autowired;

import com.mnsoft.upmu.overseasBusinessTrip.vo.OverseasBusinessTrip;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.awt.Color;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class OverseasBusinessTripDeptExcelDownloadView extends AbstractExcelView {

	
	CellStyle dateCellFormat; // no_fill
	XSSFCellStyle dateCellFormat2; // lightBlue
	XSSFCellStyle dateCellFormat3; // lightGray
	
	// 부서 틀을 위한 필드
	Integer level4SumCnt = 1, level3SumCnt = 1, level4SumStd = 0, level3SumStd = 0;
	String savedMergetype = "";
	boolean level4SumOut = false, level3SumOut = false;
	
	// 부서별 출장현황 정보를 위한 필드
	Integer level4SumTotCnt = 0, level4SumColL0 = 0, level4SumColL1 = 0, level4SumColL2 = 0, level4SumColL3 = 0, level4SumColL4 = 0, level4SumColL5 = 0, level4SumColL6 = 0, level4SumColL7 = 0, level4SumColL8 = 0, level4SumColL9 = 0, level4SumColL10 = 0, level4SumColL11 = 0, level4SumColL12 = 0, level4SumColL13 = 0, level4SumColL14 = 0, level4SumColL15 = 0, level4SumColL16 = 0, level4SumColL17 = 0, level4SumColL18 = 0, level4SumColL19 = 0, level4SumColL20 = 0, level4SumColL21 = 0, level4SumColL22 = 0, level4SumColL23 = 0, level4SumColL24 = 0, level4SumColL25 = 0, level4SumColL26 = 0, level4SumColL27 = 0, level4SumColL28 = 0, level4SumColL29 = 0, level4SumColL30 = 0, level4SumColL31 = 0, level4SumColL32 = 0, level4SumColL33 = 0, level4SumColL34 = 0, level4SumColL35 = 0, level4SumColL36 = 0, level4SumColL37 = 0, level4SumColL38 = 0, level4SumColL39 = 0;
	Integer level3SumTotCnt = 0, level3SumColL0 = 0, level3SumColL1 = 0, level3SumColL2 = 0, level3SumColL3 = 0, level3SumColL4 = 0, level3SumColL5 = 0, level3SumColL6 = 0, level3SumColL7 = 0, level3SumColL8 = 0, level3SumColL9 = 0, level3SumColL10 = 0, level3SumColL11 = 0, level3SumColL12 = 0, level3SumColL13 = 0, level3SumColL14 = 0, level3SumColL15 = 0, level3SumColL16 = 0, level3SumColL17 = 0, level3SumColL18 = 0, level3SumColL19 = 0, level3SumColL20 = 0, level3SumColL21 = 0, level3SumColL22 = 0, level3SumColL23 = 0, level3SumColL24 = 0, level3SumColL25 = 0, level3SumColL26 = 0, level3SumColL27 = 0, level3SumColL28 = 0, level3SumColL29 = 0, level3SumColL30 = 0, level3SumColL31 = 0, level3SumColL32 = 0, level3SumColL33 = 0, level3SumColL34 = 0, level3SumColL35 = 0, level3SumColL36 = 0, level3SumColL37 = 0, level3SumColL38 = 0, level3SumColL39 = 0;
	int lastCol = 0;
	
	// 조회한 부서
	String searchDept = "";
	
	protected void buildExcelDocument(Map modelMap, Workbook infWorkBook,
			HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		


		SXSSFWorkbook workBook = (SXSSFWorkbook) infWorkBook;

		String fileName = (String) modelMap.get("fileName");

		if (fileName == null || "".equals(fileName)) {
			fileName = "downloadFile";
		}

		fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");

		if (!"xlsx".equalsIgnoreCase(getFileExt(fileName))) {
			fileName = fileName + ".xlsx";
		}

		String docTitle = (String) modelMap.get("docTitle");

		List recTitles = (List) modelMap.get("recTitles");

		List recNames = (List) modelMap.get("recNames");

		// 셀 병합 포함하여 마지막 셀의 위치 고려. 
		lastCol = recNames.size() + 1; 

		List records = (List) modelMap.get("records");

		setHeader(res, fileName);

//		SXSSFSheet sheet = createSheet(workBook, "Sheet1");
		
		
		
		
		Font font = workBook.createFont();
		//font.setItalic(true);
		font.setFontName("맑은 고딕");;
		font.setFontHeightInPoints((short) 10);
		dateCellFormat = workBook.createCellStyle();
		dateCellFormat2 = (XSSFCellStyle)workBook.createCellStyle();
		dateCellFormat3 = (XSSFCellStyle)workBook.createCellStyle();
		dateCellFormat.setFont(font);
		dateCellFormat2.setFont(font);
		dateCellFormat3.setFont(font);
		
		dateCellFormat2.setFillForegroundColor(new XSSFColor(new Color(222, 230, 239)));
		dateCellFormat3.setFillForegroundColor(new XSSFColor(new Color(234, 236, 238)));
		
		dateCellFormat2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		dateCellFormat3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		// 정렬
		dateCellFormat.setAlignment(CellStyle.ALIGN_CENTER);
		dateCellFormat.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		dateCellFormat2.setAlignment(CellStyle.ALIGN_CENTER);
		dateCellFormat2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		dateCellFormat3.setAlignment(CellStyle.ALIGN_CENTER);
		dateCellFormat3.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		
		SXSSFSheet sheet = workBook.createSheet("Sheet1");	
	
		

		int colNum = 0;

		//타이틀 설정
		if (docTitle != null && !"".equals(docTitle)) {
			createDocTitle(sheet, 0, colNum++, docTitle);
		}

		//타이틀 설정
		if (recTitles != null && recTitles.size() > 0) {
			createTableTitle(sheet, colNum++, recTitles);
		}
		
		//데이터쓰기
		createTableData(sheet, colNum++, recNames, records);
	}

	private void setHeader(HttpServletResponse res, String fileName) {
		res.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		res.setHeader("Content-Disposition",
				"attachment; filename=\"" + fileName + "\";charset=\"UTF-8\"");

		res.setHeader("Content-Transfer-Encoding", "binary");
	}

	private SXSSFSheet createSheet(SXSSFWorkbook workBook, String sheetName) {

		SXSSFSheet sheet;

		sheet = workBook.createSheet();

		workBook.setSheetName(0, sheetName);

		return sheet;

	}

	private void createDocTitle(SXSSFSheet sheet, int rowNum, int colNum,
			String docTitle) {

		SXSSFRow row = sheet.createRow(rowNum);

		SXSSFCell cell = row.createCell(colNum, SXSSFCell.CELL_TYPE_STRING);
		
		
		

		
			
			

		cell.setCellValue(docTitle);
		

	}

	private void createTableTitle(SXSSFSheet sheet, int rowNum, List titles) {

		SXSSFRow row = sheet.createRow(rowNum);
		
		int col = 0;
		String temp = "";
		int tempCnt = 0;
		for (Object o : titles) {

			String s = (String) o;
			
			// 부서 A1:C2 합치기.
			if(s.equals("부서")) {
				sheet.addMergedRegion(new CellRangeAddress(0,0,0,2));
				SXSSFCell cell = row.createCell(col, SXSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(dateCellFormat3);
				cell.setCellValue(s);
				
				sheet.setColumnWidth(col, 4000);
				col = 2;
			} else if(s.equals("출장지입력")) {
				SXSSFCell cell = row.createCell(lastCol, SXSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(dateCellFormat3);
				cell.setCellValue("기타");
				sheet.setColumnWidth(lastCol, 4000);
			} else {
				SXSSFCell cell = row.createCell(col, SXSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(dateCellFormat3);
				cell.setCellValue(s);
				sheet.setColumnWidth(col, 4000);
			}
			
			
			sheet.setColumnWidth(col, 4000);
			if(!s.equals("출장지입력")) {
				col++;
			}
		}
		

	}

	private void createTableData(SXSSFSheet sheet, int startRowNum,
			List recNames, List records) throws Exception {
	

		int rowNum = startRowNum;

		List methodNames = null;

		if (records == null) {
			return;
		}
		
		String temp = "";
		int tempCnt = 0;
		List recordsTemp = new ArrayList();
		OverseasBusinessTrip objTemp = new OverseasBusinessTrip();

		for (Object o : records) {
			SXSSFRow row = sheet.createRow(rowNum);
			if (o instanceof Map) {
				setTableRowDataMap(row, (Map) o, recNames);
			} else {
				if (methodNames == null) {
					methodNames = getMethodNames(recNames);
				}
				if(records.get(0).equals(o)) {
					objTemp = (OverseasBusinessTrip)o;
					searchDept = objTemp.getDeptname();
				}
				setTableRowDataVO(sheet, row, o, methodNames, rowNum);
			}
			rowNum++;
			
			if(level4SumOut) {
				rowNum++;
				level4SumOut = false;
			}
			if(level3SumOut) {
				rowNum++;
				level3SumOut = false;
			}
		}
	}

	private void setTableRowDataMap(SXSSFRow row, Map map, List recNames) {

		int col = 0;

		for (Object key : recNames) {

			Object val = map.get(key);

			SXSSFCell cell = row.createCell(col, SXSSFCell.CELL_TYPE_STRING);

			cell.setCellValue(val.toString());
			cell.setCellStyle(dateCellFormat);

			col++;

		}

	}

	private List getMethodNames(List recNames) {

		List list = new ArrayList();
		for (String recName : (List<String>) recNames) {
			list.add(getNewMethodName(recName));
		}

		return list;

	}

	private String getNewMethodName(String oldMethodName) {
		String first = oldMethodName.substring(0, 1);

		return "get" + first.toUpperCase() + oldMethodName.substring(1);
	}

	private void setTableRowDataVO(SXSSFSheet sheet, SXSSFRow row, Object vo, List recNames, int rowNum)
			throws Exception {

		Class cls = vo.getClass();
		
		int col = 0;
		Method method1;
		String currentMergeType = "", row_merge_cnt = "";
		
		// 출장지 여분까지 고려하여 10개 생성. 출장지가 더 늘어난다면 해당 파일 수전되어야 함.
		Integer tot_cnt = 0, l0 = 0, l1 = 0, l2 = 0, l3 = 0, l4 = 0, l5 = 0, l6 = 0, l7 = 0, l8 = 0, l9 = 0, l10 = 0, l11 = 0, l12 = 0, l13 = 0, l14 = 0, l15 = 0, l16 = 0, l17 = 0, l18 = 0, l19 = 0, l20 = 0, l21 = 0, l22 = 0, l23 = 0, l24 = 0, l25 = 0, l26 = 0, l27 = 0, l28 = 0, l29 = 0, l30 = 0, l31 = 0, l32 = 0, l33 = 0, l34 = 0, l35 = 0, l36 = 0, l37 = 0, l38 = 0, l39 = 0; 
		
		// 부서 관련 정보 얻기
		method1 = cls.getMethod("getMerge_type");
		currentMergeType = method1.invoke(vo).toString();
		method1 = cls.getMethod("getRow_merge_cnt");
		row_merge_cnt = method1.invoke(vo).toString();
		
		// 부서별 출장현황 필드 값 얻기
		method1 = cls.getMethod("getTot_cnt");
		tot_cnt = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL0");
		l0 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL1");
		l1 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL2");
		l2 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL3");
		l3 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL4");
		l4 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL5");
		l5 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL6");
		l6 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL7");
		l7 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL8");
		l8 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL9");
		l9 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL10");
		l10 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL11");
		l11 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL12");
		l12 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL13");
		l13 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL14");
		l14 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL15");
		l15 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL16");
		l16 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL17");
		l17 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL18");
		l18 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL19");
		l19 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL20");
		l20 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL21");
		l21 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL22");
		l22 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL23");
		l23 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL24");
		l24 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL25");
		l25 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL26");
		l26 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL27");
		l27 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL28");
		l28 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL29");
		l29 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL30");
		l30 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL31");
		l31 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL32");
		l32 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL33");
		l33 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL34");
		l34 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL35");
		l35 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL36");
		l36 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL37");
		l37 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL38");
		l38 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getL39");
		l39 = nullToZero(method1.invoke(vo).toString());
		
		
		for (String methodName : (List<String>) recNames) {	
			Method method = cls.getMethod(methodName);

			Object val = method.invoke(vo);
			
			if (val == null) {
				val = "";
			}
			/*
			sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum+parentcountCalc, col-1,col-1));
			cell = row.createCell(col-1, SXSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(dateCellFormat);
			cell.setCellValue(val.toString());
			*/
			// 부서 정보 표출
			if(methodName.equals("getDeptname")) {
				if("A".equals(currentMergeType)) {
					// A 타입 : 1LEVEL이거나 2LEVEL 중 3LEVEL 자식이 없는 경우, EXCEL A열~C열(EX:현대앰엔소프트)
					sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, col, col + 2));
					SXSSFCell cell = row.createCell(col, SXSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(dateCellFormat);
					cell.setCellValue(val.toString());
					col += 2;
				} else if("B".equals(currentMergeType)) {
					// B 타입 : 4LEVEL 자식이 하나도 없는 3LEVEL, EXCEL C열(EX:품질혁신팀)
					if(!searchDept.equals("000100") && searchDept.equals(val.toString())) {
						col = 0;
						sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 2));
						SXSSFCell cell = row.createCell(col, SXSSFCell.CELL_TYPE_STRING);
						cell.setCellStyle(dateCellFormat);
						cell.setCellValue(val.toString());
						col = 2;
					} else {
						col = 2;
						SXSSFCell cell = row.createCell(col, SXSSFCell.CELL_TYPE_STRING);
						cell.setCellStyle(dateCellFormat);
						cell.setCellValue(val.toString());
					}
				} else if("C".equals(currentMergeType)) {
					// C 타입 : 4LEVEL 자식이 없어서 소계를 구하지 않아도 되지만 3LEVEL 형제의 경우 자식이 있어서 소계를 구해야 하는 항목, EXCEL B열~C열(EX:PM팀)
					if(!searchDept.equals("000100") && searchDept.equals(val.toString())) {
						col = 1;
						sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, col-1, col + 1));
						SXSSFCell cell = row.createCell(col-1, SXSSFCell.CELL_TYPE_STRING);
						cell.setCellStyle(dateCellFormat);
						cell.setCellValue(val.toString());
						col++;
					} else {
						col = 1;
						sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, col, col + 1));
						SXSSFCell cell = row.createCell(col, SXSSFCell.CELL_TYPE_STRING);
						cell.setCellStyle(dateCellFormat);
						cell.setCellValue(val.toString());
						col++;
					}
				} else if("D".equals(currentMergeType)) {
					// D 타입 : 합계를 구해야 하는 2LEVEL, EXCEL A열, B열~C열(ROW_MERGE_CNT 구해야 함)(EX:VI사업담당)
					sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + Integer.parseInt(row_merge_cnt) - 1, col, col));
					SXSSFCell cell = row.createCell(col, SXSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(dateCellFormat);
					cell.setCellValue(val.toString());
					col++;
					
					sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, col, col + 1));
					cell = row.createCell(col, SXSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(dateCellFormat);
					cell.setCellValue(val.toString());
					col++;
				} else if("E".equals(currentMergeType)) {
					// E 타입 : 소계를 구해야 하는 3LEVEL, EXCEL B열, C열(ROW_MERGE_CNT 구해야 함)(EX:사업실)
					if(!searchDept.equals("000100") && searchDept.equals(val.toString())) {
						col = 0;
						sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + Integer.parseInt(row_merge_cnt) - 1, col, col+1));
						SXSSFCell cell = row.createCell(col, SXSSFCell.CELL_TYPE_STRING);
						cell.setCellStyle(dateCellFormat);
						cell.setCellValue(val.toString());
						col = 2;
						
						cell = row.createCell(col, SXSSFCell.CELL_TYPE_STRING);
						cell.setCellStyle(dateCellFormat);
						cell.setCellValue(val.toString());
					} else {
						col = 1;
						sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + Integer.parseInt(row_merge_cnt) - 1, col, col));
						SXSSFCell cell = row.createCell(col, SXSSFCell.CELL_TYPE_STRING);
						cell.setCellStyle(dateCellFormat);
						cell.setCellValue(val.toString());
						col++;
						
						cell = row.createCell(col, SXSSFCell.CELL_TYPE_STRING);
						cell.setCellStyle(dateCellFormat);
						cell.setCellValue(val.toString());
					}
				} else if("F".equals(currentMergeType)) {
					// F 타입 : 4LEVEL 자식이 하나도 없는 2LEVEL, EXCEL A열~B열, C열(ROW_MERGE_CNT 구해야 함)(EX:품질관리실)
					sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum + Integer.parseInt(row_merge_cnt) - 1, col, col + 1));
					SXSSFCell cell = row.createCell(col, SXSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(dateCellFormat);
					cell.setCellValue(val.toString());
					col += 2;
					
					cell = row.createCell(col, SXSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(dateCellFormat);
					cell.setCellValue(val.toString());
				} else {
					// G 타입 : 1,2,3 레벨이 전부 존재하는 4레벨, EXCEL C열(EX:사업1팀)
					if(!searchDept.equals("000100") && searchDept.equals(val.toString())) {
						col = 0;
						sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 2));
						SXSSFCell cell = row.createCell(col, SXSSFCell.CELL_TYPE_STRING);
						cell.setCellStyle(dateCellFormat);
						cell.setCellValue(val.toString());
						col = 2;
					} else {
						col = 2;
						SXSSFCell cell = row.createCell(col, SXSSFCell.CELL_TYPE_STRING);
						cell.setCellStyle(dateCellFormat);
						cell.setCellValue(val.toString());
					}
				}
			// 부서별 출장현황
			} else {
				SXSSFCell cell = row.createCell(col, SXSSFCell.CELL_TYPE_STRING);
			
				cell.setCellValue(val.toString());
				cell.setCellStyle(dateCellFormat);
			}
			
			if(col == lastCol) {
				// 소계 계산
				if(level4SumStd == 0 && !"0".equals(row_merge_cnt) && "E".equals(currentMergeType)) {
					level4SumStd = Integer.parseInt(row_merge_cnt);
				}
				
				// 합계 계산
				if(level3SumStd == 0 && !"0".equals(row_merge_cnt) && ("D".equals(currentMergeType) || "F".equals(currentMergeType))) {
					level3SumStd = Integer.parseInt(row_merge_cnt);
					
					// 합계 틀 만들기 위한 타입 저장
					savedMergetype = currentMergeType;
				}
				
				if(level4SumStd > 0){
					level4SumCnt++;
					level4SumTotCnt += tot_cnt;
					level4SumColL0 += l0;
					level4SumColL1 += l1;
					level4SumColL2 += l2;
					level4SumColL3 += l3;
					level4SumColL4 += l4;
					level4SumColL5 += l5;
					level4SumColL6 += l6;
					level4SumColL7 += l7;
					level4SumColL8 += l8;
					level4SumColL9 += l9;
					level4SumColL10 += l10;
					level4SumColL11 += l11;
					level4SumColL12 += l12;
					level4SumColL13 += l13;
					level4SumColL14 += l14;
					level4SumColL15 += l15;
					level4SumColL16 += l16;
					level4SumColL17 += l17;
					level4SumColL18 += l18;
					level4SumColL19 += l19;
					level4SumColL20 += l20;
					level4SumColL21 += l21;
					level4SumColL22 += l22;
					level4SumColL23 += l23;
					level4SumColL24 += l24;
					level4SumColL25 += l25;
					level4SumColL26 += l26;
					level4SumColL27 += l27;
					level4SumColL28 += l28;
					level4SumColL29 += l29;
					level4SumColL30 += l30;
					level4SumColL31 += l31;
					level4SumColL32 += l32;
					level4SumColL33 += l33;
					level4SumColL34 += l34;
					level4SumColL35 += l35;
					level4SumColL36 += l36;
					level4SumColL37 += l37;
					level4SumColL38 += l38;
					level4SumColL39 += l39;
					
					if(level4SumCnt == level4SumStd){
						rowNum++;
						level4SumOut = true;
						SXSSFRow row1 = sheet.createRow(rowNum);
						
						// 소계 표시
						col = 2;
						SXSSFCell cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
						cell.setCellStyle(dateCellFormat3);
						cell.setCellValue("소계");
						
						col++;
					
						// 부서별 소계 표시
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumTotCnt);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL0);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL1);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL2);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL3);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL4);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL5);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL6);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL7);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL8);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL9);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL10);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL11);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL12);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL13);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL14);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL15);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL16);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL17);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL18);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL19);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL20);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL21);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL22);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL23);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL24);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL25);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL26);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL27);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL28);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL29);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL30);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL31);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL32);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL33);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL34);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL35);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL36);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL37);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL38);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(level4SumColL39);
							col++;
						}
						
						// 부서별 소계 정보 리셋
						level3SumCnt++;
						level4SumStd = 0;
						level4SumCnt = 1;
						level4SumColL0 =0; 
						level4SumColL1 =0; 
						level4SumColL2 =0; 
						level4SumColL3 =0; 
						level4SumColL4 =0; 
						level4SumColL5 =0; 
						level4SumColL6 =0;
						level4SumColL7 =0;
						level4SumColL8 =0;
						level4SumColL9 =0;
						level4SumColL10 =0; 
						level4SumColL11 =0; 
						level4SumColL12 =0; 
						level4SumColL13 =0; 
						level4SumColL14 =0; 
						level4SumColL15 =0; 
						level4SumColL16 =0;
						level4SumColL17 =0;
						level4SumColL18 =0;
						level4SumColL19 =0;
						level4SumColL20 =0; 
						level4SumColL21 =0; 
						level4SumColL22 =0; 
						level4SumColL23 =0; 
						level4SumColL24 =0; 
						level4SumColL25 =0; 
						level4SumColL26 =0;
						level4SumColL27 =0;
						level4SumColL28 =0;
						level4SumColL29 =0;
						level4SumColL30 =0; 
						level4SumColL31 =0; 
						level4SumColL32 =0; 
						level4SumColL33 =0; 
						level4SumColL34 =0; 
						level4SumColL35 =0; 
						level4SumColL36 =0;
						level4SumColL37 =0;
						level4SumColL38 =0;
						level4SumColL39 =0;
					}
				}
				
				if(level3SumStd > 0){
					level3SumCnt++;
					level3SumTotCnt += tot_cnt;
					level3SumColL0 += l0;
					level3SumColL1 += l1;
					level3SumColL2 += l2;
					level3SumColL3 += l3;
					level3SumColL4 += l4;
					level3SumColL5 += l5;
					level3SumColL6 += l6;
					level3SumColL7 += l7;
					level3SumColL8 += l8;
					level3SumColL9 += l9;
					level3SumColL10 += l10;
					level3SumColL11 += l11;
					level3SumColL12 += l12;
					level3SumColL13 += l13;
					level3SumColL14 += l14;
					level3SumColL15 += l15;
					level3SumColL16 += l16;
					level3SumColL17 += l17;
					level3SumColL18 += l18;
					level3SumColL19 += l19;
					level3SumColL20 += l20;
					level3SumColL21 += l21;
					level3SumColL22 += l22;
					level3SumColL23 += l23;
					level3SumColL24 += l24;
					level3SumColL25 += l25;
					level3SumColL26 += l26;
					level3SumColL27 += l27;
					level3SumColL28 += l28;
					level3SumColL29 += l29;
					level3SumColL30 += l30;
					level3SumColL31 += l31;
					level3SumColL32 += l32;
					level3SumColL33 += l33;
					level3SumColL34 += l34;
					level3SumColL35 += l35;
					level3SumColL36 += l36;
					level3SumColL37 += l37;
					level3SumColL38 += l38;
					level3SumColL39 += l39;
					
					if(level3SumCnt == level3SumStd){
						rowNum++;
						level3SumOut = true;
						SXSSFRow row1 = sheet.createRow(rowNum);
						
						// 합계 표시
						if("F".equals(savedMergetype)){
							col = 2;
							SXSSFCell cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue("합계");
						} else {
							col = 1;
							
							// 셀 병합
							sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, col,col+1));
							
							SXSSFCell cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue("합계");
						}
						
						// 부서별 합계 표시
						col = 3;
						SXSSFCell cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumTotCnt);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL0);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL1);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL2);
							col++;
						}
						if(col <= lastCol) {	
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL3);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL4);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL5);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL6);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL7);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL8);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL9);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL10);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL11);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL12);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL13);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL14);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL15);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL16);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL17);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL18);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL19);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL20);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL21);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL22);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL23);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL24);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL25);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL26);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL27);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL28);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL29);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL30);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL31);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL32);
							col++;
						}
						if(col <= lastCol) {					
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL33);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL34);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL35);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL36);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL37);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL38);
							col++;
						}
						if(col <= lastCol) {
							cell = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue(level3SumColL39);
							col++;
						}
						
						// 부서별 합계 정보 리셋
						savedMergetype = "";
						level3SumStd = 0;
						level3SumCnt = 1;
						level3SumCnt = 1;
						level3SumColL0 =0; 
						level3SumColL1 =0; 
						level3SumColL2 =0; 
						level3SumColL3 =0; 
						level3SumColL4 =0; 
						level3SumColL5 =0; 
						level3SumColL6 =0;
						level3SumColL7 =0;
						level3SumColL8 =0;
						level3SumColL9 =0;
						level3SumColL10 =0; 
						level3SumColL11 =0; 
						level3SumColL12 =0; 
						level3SumColL13 =0; 
						level3SumColL14 =0; 
						level3SumColL15 =0; 
						level3SumColL16 =0;
						level3SumColL17 =0;
						level3SumColL18 =0;
						level3SumColL19 =0;
						level3SumColL20 =0; 
						level3SumColL21 =0; 
						level3SumColL22 =0; 
						level3SumColL23 =0; 
						level3SumColL24 =0; 
						level3SumColL25 =0; 
						level3SumColL26 =0;
						level3SumColL27 =0;
						level3SumColL28 =0;
						level3SumColL29 =0;
						level3SumColL30 =0; 
						level3SumColL31 =0; 
						level3SumColL32 =0; 
						level3SumColL33 =0; 
						level3SumColL34 =0; 
						level3SumColL35 =0; 
						level3SumColL36 =0;
						level3SumColL37 =0;
						level3SumColL38 =0;
						level3SumColL39 =0;
					}
				}
			}
			col++;
		}
	}
	public  String getFileExt(String fileName) {

		//
		if (fileName.lastIndexOf(".") == -1) {
			return "";
		}
		return fileName.substring(fileName.lastIndexOf(".") + 1,
				fileName.length());
	}
	
	public static Integer nullToZero(String str) {
        if(str == null || str.trim().isEmpty())
            return 0;
        else 
        	return Integer.parseInt(str.trim());
    }
}
