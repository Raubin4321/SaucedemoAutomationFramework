package com.saucedemo.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.saucedemo.base.BaseLoggedInTest;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutPage;

public class CartPageTest extends BaseLoggedInTest {

	@Test
	public void verifyCartItemsDisplayed() {
		inventory.addProductToCart("Sauce Labs Backpack");
		CartPage cartPage = inventory.openCart();

		Assert.assertTrue(cartPage.getCartItemsCount() > 0, "Cart items are not displayed");
	}

	@Test
	public void verifyContinueShopping() {

		inventory.addProductToCart("Sauce Labs Backpack");
		CartPage cartPage = inventory.openCart();

		cartPage.clickContinueShopping();

		Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "User is not navigated back to Inventory Page");
	}

	@Test
	public void verifyCheckoutNavigation() {

		inventory.addProductToCart("Sauce Labs Backpack");
		CartPage cartPage = inventory.openCart();
		CheckoutPage checkoutPage = cartPage.clickCheckout();

		Assert.assertTrue(checkoutPage != null, "Navigation to checkout failed");
	}

}
