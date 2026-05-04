package com.saucedemo.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CartPage extends BasePage {
	
	private By cartItems = By.className("cart_item");
	private By cartItemName = By.className("inventory_item_name");
	private By removeBtn = By.xpath("//button[contains(text(), 'Remove')]");
	private By continueShopping = By.id("continue-shopping");
	private By checkoutBtn = By.id("checkout");
	
	public CartPage(WebDriver driver) {
        super(driver);
    }
	
	public int getCartItemsCount() {
	    return getElements(cartItems).size();
	}

	public void removeItemByName(String productName) {
	    List<WebElement> items = getElements(cartItems);

	    for (WebElement item : items) {
	        String name = item.findElement(cartItemName).getText();

	        if (name.equalsIgnoreCase(productName)) {
	            item.findElement(removeBtn).click();
	            return;
	        }
	    }
	    throw new RuntimeException("Item not found: " + productName);
	}

	public void clickContinueShopping() {
		log.info("Clicking Continue Shopping — returning to Inventory page");
	    click(continueShopping);
	}
	
	public CheckoutPage clickCheckout() {
	    click(checkoutBtn);
	    return new CheckoutPage(driver);
	}
}
