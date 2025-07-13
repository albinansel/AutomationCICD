package albinsacademy.Tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import albinsacademy.TestComponents.BaseTest;
import albinsacademy.TestComponents.Retry;
import albinsacademy.pageobjects.ProductCatalogue;

public class ErrorValidationsTest extends BaseTest{

	@Test(groups= {"ErrorHandling"}, retryAnalyzer = Retry.class)
	public void loginErrorValidation() throws IOException {
		// TODO Auto-generated method stub

		String productName ="IPHONE 13 PRO";
		
		ProductCatalogue productCatalogue = landingPage.loginApplication("albin@gmail.com","Ab@123400");	
	   
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());    // Correct 
	//	Assert.assertEquals("Incorrect email  password.", landingPage.getErrorMessage());
	
	}

}
