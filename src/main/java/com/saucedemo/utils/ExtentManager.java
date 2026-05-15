package com.saucedemo.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	public static ExtentReports getReporter() {

		if (extent == null) {

			// String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new
			// Date());
			String reportPath = System.getProperty("user.dir") + "/reports/extentReport.html";
			ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);

			spark.config().setReportName("Automation Test Report");
			spark.config().setDocumentTitle("SauceDemo Project Report");
			spark.config().setCss(null);
			spark.config().setJs(null);

			extent = new ExtentReports();
			extent.attachReporter(spark);
		}

		return extent;
	}

	public static void setTest(ExtentTest extentTest) {
		test.set(extentTest);
	}

	public static ExtentTest getTest() {
		return test.get();
	}

	public static void unload() {
		test.remove();
	}
	
}
