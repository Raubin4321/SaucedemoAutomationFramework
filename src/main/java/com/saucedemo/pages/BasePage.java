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
        } catch(TimeoutException e) {
        	log.error("Element not clickable: " + locator);
        	throw new RuntimeException("Element not clickable : " + locator, e);
        }
    }

    protected void enterText(By locator, String text) {
    	log.info("Entering text '{}' into element: {}", text, locator);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        try {
        	log.info("Getting text from element: " + locator);
        	return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
        } catch(TimeoutException e) {
        	log.error("Element not visible: " + locator);
        	throw new RuntimeException("Element not visible : " + locator, e);
        }
    }

    protected List<WebElement> getElements(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    protected void selectByVisibleText(By locator, String text) {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        new Select(dropdown).selectByVisibleText(text);
    }
}
