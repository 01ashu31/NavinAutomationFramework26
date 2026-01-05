package com.crm.qa.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.crm.qa.base.TestBase;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

public class TestUtils extends TestBase{

	public static long PAGE_LOAD_TIMEOUT = 20;
	public static long IMPLICITLY_WAIT = 10;
	public static Workbook book;
	public static Sheet sheet;

	public static String Test_Data_sheet_Path = "H:\\Eclipse\\naveenAutomationframework\\src\\main\\java\\com\\crm\\qa\\testdata\\CRMNaveen.xlsx";
	
	public static Object[][] getTestData(String sheetName) throws EncryptedDocumentException{
		FileInputStream file=null;
		try {
			file= new FileInputStream(Test_Data_sheet_Path);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book= WorkbookFactory.create(file);
		}catch(InvalidFormatException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		sheet= book.getSheet(sheetName);
		Object[][] data= new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		for(int i=0;i<sheet.getLastRowNum();i++) {
			for(int k=0;k<sheet.getRow(0).getLastCellNum();k++) {
				data[i][k]=sheet.getRow(i+1).getCell(k).toString();
				
			}
		}
		
		return data;
	}
	
	public static void taksScreenshotAtEndOdTest() throws IOException {
		File srcFile=((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir=System.getProperty("user.dir");
		FileUtils.copyFile(srcFile, new File(currentDir + "/screenshot/"+ System.currentTimeMillis()+".png"));
	}

}
