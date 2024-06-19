package com.example.tests;

import io.qameta.allure.Attachment;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

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
}
