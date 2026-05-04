package com.saucedemo.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.saucedemo.base.BaseLoggedInTest;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutOverviewPage;
import com.saucedemo.pages.CheckoutPage;

public class CheckoutPageTest extends BaseLoggedInTest {

	@Test
	public void verifyMissingFirstName() {
		inventory.addProductToCart("Sauce Labs Backpack");
		CartPage cartPage = inventory.openCart();
		CheckoutPage checkoutPage = cartPage.clickCheckout();

		checkoutPage.enterCheckoutInfo("", "Kumar", "500032");
		checkoutPage.clickContinue();

		Assert.assertTrue(checkoutPage.getErrorMessage().contains("First Name"),
				"Error not displayed for missing first name");
	}

	@Test
	public void verifyMissingPostalcode() {
		inventory.addProductToCart("Sauce Labs Backpack");
		CartPage cartPage = inventory.openCart();
		CheckoutPage checkoutPage = cartPage.clickCheckout();

		checkoutPage.enterCheckoutInfo("Raubin", "Kumar", "");
		checkoutPage.clickContinue();

		Assert.assertTrue(checkoutPage.getErrorMessage().contains("Postal Code"),
				"Error not displayed for missing postal code");
	}

	@Test
	public void verifyEmptyCheckoutFields() {
		inventory.addProductToCart("Sauce Labs Backpack");
		CartPage cartPage = inventory.openCart();
		CheckoutPage checkoutPage = cartPage.clickCheckout();

		checkoutPage.enterCheckoutInfo("", "", "");
		checkoutPage.clickContinue();

		Assert.assertTrue(checkoutPage.getErrorMessage().contains("Error"),
				"Validation error not displayed for empty fields");
	}

	@Test
	public void verifyOrderSummary() {
		inventory.addProductToCart("Sauce Labs Backpack");

		CartPage cartPage = inventory.openCart();
		CheckoutPage checkoutPage = cartPage.clickCheckout();

		checkoutPage.enterCheckoutInfo("John", "Doe", "123456");

		CheckoutOverviewPage overviewPage = checkoutPage.clickContinue();

		Assert.assertTrue(overviewPage.isOrderSummaryDisplayed(), "Order summary is not displayed");
	}

	@Test
	public void verifyCompleteCheckout() {
		inventory.addProductToCart("Sauce Labs Backpack");

		CartPage cartPage = inventory.openCart();
		CheckoutPage checkoutPage = cartPage.clickCheckout();

		checkoutPage.enterCheckoutInfo("Raubin", "Kumar", "500032");

		CheckoutOverviewPage overviewPage = checkoutPage.clickContinue();
		overviewPage.clickFinish();

		Assert.assertTrue(overviewPage.getSuccessMessage().contains("Thank you"),
				"Order success message is not displayed");

	}
}
