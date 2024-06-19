package com.example.tests;

import com.example.pages.HomePage;
import com.example.pages.LoginPage;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.logging.Logger;

@Listeners ({AllureTestNg.class})
public class HomeTest extends BaseTest {

    String url = "https://www.saucedemo.com/v1/index.html";

    @Test
    @Parameters ({"username" , "password"})
    @Severity (SeverityLevel.CRITICAL)
    public void testLogin (String username, String password) throws InterruptedException {
        System.out.println ("Driver id : "+driver.get ());
        getDriver ().get (url); // Replace with the actual URL

        String title = driver.get ().getTitle();
        attachScreenshot();
        attachTextLog("Title of the page: " + title);
        Logger.getGlobal ().info ("URL : "+url);

        LoginPage loginPage = new LoginPage (getDriver ());

        loginPage.setUsername (username);
        loginPage.setPassword (password);
        loginPage.clickLoginButton ();

        // Add your assertion to verify login success
        String expectedUrl = "https://www.saucedemo.com/v1/inventory.html"; // Replace with the expected URL after login
        Assert.assertEquals (getDriver ().getCurrentUrl () , expectedUrl);
        Thread.sleep (5000);

        HomePage homePage = new HomePage (getDriver ());

        homePage.clickLoginButton ();
        Thread.sleep (2000);

        homePage.assertItemsOnOpenPanel ();
    }
}
