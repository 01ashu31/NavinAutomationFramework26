package com.crm.qa.pages;

import java.io.FileNotFoundException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.crm.qa.base.TestBase;

public class ContactPage extends TestBase {

	public ContactPage() throws FileNotFoundException {
		super();
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//span[@class='selectable '] ")
	WebElement contactHeading;

	public boolean verifyContactLabel() {
		return contactHeading.isDisplayed();
	}

	public void selectContactByName(String contactName) {
		JavascriptExecutor js= (JavascriptExecutor) driver;
		By checkBoxLocator=By.xpath(
				"//a[contains(text(),'"+contactName+"')]//parent::td//preceding-sibling::td//label");
		WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(20));
		WebElement checkBox=wait.until(ExpectedConditions.visibilityOfElementLocated(checkBoxLocator));

//	
		
	}

}
