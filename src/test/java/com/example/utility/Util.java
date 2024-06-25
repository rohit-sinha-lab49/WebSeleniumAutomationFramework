package com.example.utility;

import com.example.tests.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class Util {

    public static synchronized File takeScreenshot (WebDriver driver , String testName) {
        Logger.getGlobal ().info ("Capturing the screenshot :: takeScreenshot");
        String screenshotPath;
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File src = takesScreenshot.getScreenshotAs (OutputType.FILE);
        try {
            screenshotPath = System.getProperty ("user.dir") + "\\screenshots\\" + timestamp ()+"_"+testName + "_screenshot.png";
            Logger.getGlobal ().info ("The screenshot is saved at " + screenshotPath);
            File dest = new File (screenshotPath);
            FileUtils.copyFile (src , dest);
        } catch (IOException e) {
            Logger.getGlobal ().info ("Failed to capture the screenshot :: takesScreenshot " + e);
        }
        return src;
    }

    public static String timestamp() {
        return new SimpleDateFormat ("yyyy-MM-dd HH-mm-ss").format(new Date ());
    }
}
