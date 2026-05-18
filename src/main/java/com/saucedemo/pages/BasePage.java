package com.saucedemo.pages;

import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.saucedemo.utils.ConfigReader;
import com.saucedemo.utils.ExtentManager;

public class BasePage {

	protected static final Logger log = LogManager.getLogger(BasePage.class);

	protected WebDriver driver;
	protected WebDriverWait wait;

	public BasePage(WebDriver driver) {
		this.driver = driver;

		int waitTime = Integer.parseInt(ConfigReader.getProperty("waitTime"));
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
	}

	protected void click(By locator) {
		try {
			log.info("Clicking on element: " + locator);
			wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
		} catch (TimeoutException e) {
			log.error("Element not clickable: " + locator);
			ExtentManager.fail("Action Failed — Button/Link was not clickable on the page. Locator: " + locator);
			throw new RuntimeException("Element not clickable : " + locator, e);
		}
	}

	protected void enterText(By locator, String text) {
		try {
			log.info("Entering text '{}' into element: {}", text, locator);
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			element.clear();
			element.sendKeys(text);
		} catch (TimeoutException e) {
			log.error("Element not visible for text entry: " + locator);
			ExtentManager.fail("Action Failed — Input field was not visible to enter: '" + text + "'");
			throw new RuntimeException("Element not visible: " + locator, e);
		}
	}

	protected String getText(By locator) {
		try {
			log.info("Getting text from element: " + locator);
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
		} catch (TimeoutException e) {
			log.error("Element not visible: " + locator);
			ExtentManager.fail("Action Failed — Could not read text from the page. Element was not visible.");
			throw new RuntimeException("Element not visible : " + locator, e);
		}
	}

	protected List<WebElement> getElements(By locator) {
		try {
			log.info("Getting all elements located by: " + locator);
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
		} catch (TimeoutException e) {
			log.error("Elements not visible: " + locator);
			ExtentManager.fail("Action Failed — Expected list of elements were not visible on the page.");
			throw new RuntimeException("Elements not visible: " + locator, e);
		}
	}

	protected void selectByVisibleText(By locator, String text) {
		try {
			log.info("Selecting option '{}' from dropdown: {}", text, locator);
			WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			new Select(dropdown).selectByVisibleText(text);
		} catch (TimeoutException e) {
			log.error("Dropdown not visible: " + locator);
			ExtentManager.fail("Action Failed — Could not select '" + text + "' from dropdown. Dropdown was not visible.");
			throw new RuntimeException("Dropdown not visible: " + locator, e);
		}
	}
	
}
