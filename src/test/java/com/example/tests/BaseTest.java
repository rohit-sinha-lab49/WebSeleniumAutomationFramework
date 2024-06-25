package com.example.tests;

import com.example.utility.Util;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.logging.Logger;


public class BaseTest {
    protected ThreadLocal<WebDriver> driver = new ThreadLocal<> ();

    @BeforeClass
    //@BeforeMethod
    @Parameters({"browser"})
    public void setUp (String browser) {
        System.out.println ("Browser is : "+browser);
        if(browser.equalsIgnoreCase ("chrome")) {
            driver.set (new ChromeDriver ());
        }else if (browser.equalsIgnoreCase ("firefox")){
            driver.set (new FirefoxDriver ());
        }else if (browser.equalsIgnoreCase ("edge")){
            driver.set (new EdgeDriver ());
        }else {
            throw new IllegalArgumentException ("Browser value is not supported : "+browser);
        }
        driver.get ().manage ().window ().maximize ();
    }

    @AfterClass
    //@AfterMethod
    public void tearDown () {
        if (driver.get () != null) {
            driver.get ().quit ();
            driver.remove ();
        }
    }

    public WebDriver getDriver () {
        return driver.get ();
    }

    @Attachment (value = "Page Screenshot", type = "image/png")
    public byte[] attachScreenshot() {
        // Capture and return screenshot bytes here
        return new byte[0];
    }

    @Attachment(value = "Text Log", type = "text/plain")
    public String attachTextLog(String message) {
        return message;
    }

    public void waitForPageToLoad() {
        new WebDriverWait (getDriver (), Duration.ofSeconds (10000)).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
        );
    }

    public void waitForElement(By locator) {
        new WebDriverWait(getDriver (), Duration.ofSeconds (10000)).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @AfterMethod (alwaysRun = true)
    public synchronized  void updateTestStatus(ITestResult result) throws IOException {
        Logger.getGlobal ().info ("Updating result of test script "+result.getName ()+" to report :: updateTestStatus");
        System.out.println("Driver value is : "+driver.get ());
        if(result.getStatus ()==ITestResult.FAILURE){
            System.out.println("Driver value is : "+driver.get ());
            System.out.println ("Failure is observed for test : "+result.getName ());
            File realScreenshotFileObtained = Util.takeScreenshot (driver.get (),result.getName ());
            Allure.addAttachment ("Page Screenshot for test : "+result.getName (), FileUtils.openInputStream (realScreenshotFileObtained));
        }else{
            System.out.println ("No failure is observed for test : "+result.getName ());
        }
    }
}
