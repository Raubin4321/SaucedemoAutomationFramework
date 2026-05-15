package com.saucedemo.driver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.saucedemo.utilities.ConfigReader;

public class DriverFactory {

	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	private static final Logger log = LogManager.getLogger(DriverFactory.class);

	private DriverFactory() {

	}

	public static void initDriver(String browser) {

		boolean isHeadless;
		if(System.getProperty("headless") != null) {
			isHeadless = Boolean.parseBoolean(System.getProperty("headless"));
		} else {
			isHeadless = Boolean.parseBoolean(ConfigReader.getProperty("headless"));
		}

		boolean seleniumGrid;
		if(System.getProperty("seleniumGrid") != null) {
			seleniumGrid = Boolean.parseBoolean(System.getProperty("seleniumGrid"));
		} else {
			seleniumGrid = Boolean.parseBoolean(ConfigReader.getProperty("seleniumGrid"));
		}

	    log.info("Initializing browser: [{}] | Headless: [{}] | Grid: [{}] | Thread ID: [{}]",
	            browser, isHeadless, seleniumGrid, Thread.currentThread().getId());

		WebDriver webDriver;

		try {
			if(seleniumGrid) {
	            String gridURL = ConfigReader.getProperty("gridURL");
	            log.info("Connecting to Selenium Grid at: [{}]", gridURL);
	            webDriver = createRemoteDriver(browser, isHeadless, gridURL);
	        } else {
	        	switch (browser.toLowerCase()) {

				case "chrome":
					webDriver = new ChromeDriver(getChromeOptions(isHeadless));
					break;

				case "firefox":
					webDriver = new FirefoxDriver(getFirefoxOptions(isHeadless));
					break;

				case "edge":
					webDriver = new EdgeDriver(getEdgeOptions(isHeadless));
					break;

				default:
					log.error("Invalid browser: [{}] ", browser);
					throw new IllegalArgumentException("Invalid Browser : " + browser);

				}
	        }
			
		} catch (Exception e) {
			throw new RuntimeException("Failed to initialize driver: " + browser, e);
		}

		// Maximize only when NOT headless
		if (!isHeadless) {
			webDriver.manage().window().maximize();
		}
		webDriver.manage().deleteAllCookies();

		log.info("Browser launched successfully | Thread ID: [{}]", Thread.currentThread().getId());
		driver.set(webDriver);
	}
	
	public static WebDriver getDriver() {
		return driver.get();
	}
	
	// Quit Driver
	public static void quitDriver() {

		if (driver.get() != null) {
			log.info("Closing browser | Thread ID: [{}]", Thread.currentThread().getId());
			driver.get().quit();
			driver.remove(); // prevents memory leak
			log.info("Browser closed successfully | Thread ID: [{}]", Thread.currentThread().getId());
		}
	}

	private static WebDriver createRemoteDriver(String browser, boolean isHeadless, String gridURL) {
		try {
	        URL url = new URL(gridURL);
	        switch (browser.toLowerCase()) {
	            case "chrome":
	                return new RemoteWebDriver(url, getChromeOptions(isHeadless));
	            case "firefox":
	                return new RemoteWebDriver(url, getFirefoxOptions(isHeadless));
	            case "edge":
	                return new RemoteWebDriver(url, getEdgeOptions(isHeadless));
	            default:
	                log.error("Invalid browser for Grid: [{}]", browser);
	                throw new IllegalArgumentException("Invalid Browser for Grid: " + browser);
	        }
	    } catch (MalformedURLException e) {
	        throw new RuntimeException("Invalid Grid URL: " + gridURL, e);
	    }
	}

	private static ChromeOptions getChromeOptions(boolean isHeadless) {
		ChromeOptions chromeOptions = new ChromeOptions();

		// Disable password manager popup
		chromeOptions.addArguments("--disable-notifications");
		chromeOptions.addArguments("--disable-infobars");

		// Disable password manager
		chromeOptions.setExperimentalOption("prefs",
				Map.of("credentials_enable_service", false, "profile.password_manager_enabled", false));

		// Fix parallel + password leak popup
		chromeOptions.addArguments("--guest");
		chromeOptions.addArguments("--disable-features=PasswordLeakDetection,PasswordCheck");

		// Unique user data dir per thread — avoids parallel conflicts
		String userDir = System.getProperty("java.io.tmpdir") + "chrome-test-" + Thread.currentThread().getId();
		chromeOptions.addArguments("--user-data-dir=" + userDir);

		if (isHeadless) {
			log.info("Running Chrome in headless mode");
			chromeOptions.addArguments("--headless"); // no UI needed
			chromeOptions.addArguments("--no-sandbox"); // required for Jenkins
			chromeOptions.addArguments("--disable-dev-shm-usage"); // prevents crashes
			chromeOptions.addArguments("--window-size=1920,1080");
		}

		return chromeOptions;
	}

	private static FirefoxOptions getFirefoxOptions(boolean isHeadless) {
		FirefoxOptions firefoxOptions = new FirefoxOptions();

		// disable save password
		firefoxOptions.addPreference("signon.rememberSignons", false);

		// disable push notifications
		firefoxOptions.addPreference("dom.webnotifications.enabled", false);
		firefoxOptions.addPreference("dom.push.enabled", false);

		// Disable password breach alerts (important)
		firefoxOptions.addPreference("signon.management.page.breach-alerts.enabled", false);

		if (isHeadless) {
			log.info("Running Firefox in headless mode");
			firefoxOptions.addArguments("--headless");
			firefoxOptions.addArguments("--width=1920");
			firefoxOptions.addArguments("--height=1080");
		}

		return firefoxOptions;
	}

	private static EdgeOptions getEdgeOptions(boolean isHeadless) {
		EdgeOptions edgeOptions = new EdgeOptions();

		// Disable popups
		edgeOptions.addArguments("--disable-notifications");
		edgeOptions.addArguments("--disable-infobars");

		// Disable password manager
		edgeOptions.setExperimentalOption("prefs",
				Map.of("credentials_enable_service", false, "profile.password_manager_enabled", false));

		// Fix parallel issues (same as Chrome)
		// edgeOptions.addArguments("--guest");
		edgeOptions.addArguments("--disable-features=PasswordLeakDetection,PasswordCheck");

		// edgeOptions.addArguments("--user-data-dir=/tmp/edge-test-" +
		// Thread.currentThread().getId());
		String userDir = System.getProperty("java.io.tmpdir") + "edge-test-" + Thread.currentThread().getId();
		edgeOptions.addArguments("--user-data-dir=" + userDir);

		if (isHeadless) {
			log.info("Running Edge in headless mode");
			edgeOptions.addArguments("--headless");
			edgeOptions.addArguments("--no-sandbox");
			edgeOptions.addArguments("--disable-dev-shm-usage");
			edgeOptions.addArguments("--window-size=1920,1080");
		}

		return edgeOptions;
	}
	
}
