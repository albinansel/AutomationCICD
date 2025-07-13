package albinsacademy.stepDefinition;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import albinsacademy.TestComponents.BaseTest;
import albinsacademy.pageobjects.CartPage;
import albinsacademy.pageobjects.CheckoutPage;
import albinsacademy.pageobjects.ConfirmationPage;
import albinsacademy.pageobjects.LandingPage;
import albinsacademy.pageobjects.ProductCatalogue;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitionImpl extends BaseTest {
	
	public LandingPage landingPage;
	public ProductCatalogue productCatalogue;
	public ConfirmationPage confirmationPage;
	
	@Given("I landed on Ecommerce Page")
	public void I_landed_on_Ecommerce_Page() throws IOException{
		
		landingPage= launchApplication();
	}
	
	@Given("^Logged in with username (.+) and password (.+)$")
	public void Logged_in_with_username_and_password(String username,String password )
	{
		productCatalogue = landingPage.loginApplication(username,password);
	}

	@When("^I add product (.+) to cart$")
	public void I_add_product_to_cart(String productName) throws InterruptedException {
		
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
	}
	
	
	@When("^Checkout (.+) and submit the order$")
	public void checkout_submit_order(String productName) {
		
		CartPage cartPage =productCatalogue.goToCartPage();
		
		Boolean match = cartPage.verifyProductDisplay(productName);
		
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		
		checkoutPage.selectCountry("india");
		confirmationPage = checkoutPage.submitOrder();
	}
	
	
	@Then("{string} message is displayed on confirmation page")
	public void message_displayed_confirmationPage(String string) {
		
		String confirmMessage= confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase(string));
		driver.close();
	}
	
	@Then("{string} message is displayed")
	public void something_message_displayed(String string) {
		
		Assert.assertEquals(string, landingPage.getErrorMessage()); 
		driver.close();
	}
	
	
}
