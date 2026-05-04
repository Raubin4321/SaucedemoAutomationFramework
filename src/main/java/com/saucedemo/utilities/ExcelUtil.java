package com.saucedemo.utilities;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

	public static Object[][] getSheetData(String filePath, String sheetName) {

		Object[][] data = null;

		try {
			FileInputStream fis = new FileInputStream(filePath);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);

			Sheet sheet = workbook.getSheet(sheetName);

			int rows = sheet.getPhysicalNumberOfRows();
			int cols = sheet.getRow(0).getPhysicalNumberOfCells();

			data = new Object[rows - 1][cols];

			for (int i = 1; i < rows; i++) {
				Row row = sheet.getRow(i);

				for (int j = 0; j < cols; j++) {
					Cell cell = row.getCell(j);
					data[i - 1][j] = getCellValue(cell);
				}
			}

			workbook.close();
			fis.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	private static Object getCellValue(Cell cell) {
		if (cell == null) {
			return "";
		}

		switch (cell.getCellType()) {

			case STRING:
				return cell.getStringCellValue();
				
			case NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
	                return cell.getDateCellValue().toString();
	            } else {
	                return String.valueOf((int)cell.getNumericCellValue());
	            }
				
			case BOOLEAN:
				return String.valueOf(cell.getBooleanCellValue());
				
			default:
				return "";
		}
	}

}
