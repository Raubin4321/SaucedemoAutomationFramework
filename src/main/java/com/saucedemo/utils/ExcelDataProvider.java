package com.saucedemo.utils;

import org.testng.annotations.DataProvider;

public class ExcelDataProvider {

	private static final String FILE_PATH = System.getProperty("user.dir")
			+ "/src/test/resources/testdata/testdata.xlsx";

	@DataProvider(name = "ValidLoginData")
	public static Object[][] validLoginData() {
		return ExcelUtil.getSheetData(FILE_PATH, "ValidLoginData");
	}

	@DataProvider(name = "InvalidLoginData")
	public static Object[][] inValidLoginData() {
		return ExcelUtil.getSheetData(FILE_PATH, "InvalidLoginData");
	}
	
}
