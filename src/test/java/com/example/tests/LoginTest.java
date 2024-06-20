package com.example.tests;

import com.example.listeners.TestListener;
import com.example.pages.HomePage;
import com.example.pages.LoginPage;
import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.logging.Logger;

@Listeners ({AllureTestNg.class, TestListener.class})
public class LoginTest extends BaseTest {

    String url = "https://www.saucedemo.com/v1/index.html";

    @Test(priority = 1)
    @Parameters ({"username" , "password"})
    public void testLogin (String username, String password) {
        System.out.println ("Driver id : "+driver.get ());
        getDriver ().get (url); // Replace with the actual URL

        Logger.getGlobal ().info ("URL : "+url);

        LoginPage loginPage = new LoginPage (getDriver ());

        loginPage.setUsername (username);
        loginPage.setPassword (password);
        loginPage.clickLoginButton ();

        // Add your assertion to verify login success
        String expectedUrl = "https://www.saucedemo.com/v1/inventory.html"; // Replace with the expected URL after login
        Assert.assertEquals (getDriver ().getCurrentUrl () , expectedUrl);
    }

    @Test(priority = 2)
    public void testHome() throws InterruptedException {
        HomePage homePage = new HomePage (getDriver ());

        Thread.sleep (5000);
        homePage.clickLoginButton ();
        Thread.sleep (2000);

        homePage.assertItemsOnOpenPanel ();
    }
}
