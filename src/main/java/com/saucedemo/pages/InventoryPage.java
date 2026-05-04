package com.saucedemo.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class InventoryPage extends BasePage {

	private By title = By.className("title");
	private By items = By.className("inventory_item");
	private By itemName = By.className("inventory_item_name");

	private By addToCartBtn = By.xpath("//button[contains(text(),'Add to cart')]");
	private By removeBtn = By.xpath("//button[contains(text(),'Remove')]");

	private By cartIcon = By.className("shopping_cart_link");
	private By cartBadge = By.className("shopping_cart_badge");

	private By sortDropdown = By.className("product_sort_container");

	private By menuBtn = By.id("react-burger-menu-btn");
	private By logoutLink = By.id("logout_sidebar_link");

	public InventoryPage(WebDriver driver) {
		super(driver);
	}

	// Wait for Title to appear
	public void waitForPageLoad() {
		getText(title);
	}

	// Get Number Of Products
	public int getProductCount() {
		return getElements(items).size();
	}

	// Get Product Details
	public void openProductDetails(String productName) {
		getProductByName(productName).findElement(itemName).click();
	}

	// Add Product To Cart
	public void addProductToCart(String productName) {
		
		clearCart();
		log.info("Adding product to cart: [{}]", productName);
		
		getProductByName(productName).findElement(addToCartBtn).click();
	}

	// Remove Product From Cart
	public void removeProductFromCart(String productName) {
		log.info("Removing product from cart: [{}]", productName);
		getProductByName(productName).findElement(removeBtn).click();
	}

	public void clearCart() {
		List<WebElement> removeButtons = driver.findElements(removeBtn);
		
		for (WebElement btn : removeButtons) {
			btn.click();
		}
	}

	// Sort Product (low to high)
	public void sortByLowToHigh() {
		log.info("Sorting products: Low to High");
		
		selectByVisibleText(sortDropdown, "Price (low to high)");
	}

	// Get Number Of Products Added In The Cart
	public String getCartCount() {
		log.info("Fetching cart count");
		
		List<WebElement> badge = driver.findElements(cartBadge);
		return badge.isEmpty() ? "0" : badge.get(0).getText();
	}

	// Open Cart
	public CartPage openCart() {
		click(cartIcon);
		return new CartPage(driver);
	}

	// Logout
	public void logout() {
		click(menuBtn);
		log.info("Clicking Logout link");
		click(logoutLink);
		log.info("User logged out successfully");
	}

	private WebElement getProductByName(String productName) {
		List<WebElement> productList = getElements(items);

		for (WebElement product : productList) {
			String name = product.findElement(itemName).getText();

			if (name.equalsIgnoreCase(productName)) {
				return product;
			}
		}
		throw new RuntimeException("Product not found: " + productName);
	}
}
