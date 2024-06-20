package com.example.listeners;

import com.example.tests.BaseTest;
import com.example.utility.ScreenshotUtils;
import io.qameta.allure.Attachment;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        Object testClass = result.getInstance();
        WebDriver driver = ((BaseTest) testClass).getDriver();
        if (driver != null) {
            captureScreenshot(driver);
        }
    }

    @Attachment(value = "Failure Screenshot", type = "image/png")
    public byte[] captureScreenshot(WebDriver driver) {
        return ScreenshotUtils.captureScreenshot(driver);
    }

    @Override
    public void onTestStart(ITestResult result) {
        logTestClassName(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logTestClassName(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logTestClassName(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logTestClassName(result);
    }

    @Override
    public void onStart(ITestContext context) {
        // Do nothing
    }

    @Override
    public void onFinish(ITestContext context) {
        // Do nothing
    }

    private void logTestClassName(ITestResult result) {
        System.out.println("Running test in class: " + result.getTestClass().getName());
    }
}

