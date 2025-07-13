package albinsacademy.Tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import albinsacademy.TestComponents.BaseTest;
import albinsacademy.pageobjects.CartPage;
import albinsacademy.pageobjects.CheckoutPage;
import albinsacademy.pageobjects.ConfirmationPage;
import albinsacademy.pageobjects.OrderPage;
import albinsacademy.pageobjects.ProductCatalogue;

public class SubmitOrderTest extends BaseTest{

	String productName ="IPHONE 13 PRO";
	
	@Test(dataProvider ="getData", groups= {"Purchase"})
	public void submitOrder(HashMap<String,String> input) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"),input.get("password"));	
		
		List<WebElement> products = productCatalogue.getProductList();
		
		productCatalogue.addProductToCart(input.get("product"));
		CartPage cartPage =productCatalogue.goToCartPage();
		
		Boolean match = cartPage.verifyProductDisplay(input.get("product"));
		
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		
		checkoutPage.selectCountry("india");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		
		String confirmMessage= confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("Thankyou for the order."));
	}

	// To verify the product is displaying in orders page.
	@Test(dependsOnMethods = {"submitOrder"})
	public void OrderHistoryTest() {
		ProductCatalogue productCatalogue = landingPage.loginApplication("albin@gmail.com","Ab@123456");	
		OrderPage orderPage =productCatalogue.goToOrderPage();
		Assert.assertTrue(orderPage.verifyOrderDisplay(productName));
	}
	
	
	
	@DataProvider            //Method-3 : reading data from json file 
	public Object[][] getData() throws IOException {
		
		List<HashMap<String,String>> data= getJsonDataToMap(System.getProperty("user.dir")+"//src//test//java//albinsacademy//data//PurchaseOrder.json");
		return new Object[][] {{data.get(0)}, {data.get(1)}};
	
	}
	
	//@DataProvider            //Method-2 :Using HashMap 
//	public Object[][] getData() {
//		
//		HashMap<String,String> map = new HashMap<String,String>();
//		map.put("email", "albin@gmail.com");
//		map.put("password", "Ab@123456");
//		map.put("product", "IPHONE 13 PRO");
//		
//		HashMap<String,String> map1 = new HashMap<String,String>();
//		map1.put("email", "shetty@gmail.com");
//		map1.put("password", "Iamking@000");
//		map1.put("product", "ADIDAS ORIGINAL");
//		return new Object[][] {{map}, {map1}};
//	}
	
	
	//@DataProvider           //Method-1
//	public Object[][] getData() {
//		return new Object[][] {{"albin@gmail.com","Ab@123456","IPHONE 13 PRO"},{"shetty@gmail.com","Iamking@000","ADIDAS ORIGINAL"}};
//	}
	
	
	
}
