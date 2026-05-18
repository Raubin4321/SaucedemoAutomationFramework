package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.saucedemo.utils.ExtentManager;

public class CheckoutOverviewPage extends BasePage {

	private By orderSummary = By.className("summary_info");
	private By finishBtn = By.id("finish");
	private By cancelBtn = By.id("cancel");
	private By successMsg = By.tagName("h2");

	public CheckoutOverviewPage(WebDriver driver) {
		super(driver);
	}

	public boolean isOrderSummaryDisplayed() {
		log.info("Checking if order summary is displayed");
		boolean isDisplayed = getElements(orderSummary).size() > 0;
		if (isDisplayed) {
			ExtentManager.pass("Order summary is visible — user can review items, prices and total before placing order");
		} else {
			ExtentManager.fail("Order summary was not displayed on the Checkout Overview page");
		}
		return isDisplayed;
	}

	public void clickFinish() {
		log.info("Clicking Finish — placing the order");
		click(finishBtn);
		log.info("Order placed successfully");
		ExtentManager.pass("Order placed successfully — 'Finish' button clicked on Order Summary page");
    }

	public void clickCancel() {
		log.info("Clicking Cancel on Checkout Overview page");
		click(cancelBtn);
		ExtentManager.pass("Order cancelled on Summary page");
    }

	public String getSuccessMessage() {
		log.info("Retrieving order success message");
		String message = getText(successMsg);
		log.info("Order success message: [{}]", message);
		ExtentManager.pass("Order confirmation message displayed: '" + message + "'");
        return message;
	}

}
