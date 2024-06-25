package com.example.listeners;

import com.example.tests.BaseTest;
import com.example.utility.ScreenshotUtils;
import com.example.utility.Util;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class TestListener implements ITestListener {

    private static String getTestMethodName(ITestResult iTestResult){
        return iTestResult.getMethod ().getConstructorOrMethod ().getName ();
    }

    @Attachment
    public File saveFailureScreenShot(WebDriver driver){
        return ((TakesScreenshot)driver).getScreenshotAs (OutputType.FILE);
    }

    @Attachment(value = "Screenshot",type = "image/png")
    public static String saveTextLog(String message){
        return message;
    }

    public void onStart(ITestContext iTestContext){
        System.out.println ("I am in onStart method "+iTestContext.getName ());
        BaseTest baseTest = new BaseTest ();
        iTestContext.setAttribute ("WebDriver",baseTest.getDriver ());
    }

    public void onFinish(ITestContext iTestContext){
        System.out.println ("I am in onFinish method "+iTestContext.getName ());
    }

    public void onTestStart(ITestResult iTestResult){
        System.out.println ("I am in onTestStart method "+getTestMethodName (iTestResult));
    }

    public void onTestSuccess(ITestResult iTestResult){
        System.out.println ("I am in onTestSuccess method "+getTestMethodName (iTestResult));
    }

    public void onTestFailure(ITestResult iTestResult){
        System.out.println ("I am in onTestFailure method "+getTestMethodName (iTestResult));

       /* Object testClass = iTestResult.getInstance ();
        WebDriver driver = BaseClass.getDriver ();

        if(driver instanceof WebDriver){
            System.out.println("Screenshot captured for test case : "+getTestMethodName (iTestResult));
            //File file = saveFailureScreenShot (driver);
            Allure.addAttachment("Screenshot", new ByteArrayInputStream (((TakesScreenshot)driver).getScreenshotAs (OutputType.BYTES)));
        }
        saveTextLog (getTestMethodName (iTestResult)+"Failed and screenshot taken!");*/
    }

    public void onTestSkipped(ITestResult iTestResult){
        System.out.println ("I am in onTestSkipped method "+getTestMethodName (iTestResult));
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult){
        System.out.println("Test failed but it is in defined success ratio "+getTestMethodName (iTestResult));
    }
}

