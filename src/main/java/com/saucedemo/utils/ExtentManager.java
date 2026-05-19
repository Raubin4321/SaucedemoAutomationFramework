package com.saucedemo.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	public static ExtentReports getReporter() {

		if (extent == null) {

			// String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
			String reportPath = System.getProperty("user.dir") + "/reports/extentReport.html";
			ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);

			spark.config().setReportName("Automation Test Report");
			spark.config().setDocumentTitle("SauceDemo Project Report");
			spark.config().enableOfflineMode(true);  // embeds all CSS/JS inline

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
	
	// Reporting Methods
    public static void pass(String message) {
        if (test.get() != null) {
            test.get().log(Status.PASS, message);
        }
    }
    
    public static void info(String message) {
        if (test.get() != null) {
            test.get().log(Status.INFO, message);
        }
    }

    public static void fail(String message) {
        if (test.get() != null) {
            test.get().log(Status.FAIL, message);
        }
    }

    public static void warning(String message) {
        if (test.get() != null) {
            test.get().log(Status.WARNING, message);
        }
    }
	
}
