package com.example.tests;

import com.example.utility.Util;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.logging.Logger;


public class BaseTest {
    protected ThreadLocal<WebDriver> driver = new ThreadLocal<> ();

    @BeforeClass
    //@BeforeMethod
    @Parameters({"browser"})
    public void setUp (String browser) throws MalformedURLException {
        System.out.println ("Browser is : "+browser);
        String host = "localhost";
        if(System.getProperty("HUB_HOST")!=null) {
            host=System.getProperty("HUB_HOST");

        }
        String completeURL="http://" + host +":4444/wd/hub";
        System.out.println("Complete URL : "+completeURL);
        if(browser.equalsIgnoreCase ("chrome")) {
            System.out.println ("Inside : "+browser);
            ChromeOptions options = new ChromeOptions ();
            options.setAcceptInsecureCerts (true);

            DesiredCapabilities desiredCapabilities = new DesiredCapabilities ();
            desiredCapabilities.setCapability (ChromeOptions.CAPABILITY,options);

            URL url = new URL ("http://localhost:4444/wd/hub");

            driver.set (new RemoteWebDriver (new URL (completeURL),desiredCapabilities));
            System.out.println("Driver value is : "+driver.get ());
            System.out.println("URL is : "+driver.get ().getCurrentUrl ());
        }else if (browser.equalsIgnoreCase ("firefox")){
            WebDriverManager.firefoxdriver ().setup();
            driver.set (new FirefoxDriver ());
        }else if (browser.equalsIgnoreCase ("edge")){
            WebDriverManager.edgedriver ().setup();
            driver.set (new EdgeDriver ());
        }else {
            throw new IllegalArgumentException ("Browser value is not supported : "+browser);
        }
        driver.get ().manage ().window ().maximize ();
    }

    @AfterClass
    //@AfterMethod
    public void tearDown () {
        WebDriver webDriver = getDriver ();
        if (webDriver != null) {
            webDriver.quit();
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

    @AfterMethod (alwaysRun = true,enabled = true)
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
