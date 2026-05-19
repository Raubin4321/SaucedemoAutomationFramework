package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.saucedemo.utils.ExtentManager;

public class LoginPage extends BasePage {
	
	// Locators
	private By username = By.id("user-name");
	private By password = By.id("password");
	private By loginBtn = By.id("login-button");
	private By errorMsg = By.cssSelector("[data-test='error']");
	
	public LoginPage(WebDriver driver) {
        super(driver);
    }

	public InventoryPage loginValidUser(String user, String pass) {
		log.info("Logging in with valid user : [{}] ", user);
		enterText(username, user);
        enterText(password, pass);
        log.info("Clicking Login button");
        click(loginBtn);
        
        ExtentManager.pass("User '" + user + "' logged into the application successfully");
		
        InventoryPage inventory = new InventoryPage(driver);
        inventory.waitForPageLoad();

        return inventory;
    }
	
	public LoginPage loginInvalidUser(String user, String pass) {
		log.info("Logging in with invalid user : [{}] ", user);
		enterText(username, user);
        enterText(password, pass);
        log.info("Clicking Login button");
        click(loginBtn);
        
        ExtentManager.info("Login attempted with invalid credentials for user: '" + user + "'");
        
        return this;
	}

	public String getErrorMessage() {
		log.info("Login failed, Retrieving error message from Login page");
        String error = getText(errorMsg);
        log.warn("Error message displayed: [{}]", error);
        ExtentManager.pass("Application correctly displayed error message: '" + error + "'");
        return error;
	}

}
