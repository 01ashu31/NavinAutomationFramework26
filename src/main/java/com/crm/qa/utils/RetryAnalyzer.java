package com.crm.qa.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer{
	
	 int counter =0;
	 int reTryLimit=3;
	 
	 
	 public boolean retry(ITestResult result) {
		 if(counter < reTryLimit) {
			 counter++;
			 return true;
		 }
		 return false;
	 }
	 

}
