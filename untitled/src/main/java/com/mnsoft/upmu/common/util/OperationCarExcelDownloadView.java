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

import com.mnsoft.upmu.operationCar.vo.OperationCar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.awt.Color;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class OperationCarExcelDownloadView extends AbstractExcelView {

	
	CellStyle dateCellFormat; // no_fill
	XSSFCellStyle dateCellFormat2; // lightBlue
	XSSFCellStyle dateCellFormat3; // lightGray
	
	// 부서 틀을 위한 필드
	Integer level4SumCnt = 1, level3SumCnt = 1, level4SumStd = 0, level3SumStd = 0;
	String savedMergetype = "";
	boolean level4SumOut = false, level3SumOut = false;
	
	// 부서별 배차현황을 위한 필드
	Integer level4SumCol1 = 0, level4SumCol2 = 0;
	Integer level3SumCol1 = 0, level3SumCol2 = 0;
	
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
		
		// 제목이 두줄이므로
		colNum++;
		
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

		SXSSFRow row = sheet.createRow(rowNum++);
		SXSSFRow row1 = sheet.createRow(rowNum);
		int col = 0;
		String temp = "";
		int tempCnt = 0;
		int forCnt = 0;
		boolean forLast = false;
		boolean forDept = true;
		if(((String)titles.get(0)).equals("이름")) {
			forDept = false;
		}
			
		for (Object o : titles) {
			forCnt++;
			if(forCnt == titles.size()) {
				forLast = true;
			}
			String s = (String) o;
			
			
			// 부서 A1:C2 합치기.
			if(s.equals("부서")) {
				sheet.addMergedRegion(new CellRangeAddress(0,1,0,2));
				SXSSFCell cell = row.createCell(col, SXSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(dateCellFormat3);
				cell.setCellValue(s);
				
				sheet.setColumnWidth(col, 4000);
				col++;
				sheet.setColumnWidth(col, 4000);
			}
			
			if(!s.contains("_")) {
				if(tempCnt != 0) {
					sheet.addMergedRegion(new CellRangeAddress(0,0,col-tempCnt,col));
					temp = "";
					tempCnt = 0;
				}
				if(!s.contains("부서")) {
					if("이름".equals(s) && !forDept) {
						sheet.addMergedRegion(new CellRangeAddress(0,1,col,col));
						SXSSFCell cell = row.createCell(col, SXSSFCell.CELL_TYPE_STRING);
						cell.setCellStyle(dateCellFormat3);
						cell.setCellValue(s);
					} else {
						sheet.addMergedRegion(new CellRangeAddress(0,1,col+1,col+1));
						SXSSFCell cell = row.createCell(col+1, SXSSFCell.CELL_TYPE_STRING);
						cell.setCellStyle(dateCellFormat3);
						cell.setCellValue(s);
					}
				}
			} else {
				if(forDept) {
					if(!temp.equals(s.substring(0, s.indexOf("_"))) || forLast){
						if(tempCnt != 0 || forLast) {
							if(forLast) {
								sheet.addMergedRegion(new CellRangeAddress(0,0,col-tempCnt,col+1));
							} else {
								sheet.addMergedRegion(new CellRangeAddress(0,0,col-tempCnt,col));
								SXSSFCell cell = row.createCell(col + 1, SXSSFCell.CELL_TYPE_STRING);
								cell.setCellStyle(dateCellFormat3);
								cell.setCellValue(s.substring(0, s.indexOf("_")));
							}
							temp = "";
							tempCnt = 0;
						} else {
							SXSSFCell cell = row.createCell(col + 1, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(s.substring(0, s.indexOf("_")));
						}
					} else {
						tempCnt++;
					}
					temp = s.substring(0, s.indexOf("_"));
					
					SXSSFCell cell1 = row1.createCell(col + 1, SXSSFCell.CELL_TYPE_STRING);
					cell1.setCellStyle(dateCellFormat3);
					cell1.setCellValue(s.substring(s.indexOf("_")+1));
				} else {
					if(!temp.equals(s.substring(0, s.indexOf("_"))) || forLast){
						if(tempCnt != 0 || forLast) {
							if(forLast) {
								sheet.addMergedRegion(new CellRangeAddress(0,0,col-tempCnt-1,col));
							} else {
								sheet.addMergedRegion(new CellRangeAddress(0,0,col-tempCnt-1,col-1));
								SXSSFCell cell = row.createCell(col, SXSSFCell.CELL_TYPE_STRING);
								cell.setCellStyle(dateCellFormat3);
								cell.setCellValue(s.substring(0, s.indexOf("_")));
							}
							temp = "";
							tempCnt = 0;
						} else {
							SXSSFCell cell = row.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat3);
							cell.setCellValue(s.substring(0, s.indexOf("_")));
						}
					} else {
						tempCnt++;
					}
					temp = s.substring(0, s.indexOf("_"));
					
					SXSSFCell cell1 = row1.createCell(col, SXSSFCell.CELL_TYPE_STRING);
					cell1.setCellStyle(dateCellFormat3);
					cell1.setCellValue(s.substring(s.indexOf("_")+1));
				}
			}
			sheet.setColumnWidth(col + 1, 4000);
			col++;
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
		OperationCar objTemp = new OperationCar();

		for (Object o : records) {
			SXSSFRow row = sheet.createRow(rowNum);
			if (o instanceof Map) {
				setTableRowDataMap(row, (Map) o, recNames);
			} else {
				if (methodNames == null) {
					methodNames = getMethodNames(recNames);
				}
				if(records.get(0).equals(o)) {
					objTemp = (OperationCar)o;
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
		
		Integer c1 = 0, t2 = 0;
		String t1 = "";
		
		// 부서 관련 정보 얻기
		method1 = cls.getMethod("getMerge_type");
		currentMergeType = method1.invoke(vo).toString();
		method1 = cls.getMethod("getRow_merge_cnt");
		row_merge_cnt = method1.invoke(vo).toString();
		
		// 부서별 배차현황 필드 값 얻기
		method1 = cls.getMethod("getC1");
		c1 = nullToZero(method1.invoke(vo).toString());
		method1 = cls.getMethod("getT1");
		t1 = method1.invoke(vo).toString();
		method1 = cls.getMethod("getT2");
		t2 = nullToZero(method1.invoke(vo).toString());
		
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
			// 부서별 배차현황
			} else {
				SXSSFCell cell = row.createCell(col, SXSSFCell.CELL_TYPE_STRING);
				
				if(methodName.equals("getT1")) {
					cell.setCellValue(toHHMM(t2));
				} else {
					cell.setCellValue(val.toString());
				}
				cell.setCellStyle(dateCellFormat);
			}
			
			if(methodName.equals("getT1")) {
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
					level4SumCol1 += c1;
					level4SumCol2 += t2;
					
					if(level4SumCnt == level4SumStd){
						rowNum++;
						level4SumOut = true;
						row = sheet.createRow(rowNum);
						
						// 소계 표시
						col = 2;
						SXSSFCell cell = row.createCell(col, SXSSFCell.CELL_TYPE_STRING);
						cell.setCellStyle(dateCellFormat3);
						cell.setCellValue("소계");
						
						col++;
					
						// 부서별 소계 표시
						cell = row.createCell(col, SXSSFCell.CELL_TYPE_STRING);
						cell.setCellStyle(dateCellFormat3);
						cell.setCellValue(level4SumCol1);
						col++;
						cell = row.createCell(col, SXSSFCell.CELL_TYPE_STRING);
						cell.setCellStyle(dateCellFormat3);
						cell.setCellValue(toHHMM(level4SumCol2));
						col++;
						
						
						// 부서별 소계 정보 리셋
						level3SumCnt++;
						level4SumStd = 0;
						level4SumCnt = 1;
						level4SumCol1 = 0; 
						level4SumCol2 = 0;
					}
				}
				
				if(level3SumStd > 0){
					level3SumCnt++;
					level3SumCol1 += c1;
					level3SumCol2 += t2;
					
					if(level3SumCnt == level3SumStd){
						rowNum++;
						level3SumOut = true;
						row = sheet.createRow(rowNum);
						
						// 합계 표시
						if("F".equals(savedMergetype)){
							col = 2;
							SXSSFCell cell = row.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue("합계");
						} else {
							col = 1;
							
							// 셀 병합
							sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, col,col+1));
							
							SXSSFCell cell = row.createCell(col, SXSSFCell.CELL_TYPE_STRING);
							cell.setCellStyle(dateCellFormat2);
							cell.setCellValue("합계");
						}
						
						// 부서별 합계 표시
						col = 3;
						SXSSFCell cell = row.createCell(col, SXSSFCell.CELL_TYPE_STRING);
						cell.setCellStyle(dateCellFormat2);
						cell.setCellValue(level3SumCol1);
						col++;
						cell = row.createCell(col, SXSSFCell.CELL_TYPE_STRING);
						cell.setCellStyle(dateCellFormat2);
						cell.setCellValue(toHHMM(level3SumCol2));
						col++;
						
						// 부서별 합계 정보 리셋
						savedMergetype = "";
						level3SumStd = 0;
						level3SumCnt = 1;
						level3SumCol1 =0; 
						level3SumCol2 =0; 
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
	
	public static String toHHMM(Integer data) {
	    String hours   = ((Integer)(data / 60)).toString();
	    String minutes = ((Integer)(data - (Integer.parseInt(hours) * 60))).toString();

	    if(hours.length() == 1) {
	    	hours   = "0"+hours;
	    }
	    if(minutes.length() == 1) {
	    	minutes = "0"+minutes;
	    }
	    
	    return hours+':'+minutes;
	}
}
