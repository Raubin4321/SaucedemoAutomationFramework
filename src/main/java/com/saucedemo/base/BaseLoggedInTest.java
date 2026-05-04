package com.saucedemo.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;

import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;

public class BaseLoggedInTest extends BaseTest {

	private static final Logger log = LogManager.getLogger(BaseLoggedInTest.class);

	protected InventoryPage inventory;

	@BeforeMethod
	public void loginSetup() {
		log.info("Starting login with user : [{}]", "standard_user");
		
		LoginPage loginPage = new LoginPage(driver);
		inventory = loginPage.loginValidUser("standard_user", "secret_sauce");
	}

}
