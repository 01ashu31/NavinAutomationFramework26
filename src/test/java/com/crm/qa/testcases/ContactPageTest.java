package com.crm.qa.testcases;

import java.io.FileNotFoundException;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.crm.qa.base.TestBase;
import com.crm.qa.pages.ContactPage;
import com.crm.qa.pages.HomePage;
import com.crm.qa.pages.LoginPage;

public class ContactPageTest extends TestBase {

	HomePage homePage;
	LoginPage loginPage;
	ContactPage contactPage;

	public ContactPageTest() {
		super();
	}

	@BeforeMethod
	public void setUp() throws FileNotFoundException {
		initialization();
		contactPage = new ContactPage();
		loginPage=new LoginPage();
		homePage=new HomePage();
		homePage = loginPage.login(prop.getProperty("username"), prop.getProperty("password"));
		contactPage=homePage.clickOnContactsLink();
		
	}

	@Test(priority=1)
	public void verifyContactLabelTest() {
		Assert.assertTrue(contactPage.verifyContactLabel());
	}
	
	@Test(priority=2, enabled=false)
	public void verifySelectContactByName() {
		contactPage.selectContactByName("Ashutest singh test");
	}
	
	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
