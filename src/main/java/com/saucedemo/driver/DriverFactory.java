package com.saucedemo.driver;

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

public class DriverFactory {

	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	private static final Logger log = LogManager.getLogger(DriverFactory.class);

	private DriverFactory() {

	}

	public static void initDriver(String browser) {

		WebDriver webDriver;
		log.info("Initializing browser: {} | Thread ID: {}", browser, Thread.currentThread().getId());
		
		try {
			switch (browser.toLowerCase()) {

			case "chrome":
				webDriver = new ChromeDriver(getChromeOptions());
				break;

			case "firefox":
				webDriver = new FirefoxDriver(getFirefoxOptions());
				break;

			case "edge":
				webDriver = new EdgeDriver(getEdgeOptions());
				break;

			default:
				log.error("Invalid browser: {} ", browser);
				throw new IllegalArgumentException("Invalid Browser : " + browser);

			}
		} catch(Exception e) {
			throw new RuntimeException("Failed to initialize driver: " + browser, e);
		}
		
		webDriver.manage().window().maximize();
		webDriver.manage().deleteAllCookies();

		log.info("Browser launched successfully for Thread: {}", Thread.currentThread().getId());
		driver.set(webDriver);
	}

	// Get Driver (Singleton-like access)
	public static WebDriver getDriver() {
		return driver.get();
	}

	// Quit Driver
	public static void quitDriver() {

		if (driver.get() != null) {
			driver.get().quit();
			driver.remove(); // avoid memory leak
		}
	}

	private static ChromeOptions getChromeOptions() {
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
		String userDir = System.getProperty("java.io.tmpdir") + "chrome-test-" + Thread.currentThread().getId();
		chromeOptions.addArguments("--user-data-dir=" + userDir);

		/*
		 * Headless chromeOptions.addArguments("--headless=new");
		 * chromeOptions.addArguments("--window-size=1920,1080");
		 */

		return chromeOptions;
	}

	private static FirefoxOptions getFirefoxOptions() {
		FirefoxOptions firefoxOptions = new FirefoxOptions();

		// disable save password
		firefoxOptions.addPreference("signon.rememberSignons", false);

		// disable push notifications
		firefoxOptions.addPreference("dom.webnotifications.enabled", false);
		firefoxOptions.addPreference("dom.push.enabled", false);

		// Disable password breach alerts (important)
		firefoxOptions.addPreference("signon.management.page.breach-alerts.enabled", false);

		/*
		 * Headless firefoxOptions.addArguments("-headless");
		 */

		return firefoxOptions;
	}

	private static EdgeOptions getEdgeOptions() {
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

		/*
		 * Headless edgeOptions.addArguments("--headless=new");
		 * edgeOptions.addArguments("--window-size=1920,1080");
		 */

		return edgeOptions;
	}
}
