package com.crm.qa.pages;

import java.io.FileNotFoundException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.crm.qa.base.TestBase;

public class HomePage extends TestBase {

	Actions action = new Actions(driver);

	public HomePage() throws FileNotFoundException {
		super();
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@id=\"top-header-menu\"]//b[contains(text(),'Ashutosh')]")
	WebElement userNameLabel;

	@FindBy(xpath = "//div[@class='ui left fixed vertical  icon menu sidebar-dark left-to-right']")
	WebElement sideBar;

	@FindBy(xpath = "//span[contains(text(),'Contacts')]")
	WebElement contactButton;

	@FindBy(xpath = "//span[contains(text(),'Deals')]")
	WebElement dealsButton;

	@FindBy(xpath = "//span[contains(text(),'Tasks')]")
	WebElement tasksButton;

	@FindBy(xpath = "//a[@href='/contacts']//following-sibling::button/i")
	WebElement createContactButton;

	public String verifyHomePageTitle() {
		return driver.getTitle();
	}

	public boolean verifyCorrectUserName() {
		return userNameLabel.isDisplayed();
	}

	public ContactPage clickOnContactsLink() throws FileNotFoundException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		action.moveToElement(sideBar).perform();
		contactButton.click();
		action.moveToElement(userNameLabel).perform();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("table.ui.celled.table")));
		return new ContactPage();

	}

	public ContactPage createNewContact() throws FileNotFoundException {

		action.moveToElement(sideBar).perform();
		contactButton.click();
		return new ContactPage();
	}

	public DealsPage clickOnDealsButton() throws FileNotFoundException {
		action.moveToElement(sideBar).perform();
		dealsButton.click();
		return new DealsPage();
	}

	public TasksPage clickOnTasksButton() throws FileNotFoundException {
		action.moveToElement(sideBar).perform();
//		driver.findElement(RelativeLocator.with(By.xpath("//a[@href='/contacts']"))
//				.toRightOf(By.xpath("//i[@class='plus inverted icon']"))).click();
		tasksButton.click();
		return new TasksPage();
	}

}
