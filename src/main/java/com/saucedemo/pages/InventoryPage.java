package com.saucedemo.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.saucedemo.utils.ExtentManager;

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
		ExtentManager.pass("Inventory / Products page loaded successfully");
	}

	// Get Number Of Products
	public int getProductCount() {
		int count = getElements(items).size();
		ExtentManager.pass("Total products displayed on the page: " + count);
		return count;
	}

	// Get Product Details
	public void openProductDetails(String productName) {
		log.info("Opening product details for: [{}]", productName);
		getProductByName(productName).findElement(itemName).click();
		ExtentManager.pass("Opened product details page for: '" + productName + "'");
	}

	// Add Product To Cart
	public void addProductToCart(String productName) {
		clearCart();
		log.info("Adding product to cart: [{}]", productName);
		getProductByName(productName).findElement(addToCartBtn).click();
		ExtentManager.pass("Product '" + productName + "' added to cart successfully");
	}

	// Remove Product From Cart
	public void removeProductFromCart(String productName) {
		log.info("Removing product from cart: [{}]", productName);
		getProductByName(productName).findElement(removeBtn).click();
		ExtentManager.pass("Product '" + productName + "' removed from cart successfully");
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
		ExtentManager.pass("Products sorted by Price: Low to High");
	}

	// Get Number Of Products Added In The Cart
	public String getCartCount() {
		log.info("Fetching cart count");
		List<WebElement> badge = driver.findElements(cartBadge);
		String count = badge.isEmpty() ? "0" : badge.get(0).getText();
		ExtentManager.pass("Cart item count displayed on cart icon: " + count);
		return count;
	}

	// Open Cart
	public CartPage openCart() {
		log.info("Opening cart");
		click(cartIcon);
		ExtentManager.pass("Navigated to Cart page");
		return new CartPage(driver);
	}

	// Logout
	public void logout() {
		click(menuBtn);
		log.info("Clicking Logout link");
		click(logoutLink);
		log.info("User logged out successfully");
		ExtentManager.pass("User logged out and returned to Login page successfully");
	}

	private WebElement getProductByName(String productName) {
		List<WebElement> productList = getElements(items);

		for (WebElement product : productList) {
			String name = product.findElement(itemName).getText();

			if (name.equalsIgnoreCase(productName)) {
				return product;
			}
		}
		ExtentManager.fail("Product '" + productName + "' was not found on the Inventory page");
		throw new RuntimeException("Product not found: " + productName);
	}
}
