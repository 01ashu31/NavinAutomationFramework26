package com.crm.qa.pages;

import java.io.FileNotFoundException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.crm.qa.base.TestBase;

public class LoginPage extends TestBase{

	public LoginPage() throws FileNotFoundException {
		super();
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//a[@href='https://ui.freecrm.com']")
	WebElement logInButton;
	
	@FindBy(xpath="//input[@placeholder='Email']")
	WebElement userName;
	
	@FindBy( name="password")
	WebElement passowrd;
	
	@FindBy(css ="div[class*='submit button']")
	WebElement loginButton;
	
	@FindBy(xpath="//a[contains(text(), 'Sign Up')]")
	WebElement signupButton;
	
	@FindBy(xpath="(//div[@class='rd-navbar-brand'])[1]")
	WebElement crmlogo;
	
	
	public String validateLoginPageTitle() {
		return driver.getTitle();
	}
	
	public boolean validateCRMImage() {
		return crmlogo.isDisplayed();
	}
	
	public HomePage login(String un, String pswd) throws FileNotFoundException {
		logInButton.click();
		userName.sendKeys(un);
		passowrd.sendKeys(pswd);
		loginButton.click();
		
		return new HomePage();
		
	}
	
	

}
