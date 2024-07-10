package com.example.tests;

import com.example.capabilities.BrowserCapabilities;
import com.example.capabilities.DriverFactory;
import com.example.utility.Constants;
import com.example.utility.DockerComposeStatusChecker;
import com.example.utility.EnvironmentSetup;
import com.example.utility.Util;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
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
    public WebDriver driver;

    @BeforeClass
    @Parameters ({"browser","runEnv"})
    public void setUp (String browser, String runEnv) throws IOException {
        System.out.println ("Browser is : " + browser);
        //boolean flag = DockerComposeStatusChecker.isDockerComposeServiceUp (Constants.serviceName);
        if (browser.equalsIgnoreCase ("chrome")) {
            driver = DriverFactory.getDriver  (DriverFactory.Browser.CHROME,runEnv);
        } else if (browser.equalsIgnoreCase ("firefox")) {
            driver = DriverFactory.getDriver  (DriverFactory.Browser.FIREFOX,runEnv);
        } else if (browser.equalsIgnoreCase ("edge")) {
            driver = DriverFactory.getDriver  (DriverFactory.Browser.Edge,runEnv);
        } else {
            throw new IllegalArgumentException ("Browser value is not supported : " + browser);
        }
        driver.manage ().window ().maximize ();
    }

    @AfterClass
    public void tearDown () {
        DriverFactory.quitDriver ();
    }

    public WebDriver getDriver () {
        return driver;
    }

    @Attachment (value = "Page Screenshot", type = "image/png")
    public byte[] attachScreenshot () {
        // Capture and return screenshot bytes here
        return new byte[0];
    }

    @Attachment (value = "Text Log", type = "text/plain")
    public String attachTextLog (String message) {
        return message;
    }

    public void waitForPageToLoad () {
        new WebDriverWait (getDriver () , Duration.ofSeconds (10000)).until (
                webDriver -> ((JavascriptExecutor) webDriver).executeScript ("return document.readyState").equals ("complete")
        );
    }

    public void waitForElement (By locator) {
        new WebDriverWait (getDriver () , Duration.ofSeconds (10000)).until (ExpectedConditions.visibilityOfElementLocated (locator));
    }

    @AfterMethod (alwaysRun = true, enabled = true)
    public synchronized void updateTestStatus (ITestResult result) throws IOException {
        Logger.getGlobal ().info ("Updating result of test script " + result.getName () + " to report :: updateTestStatus");
        System.out.println ("Driver value is : " + driver);
        if (result.getStatus () == ITestResult.FAILURE) {
            System.out.println ("Driver value is : " + driver);
            System.out.println ("Failure is observed for test : " + result.getName ());
            File realScreenshotFileObtained = Util.takeScreenshot (driver , result.getName ());
            Allure.addAttachment ("Page Screenshot for test : " + result.getName () , FileUtils.openInputStream (realScreenshotFileObtained));
        } else {
            System.out.println ("No failure is observed for test : " + result.getName ());
        }
    }

    public static String hubURL () {
        String host = "localhost";
        if (System.getProperty ("HUB_HOST") != null) {
            host = System.getProperty ("HUB_HOST");

        }
        String completeURL = "http://" + host + ":4444/wd/hub";
        System.out.println ("Complete URL : " + completeURL);
        return completeURL;
    }
}
