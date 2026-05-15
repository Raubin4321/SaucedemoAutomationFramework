package com.saucedemo.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.saucedemo.driver.DriverFactory;
import com.saucedemo.utils.ConfigReader;

public class BaseTest {

	private static final Logger log = LogManager.getLogger(BaseTest.class);
	protected WebDriver driver;
	
	@Parameters("browser")
	@BeforeMethod
	public void setUp(@Optional String browserParam) {
		
		String browser;
		if(System.getProperty("browser") != null) {
			browser = System.getProperty("browser"); // from command line
		} else if (browserParam != null) {
			browser = browserParam;                 // testng.xml <parameter>
		} else {
		    browser = ConfigReader.getProperty("browser");  // from config.properties
		}
		
		log.info("===== Test Started: {} =====", Thread.currentThread().getName());
		
		DriverFactory.initDriver(browser);
		driver = DriverFactory.getDriver();
		
		log.info("Navigating to URL: [{}]", ConfigReader.getProperty("url"));
		driver.get(ConfigReader.getProperty("url"));
	}

	@AfterMethod
	public void tearDown() {
		log.info("===== Test Finished =====");
		DriverFactory.quitDriver();
	}

}
