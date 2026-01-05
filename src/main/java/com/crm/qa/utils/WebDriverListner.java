package com.crm.qa.utils;

import java.io.IOException;

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
	
	public void onException(Throwable error) {
		System.out.println("Exception occur" + error);
		try {
			TestUtils.taksScreenshotAtEndOdTest();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
