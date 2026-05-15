package com.saucedemo.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.utils.ExcelDataProvider;

public class LoginPageTest extends BaseTest {
	
	private LoginPage login;

	@BeforeMethod
	public void initPage() {
	    login = new LoginPage(driver);
	}
	
	@Test(dataProvider = "ValidLoginData", dataProviderClass = ExcelDataProvider.class)
	public void validLoginTest(String username, String password) {
		login.loginValidUser(username, password);

		Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));

		/*
		 InventoryPage inventory = login.loginValidUser("standard_user", "secret_sauce");
		 Assert.assertTrue(inventory.getProductCount() > 0, "Login failed - Inventory not loaded");
		*/
	}

	@Test(dataProvider = "InvalidLoginData", dataProviderClass = ExcelDataProvider.class)
	public void invalidLoginTest(String username, String password) {
		login.loginInvalidUser(username, password);

		Assert.assertEquals(login.getErrorMessage(),
				"Epic sadface: Username and password do not match any user in this service");
	}

	@Test
	public void emptyCredentialTest() {
		login.loginInvalidUser("", "");

		Assert.assertEquals(login.getErrorMessage(), "Epic sadface: Username is required");
	}

	@Test
	public void lockedUserTest() {
		login.loginInvalidUser("locked_out_user", "secret_sauce");

		Assert.assertTrue(login.getErrorMessage().contains("locked out"),"Locked user error message not displayed");
	}

}
