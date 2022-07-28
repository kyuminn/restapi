package com.mnsoft.upmu.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ReadExcelPoi {

	private String inputFile;

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	@SuppressWarnings("finally")
	public List<Map<String, String>> getExcelXlsData(String[] excelKeyArr)throws Exception{
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		Map<String, String>       result     = new HashMap<String, String>();

		try{
			//파일을 읽기위해 엑셀파일을 가져온다
			FileInputStream fis   = new FileInputStream(inputFile);
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			int rowindex    = 0;
			int columnindex = 0;
			//시트 수 (첫번째에만 존재하므로 0을 준다)
			//만약 각 시트를 읽기위해서는 FOR문을 한번더 돌려준다
			HSSFSheet sheet = workbook.getSheetAt(0);
			//행의 수
			int rows=sheet.getPhysicalNumberOfRows();
			for(rowindex = 1; rowindex < rows; rowindex++){
				//행을 읽는다
				HSSFRow row = sheet.getRow(rowindex);
				if(row != null){
					result = new HashMap<String, String>();
					for(columnindex = 0; columnindex < excelKeyArr.length; columnindex++){
						//셀값을 읽는다
						HSSFCell cell = row.getCell(columnindex);
						//셀이 빈값일경우를 위한 널체크
						if(cell == null){
							continue;
						}else{
							//타입별로 내용 읽기
										
							switch (cell.getCellType()){
							case HSSFCell.CELL_TYPE_FORMULA:
								result.put(excelKeyArr[columnindex], cell.getCellFormula());
								break;
							case HSSFCell.CELL_TYPE_NUMERIC:
								
								String strtemp = Double.toString(cell.getNumericCellValue());
								cell.setCellType(cell.CELL_TYPE_STRING);
								
								String cellStr = cell.getStringCellValue();
								
								if(cellStr.indexOf(".") > -1) {
									cellStr = strtemp;
								}
								
								System.out.println("cellStr:"+cellStr);
								result.put(excelKeyArr[columnindex], cellStr);
								break;
							case HSSFCell.CELL_TYPE_STRING:
								result.put(excelKeyArr[columnindex], cell.getStringCellValue()+"");
								break;
							case HSSFCell.CELL_TYPE_BLANK:
								result.put(excelKeyArr[columnindex], cell.getBooleanCellValue()+"");
								break;
							case HSSFCell.CELL_TYPE_ERROR:
								result.put(excelKeyArr[columnindex], cell.getErrorCellValue()+"");
								break;
							}
//							System.out.println("========== "+ excelKeyArr[columnindex] + " :: "+cell.getCellType() + "::" + result.get(excelKeyArr[columnindex]));
						}
					}

					resultList.add(result);
				}
			}
		}catch(Exception e){
			StringUtil.printStackTrace(e);
			throw e;
		}finally{
			return resultList;
		}
	}

	public List<Map<String, String>> getExcelXlsxData(String[] excelKeyArr) throws Exception{
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		Map<String, String>       result     = new HashMap<String, String>();


		File excelFile = new File(inputFile);
		//파일을 읽기위해 엑셀파일을 가져온다
		FileInputStream fis   = null;
		XSSFWorkbook workbook = null;
		try{
			fis   = new FileInputStream(excelFile);
			workbook = new XSSFWorkbook(fis);
			XSSFSheet    sheet    = workbook.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();
			for (int rowindex = 1; rowindex < rows; rowindex++) {
				// 행을읽는다
				XSSFRow row = sheet.getRow(rowindex);
				if (row != null) {
					result = new HashMap<String, String>();
					for ( int colIndex = 0; colIndex < excelKeyArr.length; colIndex++ ){
						XSSFCell cell = row.getCell(colIndex);
						// 셀이 빈값일경우를 위한 널체크
						if (cell == null) {
							continue;
						} else {
							/*
							// 타입별로 내용 읽기
							switch (cell.getCellType()) {
							case XSSFCell.CELL_TYPE_FORMULA:
								result.put(excelKeyArr[colIndex], cell.getStringCellValue());
								break;
							case XSSFCell.CELL_TYPE_NUMERIC:
								
//								String strtemp = Double.toString(cell.getNumericCellValue());
								String strtemp = new BigDecimal(String.format("%.15f", cell.getNumericCellValue())).stripTrailingZeros().toPlainString();
								if(strtemp.indexOf("9999999999") > -1) {
									strtemp = new BigDecimal(String.format("%.14f", cell.getNumericCellValue())).stripTrailingZeros().toPlainString();
								}
								cell.setCellType(cell.CELL_TYPE_STRING);
								
								String cellStr = cell.getStringCellValue();
//								if(cellStr.indexOf(".") > -1) {
//									BigDecimal bigman = new BigDecimal(cell.getStringCellValue());
//									String cellStr1 = bigman.stripTrailingZeros().toPlainString();
//									System.out.println("테스트 : "+cellStr1);
//								}
								
								
								if(cellStr.indexOf(".") > -1) {
									cellStr = strtemp;
								}
								
								System.out.println("cellStr:"+cellStr);
								
								result.put(excelKeyArr[colIndex], cellStr);
								
								break;
							case XSSFCell.CELL_TYPE_STRING:
							case XSSFCell.CELL_TYPE_BLANK:
								result.put(excelKeyArr[colIndex], cell.getStringCellValue());
								break;
							case XSSFCell.CELL_TYPE_ERROR:
								result.put(excelKeyArr[colIndex], cell.getErrorCellValue()+"");
								break;
							}
							*/
							// cell.setCellType(cell.CELL_TYPE_STRING);
							DataFormatter df = new DataFormatter();
							result.put(excelKeyArr[colIndex], df.formatCellValue(cell));
						}
					}
					resultList.add(result);
				}
			}
			fis.close();
		}catch(IOException e){
			StringUtil.printStackTrace(e);
		}catch(Exception e){
			StringUtil.printStackTrace(e);
		}finally{
			if(fis != null){fis.close();}
		}

		return resultList;
	}

}