package com.example.capabilities;

import com.example.utility.Constants;
import com.example.utility.DockerComposeStatusChecker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.net.URL;

import static com.example.tests.BaseTest.hubURL;

public class DriverFactory {
    private static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    public enum Browser {
        CHROME,
        FIREFOX,
        Edge
        // Add more browsers here as needed
    }

    public static WebDriver getDriver(Browser browser,String runEnv) throws IOException {
        // Return the WebDriver instance associated with the current thread and browser
        //boolean flag = DockerComposeStatusChecker.isDockerComposeServiceUp (Constants.serviceName);
        if (threadLocalDriver.get() == null) {
            switch (browser) {
                case CHROME:
                    if(runEnv.equals ("docker")) {
                        threadLocalDriver.set(new RemoteWebDriver (new URL (hubURL ()) , BrowserCapabilities.getChromeCapabilities ()));
                    }else {
                        threadLocalDriver.set (new ChromeDriver ());
                    }
                    break;
                case FIREFOX:
                    if(runEnv.equals ("docker")) {
                        threadLocalDriver.set(new RemoteWebDriver (new URL (hubURL ()) , BrowserCapabilities.getFirefoxCapabilities ()));
                    }else {
                        threadLocalDriver.set (new FirefoxDriver ());
                    }
                    break;
                case Edge:
                    if(runEnv.equals ("docker")) {
                        threadLocalDriver.set(new RemoteWebDriver (new URL (hubURL ()) , BrowserCapabilities.getEdgeCapabilities ()));
                    }else{
                        threadLocalDriver.set(new EdgeDriver ());
                    }
                    break;
                // Add cases for more browsers here
                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browser);
            }
        }
        return threadLocalDriver.get();
    }

    public static void quitDriver() {
        // Quit the WebDriver instance and remove it from ThreadLocal
        WebDriver driver = threadLocalDriver.get();
        if (driver != null) {
            driver.quit();
            threadLocalDriver.remove();
        }
    }

}

