package com.crm.qa.testcases;

import java.io.FileNotFoundException;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.crm.qa.base.TestBase;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;

public class LoginPageTest extends TestBase{
	
	LoginPage loginPage;
	HomePage homePage;

	public LoginPageTest() throws FileNotFoundException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@BeforeMethod 
	public void setup() throws FileNotFoundException
	{
		initialization();
		loginPage= new LoginPage();	
	}
	
	@Test(priority=1)
	public void loginPageTitleTest() {
		String title=loginPage.validateLoginPageTitle();
		Assert.assertEquals(title, "#1 Free CRM Business Software - Free Forever");
	}
	
	@Test(priority=2)
	public void crmLogoImageTest() {
		boolean logo=loginPage.validateCRMImage();
		Assert.assertTrue(logo);
	}
	
	@Test(priority=3)
	public void loginTest() throws FileNotFoundException {
		homePage=loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
		
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
