package com.crm.qa.utils;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

import com.crm.qa.base.TestBase;

public class WebDriverListner extends TestBase implements WebDriverListener {
	@Override
	public void beforeClick(WebElement element) {
		System.out.println("Clicking: " + element);
	}

	@Override
	public void afterClick(WebElement element) {
		System.out.println("Clicked: " + element);
	}

	public void onException(Throwable error, WebDriver driver) {
		System.out.println("Exception occur" + error.getMessage());
		try {
			TestUtils.taksScreenshotAtEndOdTest();
			System.out.println("📸 Screenshot captured for failure");
		} catch (IOException e) {
			System.out.println("❌ Failed to capture screenshot");
			e.printStackTrace();
		}
	}

}
