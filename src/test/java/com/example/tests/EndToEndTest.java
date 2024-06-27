package com.example.tests;

import com.example.listeners.TestListener;
import com.example.pages.*;
import io.qameta.allure.*;
import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.util.logging.Logger;

@Listeners ({AllureTestNg.class , TestListener.class})
public class EndToEndTest extends BaseTest {

    String url = "https://www.saucedemo.com/v1/index.html";

    @Test (priority = 1)
    @Parameters ({"username" , "password"})
    @Severity (SeverityLevel.CRITICAL)
    public void testLogin (String username , String password) throws InterruptedException {
        System.out.println ("Driver id : " + driver.get ());
        getDriver ().get (url); // Replace with the actual URL

        String title = driver.get ().getTitle ();
        attachScreenshot ();
        attachTextLog ("Title of the page: " + title);
        Logger.getGlobal ().info ("URL : " + url);

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

        SideBarPage sideBarPage = new SideBarPage (getDriver ());
        sideBarPage.clickAllItemsLink ();
        //Wait for page to load
        waitForPageToLoad ();

        ProductsPage productsPage = new ProductsPage (getDriver ());
        productsPage.numberOfItems ("Sauce Labs Backpack");
        productsPage.clickOnCartLink ();

        YourCartPage yourCartPage = new YourCartPage (getDriver ());
        yourCartPage.validateVisibility ();
        yourCartPage.validateProductAndPrice ("Sauce Labs Backpack" , "29.99");
        yourCartPage.clickOnCheckoutBtn ();

        waitForPageToLoad ();

        CheckoutPage checkoutPage = new CheckoutPage (getDriver ());
        checkoutPage.enterCustomerDetails ("Ram" , "Kumar" , "201305/Noida");
        checkoutPage.clickOnContinueBtn ();

        waitForPageToLoad ();
        checkoutPage.clickOnFinishBtn ();

        checkoutPage.validateOrderPlacementMsg ();
    }

    @Test(description = "Verify failure msg",priority = 2)
    @Description ("Verify failure msg")
    @Epic ("EP003")
    @Feature ("Feature3:Login Failure")
    @Story ("Story : Failure Functionality")
    @Step("Verify failure msg")
    @Severity (SeverityLevel.CRITICAL)
    public void testFailure () {
        //Allure.addAttachment ("Failure",new ByteArrayInputStream (((TakesScreenshot) driver.get ()).getScreenshotAs (OutputType.BYTES)));
        System.out.println("Driver value is : "+driver.get ());
        Assert.assertEquals (driver.get ().getTitle ().trim (),"Swag Labs");
    }
}
