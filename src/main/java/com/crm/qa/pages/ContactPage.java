package com.crm.qa.pages;

import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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

	@FindBy(name = "first_name")
	WebElement firstName;

	@FindBy(name = "last_name")
	WebElement lastName;

	@FindBy(name = "company")
	WebElement companyName;

	@FindBy(xpath = "//div[@class='visible menu transition']")
	WebElement addText;

	@FindBy(xpath = "//button[@class='ui linkedin button']")
	WebElement saveButton;

	public boolean verifyContactLabel() {
		return contactHeading.isDisplayed();
	}

	public void selectContactByName(String contactName) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		By checkBoxLocator = By
				.xpath("//a[contains(text(),'" + contactName + "')]//parent::td//preceding-sibling::td//label");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement checkBox = wait.until(ExpectedConditions.visibilityOfElementLocated(checkBoxLocator));

	}

	public ContactPage createContactPage(String Fname, String Lname, String Cname) throws FileNotFoundException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		driver.findElement(By.xpath("//button[@class='ui linkedin button']//i[@class='edit icon']")).click();
		firstName.sendKeys(Fname);
		lastName.sendKeys(Lname);
		companyName.click();
		WebElement searchInput = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//div[@name='company']//input[@class='search']")));
		searchInput.sendKeys(Cname);

		// Wait for suggestions list
		List<WebElement> options = wait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(By.xpath("//div[@name='company']//div[@role='option']")));

		boolean matched = false;

		for (WebElement option : options) {
			String text = option.getText().trim();

			if (text.equalsIgnoreCase(Cname)) {
				option.click();
				matched = true;
				break;
			}
		}

		// If not matched → create new entry
		if (!matched) {
			searchInput.sendKeys(Keys.ENTER);
		}

		saveButton.click();
		return new ContactPage();
	}

}
