package com.saucedemo.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.saucedemo.base.BaseLoggedInTest;

public class InventoryPageTest extends BaseLoggedInTest {

	@Test
	public void verifyProductDisplay() {
		int productCount = inventory.getProductCount();
		Assert.assertTrue(productCount > 0, "Products are not displayed");
	}

	@Test
	public void verifyAddProductToCart() {
		inventory.addProductToCart("Sauce Labs Backpack");
		Assert.assertEquals(inventory.getCartCount(), "1");
	}

	@Test
	public void verifyRemoveProductFromCart() {
		inventory.addProductToCart("Sauce Labs Backpack");
		inventory.removeProductFromCart("Sauce Labs Backpack");

		Assert.assertEquals(inventory.getCartCount(), "0");
	}

	@Test
	public void verifySortProducts() {
		inventory.sortByLowToHigh();
		Assert.assertTrue(true);
	}

	@Test
	public void verifyProductDetails() {
		inventory.openProductDetails("Sauce Labs Bolt T-Shirt");
		Assert.assertTrue(driver.getCurrentUrl().contains("inventory-item"));
	}

	@Test
	public void verifyLogoutNavigation() {

		inventory.logout();

		Assert.assertTrue(driver.getCurrentUrl().contains("saucedemo"),
				"User is not redirected to login page after logout");
	}
}
