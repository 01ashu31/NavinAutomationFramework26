package com.crm.qa.extentreportlistner;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportListnerNG implements IReporter{
	
	private ExtentReports extent;

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {

        String reportPath = outputDirectory + File.separator + "ExtentReport.html";

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setDocumentTitle("Automation Test Report");
        spark.config().setReportName("Execution Summary");
        spark.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(spark);

        extent.setSystemInfo("Tester", "Ashutosh Singh");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Tool", "Selenium + TestNG");

        for (ISuite suite : suites) {

            Map<String, ISuiteResult> results = suite.getResults();

            for (ISuiteResult result : results.values()) {

                ITestContext context = result.getTestContext();

                buildTestNodes(context.getPassedTests(), Status.PASS);
                buildTestNodes(context.getFailedTests(), Status.FAIL);
                buildTestNodes(context.getSkippedTests(), Status.SKIP);
            }
        }

        extent.flush();
    }

    private void buildTestNodes(IResultMap tests, Status status) {

        if (tests.size() == 0)
            return;

        Set<ITestResult> resultSet = tests.getAllResults();

        for (ITestResult result : resultSet) {

            String testName = result.getMethod().getMethodName();

            ExtentTest test = extent.createTest(testName);

            // assign class as category
            test.assignCategory(result.getTestClass().getName());

            test.log(Status.INFO, "Class : " + result.getTestClass().getName());

            if (status == Status.FAIL) {
                test.log(Status.FAIL, result.getThrowable());
            }

            test.log(status, "Test Status : " + status);
        }
    }
}
