package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutOverviewPage extends BasePage {

	private By orderSummary = By.className("summary_info");
	private By finishBtn = By.id("finish");
	private By cancelBtn = By.id("cancel");
	private By successMsg = By.tagName("h2");

	public CheckoutOverviewPage(WebDriver driver) {
		super(driver);
	}

	public boolean isOrderSummaryDisplayed() {
		log.info("Order summary displayed");
		return getElements(orderSummary).size() > 0;
	}

	public void clickFinish() {
		log.info("Clicking Finish — placing the order");
		click(finishBtn);
		log.info("Order placed successfully");
	}

	public void clickCancel() {
		log.info("Clicking Cancel on Checkout Overview page");
		click(cancelBtn);
	}

	public String getSuccessMessage() {
		log.info("Retrieving order success message");
        String message = getText(successMsg);
        log.info("Order success message: [{}]", message);
        return message;
	}

}
