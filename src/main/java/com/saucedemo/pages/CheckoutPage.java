package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.saucedemo.utils.ExtentManager;

public class CheckoutPage extends BasePage {

	private By firstName = By.id("first-name");
	private By lastName = By.id("last-name");
	private By postalCode = By.id("postal-code");

	private By continueBtn = By.id("continue");
	private By cancelBtn = By.id("cancel");
	private By errorMsg = By.cssSelector("h3[data-test='error']");

	public CheckoutPage(WebDriver driver) {
		super(driver);
	}

	public void enterCheckoutInfo(String fName, String lName, String zip) {
		log.info("Entering checkout info — Name: [{} {}], ZIP: [{}]", fName, lName, zip);
		enterText(firstName, fName);
		enterText(lastName, lName);
		enterText(postalCode, zip);
		log.info("Checkout info entered successfully");
		ExtentManager.pass("Delivery information entered — Name: '" + fName + " " + lName + "', ZIP: '" + zip + "'");
	}

	public String getErrorMessage() {
		log.info("Retrieving error message from Checkout page");
		String error = getText(errorMsg);
		log.warn("Checkout error displayed: [{}]", error);
		ExtentManager.pass("Application correctly displayed checkout error: '" + error + "'");
	    return error;
	}

	public CheckoutOverviewPage clickContinue() {
		log.info("Clicking Continue — navigating to Checkout Overview page");
		click(continueBtn);
		ExtentManager.pass("Delivery information submitted — navigated to Order Summary page");
        return new CheckoutOverviewPage(driver);
	}

	public void clickCancel() {
		log.info("Clicking Cancel on Checkout page — returning to Cart");
		click(cancelBtn);
		ExtentManager.pass("Checkout cancelled — returned to Cart page");
	}

}
