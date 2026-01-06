package com.crm.qa.extentreportlistner;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
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
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportListnerNG implements IReporter {

    private ExtentReports extent;

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String reportPath = outputDirectory + File.separator + "ExtentReport-" + timeStamp + ".html";

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setDocumentTitle("Automation Test Report");
        spark.config().setReportName("Execution Summary");
        spark.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(spark);

        // Use system properties or config if available
        extent.setSystemInfo("Tester", System.getProperty("report.tester", "Ashutosh Singh"));
        extent.setSystemInfo("Environment", System.getProperty("report.env", "QA"));
        extent.setSystemInfo("Tool", "Selenium + TestNG");

        for (ISuite suite : suites) {
            Map<String, ISuiteResult> results = suite.getResults();
            for (ISuiteResult suiteResult : results.values()) {
                ITestContext context = suiteResult.getTestContext();
                buildTestNodes(context.getPassedTests(), Status.PASS);
                buildTestNodes(context.getFailedTests(), Status.FAIL);
                buildTestNodes(context.getSkippedTests(), Status.SKIP);
            }
        }
        extent.flush();
    }

    private void buildTestNodes(IResultMap tests, Status status) {
        if (tests == null || tests.size() == 0) return;

        // deduplicate results (avoids retries appearing multiple times)
        Set<String> seen = new HashSet<>();

        for (ITestResult result : tests.getAllResults()) {
            String uniqueId = result.getMethod().getQualifiedName() + ":" + result.getStartMillis();
            if (seen.contains(uniqueId)) {
                continue;
            }
            seen.add(uniqueId);

            String testName = result.getMethod().getMethodName();
            String description = result.getMethod().getDescription();
            String displayName = (description != null && !description.isEmpty()) ? description : testName;

            ExtentTest test = extent.createTest(displayName);

            // categories: class and groups
            test.assignCategory(result.getTestClass().getName());
            String[] groups = result.getMethod().getGroups();
            for (String g : groups) {
                test.assignCategory(g);
            }

            test.log(Status.INFO, "Class : " + result.getTestClass().getName());

            // Set start/end time
            try {
                test.getModel().setStartTime(new Date(result.getStartMillis()));
                test.getModel().setEndTime(new Date(result.getEndMillis()));
            } catch (Exception e) {
                // ignore if model manipulation unsupported by version
            }

            if (status == Status.FAIL) {
                Throwable t = result.getThrowable();
                if (t != null) {
                    test.log(Status.FAIL, t);
                }
                // Attach screenshot if testcase put path in attribute "screenshot"
                Object screenshotPath = result.getAttribute("screenshot");
                if (screenshotPath != null) {
                    try {
                        test.fail("Screenshot", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath.toString()).build());
                    } catch (Exception ex) {
                        test.info("Could not attach screenshot: " + ex.getMessage());
                    }
                }
            } else if (status == Status.SKIP) {
                Throwable t = result.getThrowable();
                if (t != null) test.log(Status.SKIP, t);
            }

            test.log(status, "Test Status : " + status);
        }
    }
}