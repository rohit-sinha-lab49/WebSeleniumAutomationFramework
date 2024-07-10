package com.example.capabilities;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class BrowserCapabilities {

    public static DesiredCapabilities getChromeCapabilities() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-notifications");
        options.setAcceptInsecureCerts (true);

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities ();
        desiredCapabilities.setCapability (ChromeOptions.CAPABILITY , options);
        return desiredCapabilities;
    }

    public static DesiredCapabilities getFirefoxCapabilities() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-notifications");
        options.setAcceptInsecureCerts (true);

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities ();
        desiredCapabilities.setCapability (FirefoxOptions.FIREFOX_OPTIONS , options);
        return desiredCapabilities;
    }

    public static DesiredCapabilities getEdgeCapabilities() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-notifications");
        options.setAcceptInsecureCerts (true);

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities ();
        desiredCapabilities.setCapability (EdgeOptions.CAPABILITY , options);
        return desiredCapabilities;
    }
}
