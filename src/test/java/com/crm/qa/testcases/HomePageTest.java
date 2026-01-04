package com.crm.qa.testcases;

import java.io.FileNotFoundException;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.crm.qa.base.TestBase;
import com.crm.qa.pages.ContactPage;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;

public class HomePageTest extends TestBase{
	
	LoginPage loginPage;
	HomePage homePage;
	ContactPage contactPage;
	
	public HomePageTest()
	{
		super();
	}
	
	@BeforeMethod
	public void setup() throws FileNotFoundException {
		initialization();
		loginPage= new LoginPage();
		contactPage=new ContactPage();
		homePage=loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
		
	}
	
	@Test (priority=1)
	public void verifyHomePageTitle() {
		String hometitle=homePage.verifyHomePageTitle();
		Assert.assertEquals(hometitle, "Free CRM", "title not matched");
	}
	
	@Test(priority=2)
	public void verifyCorrectUserNameTest() {
		Assert.assertTrue(homePage.verifyCorrectUserName());
	}
	
	@Test(priority=3)
	public void clickOnContactsLinkTest() throws FileNotFoundException {
		contactPage=homePage.clickOnContactsLink();
		
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}
	
	
	
	
	
	

}
