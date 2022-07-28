package com.mnsoft.upmu.common.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.awt.Color;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class TeamExcelDownloadView extends AbstractExcelView {

	
	CellStyle dateCellFormat; // no_fill
	XSSFCellStyle dateCellFormat2; // lightBlue
	XSSFCellStyle dateCellFormat3; // lightGreen
	XSSFCellStyle dateCellFormat4; // yellow
	XSSFCellStyle dateCellFormat5; // lightOrange
	XSSFCellStyle dateCellFormat6; // red
	Workbook flagWorkBook;
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
		dateCellFormat4 = (XSSFCellStyle)workBook.createCellStyle();
		dateCellFormat5 = (XSSFCellStyle)workBook.createCellStyle();
		dateCellFormat6 = (XSSFCellStyle)workBook.createCellStyle();
		dateCellFormat.setFont(font);
		dateCellFormat2.setFont(font);
		dateCellFormat3.setFont(font);
		dateCellFormat4.setFont(font);
		dateCellFormat5.setFont(font);
		dateCellFormat6.setFont(font);
		
		dateCellFormat2.setFillForegroundColor(new XSSFColor(new Color(97, 150, 190)));
		dateCellFormat3.setFillForegroundColor(new XSSFColor(new Color(219, 255, 213)));
		dateCellFormat4.setFillForegroundColor(new XSSFColor(new Color(255, 255, 25)));
		dateCellFormat5.setFillForegroundColor(new XSSFColor(new Color(255, 94, 0)));
		dateCellFormat6.setFillForegroundColor(new XSSFColor(new Color(255, 0, 0)));
		
		dateCellFormat2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		dateCellFormat3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		dateCellFormat4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		dateCellFormat5.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		dateCellFormat6.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		flagWorkBook = workBook;
		
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

		for (Object o : titles) {

			String s = (String) o;

			SXSSFCell cell = row.createCell(col, SXSSFCell.CELL_TYPE_STRING);
			
			if(s.equals("w1") || s.equals("w2") || s.equals("w3") || s.equals("w4") || s.equals("w5") || s.equals("w6") || s.equals("평균")) {
				cell.setCellStyle(dateCellFormat2);	
				cell.setCellValue(s);
			} else if(s.equals("근무시간")) {
				cell.setCellStyle(dateCellFormat4);	
				cell.setCellValue(s);
			} else {
				cell.setCellValue(s);
				cell.setCellStyle(dateCellFormat);
			}
			sheet.setColumnWidth(col, 4000);

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

		for (Object o : records) {

			SXSSFRow row = sheet.createRow(rowNum);

			if (o instanceof Map) {

				setTableRowDataMap(row, (Map) o, recNames);

			} else {

				if (methodNames == null) {

					methodNames = getMethodNames(recNames);

				}

				setTableRowDataVO(row, o, methodNames);

			}

			rowNum++;

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
		int listCnt = 0;
		for (String recName : (List<String>) recNames) {
			list.add(getNewMethodName(recName));
			listCnt++;
		}

		return list;

	}

	private String getNewMethodName(String oldMethodName) {
		String returnValue = "";
		if(oldMethodName.contains("&")) {
			String[] nameSplit = oldMethodName.split("&");
			int splitCnt = 0;
			for(String nameValue : nameSplit) {
				String first = nameValue.substring(0, 1);
				if(splitCnt == 0) {
					returnValue += "get" + first.toUpperCase() + nameValue.substring(1);
				}else {
					returnValue += "&get" + first.toUpperCase() + nameValue.substring(1);
				}
				splitCnt++;
			}
		}else {
			String first = oldMethodName.substring(0, 1);

			returnValue =  "get" + first.toUpperCase() + oldMethodName.substring(1);
		}
		return returnValue;
	}

	private void setTableRowDataVO(SXSSFRow row, Object vo, List recNames)
			throws Exception {

		Class cls = vo.getClass();

		int col = 0;

		for (String methodName : (List<String>) recNames) {
			if(methodName.contains("&")) {
				String[] nameSplit = methodName.split("&");
				int splitCnt = 0;
				Method method = null;
				Method method2 = null;
				String methodVal = "";
				String methodVal2 = "";
					
				for(String nameValue : nameSplit) {
					if(splitCnt == 0) {
						method = cls.getMethod(nameSplit[splitCnt]);
						methodVal = nameSplit[splitCnt];
					}else {
						method2 = cls.getMethod(nameSplit[splitCnt]);
					}
					splitCnt++;
				}
				
				Object val = method.invoke(vo);
				Object val2 = method2.invoke(vo);
				
				SXSSFCell cell = row.createCell(col, SXSSFCell.CELL_TYPE_STRING);
				
				if(methodVal.startsWith("getW")) {
					if(val2.toString() != null && !val2.toString().equals("")) {
						cell.setCellStyle(dateCellFormat3);	
					} else {
						cell.setCellStyle(dateCellFormat);	
					}
				}else {
					if(val2.toString().equals("2")) {
						cell.setCellStyle(dateCellFormat6);	
					}else if(val2.toString().equals("1")) {
						cell.setCellStyle(dateCellFormat5);	
					}else {
						cell.setCellStyle(dateCellFormat2);	
					}
				}
				
				if (val == null) {
					cell.setCellValue("");
				} else {
					cell.setCellValue(val.toString());
				}
				
			}else {
				Method method = cls.getMethod(methodName);

				Object val = method.invoke(vo);

				SXSSFCell cell = row.createCell(col, SXSSFCell.CELL_TYPE_STRING);

				if (val == null) {
					cell.setCellValue("");
				} else {
					if(methodName.toString().contains("getSum_w")) {
						cell.setCellStyle(dateCellFormat2);
					} else if(methodName.toString().contains("getSum_t")) {
						cell.setCellStyle(dateCellFormat4);
					} else {
						cell.setCellStyle(dateCellFormat);
					}
					cell.setCellValue(val.toString());
					
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





}
