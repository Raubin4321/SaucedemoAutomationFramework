package com.saucedemo.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.saucedemo.utils.ExtentManager;

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
		int count = getElements(cartItems).size();
        ExtentManager.pass("Cart contains " + count + " item(s)");
        return count;
	}

	public void removeItemByName(String productName) {
		log.info("Removing item from cart: [{}]", productName);
	    List<WebElement> items = getElements(cartItems);

	    for (WebElement item : items) {
	        String name = item.findElement(cartItemName).getText();

	        if (name.equalsIgnoreCase(productName)) {
	            item.findElement(removeBtn).click();
	            ExtentManager.pass("Product '" + productName + "' removed from cart successfully");
                return;
            }
        }
        ExtentManager.fail("Product '" + productName + "' was not found in the cart to remove");
        throw new RuntimeException("Item not found: " + productName);
	}

	public void clickContinueShopping() {
		log.info("Clicking Continue Shopping — returning to Inventory page");
	    click(continueShopping);
	    ExtentManager.pass("Clicked 'Continue Shopping' — returned to Products page");
    }
	
	public CheckoutPage clickCheckout() {
		log.info("Clicking Checkout button");
        click(checkoutBtn);
        ExtentManager.pass("Navigated to Checkout page to enter delivery information");
        return new CheckoutPage(driver);
	}
}
