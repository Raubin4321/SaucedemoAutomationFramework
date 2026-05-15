package com.saucedemo.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import com.aventstack.extentreports.ExtentTest;
import com.saucedemo.utils.ExtentManager;
import com.saucedemo.utils.ScreenshotUtil;

public class TestListener implements ITestListener, IAnnotationTransformer {

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		annotation.setRetryAnalyzer(RetryAnalyzer.class);
	}

	@Override
	public void onTestStart(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		ExtentTest test = ExtentManager.getReporter().createTest(testName);

		test.assignCategory(result.getTestClass().getName());
		test.info("Test Started: " + testName);

		ExtentManager.setTest(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ExtentManager.getTest().pass("✅ Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		ExtentTest test = ExtentManager.getTest();

		test.fail("❌ Test Failed");
		test.fail(result.getThrowable());

		String path = ScreenshotUtil.captureScreenshot(result.getMethod().getMethodName());
		test.addScreenCaptureFromPath(path);
		test.info("Screenshot attached");

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		ExtentManager.getTest().skip("⚠️ Test Skipped: " + result.getThrowable());
	}
	
	@Override
	public void onStart(ITestContext context) {
		ExtentManager.getReporter();
	}

	@Override
	public void onFinish(ITestContext context) {
		ExtentManager.getReporter().flush();
		ExtentManager.unload();
	}

}
