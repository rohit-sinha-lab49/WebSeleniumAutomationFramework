package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.logging.Logger;

public class CheckoutPage {

    private WebDriver driver;

    private By firstNameELe = By.xpath ("//input[@id='first-name']");
    private By lastNameELe = By.xpath ("//input[@id='last-name']");
    private By zipPostalCodeELe = By.xpath ("//input[@id='postal-code']");
    private By continueBtnEle = By.xpath ("//input[@value='CONTINUE']");
    private By finishBtnELe = By.xpath ("//a[@class='btn_action cart_button']");
    private By orderPlacementELeOne = By.xpath ("//h2[@class='complete-header']");
    private By orderPlacementEleTwo = By.xpath ("//div[@class='complete-text']");

    public CheckoutPage (WebDriver driver) {
        this.driver = driver;
    }

    public void enterCustomerDetails(String firstName,String lastName, String postalCode){
        driver.findElement (firstNameELe).clear ();
        driver.findElement (firstNameELe).sendKeys (firstName);
        driver.findElement (lastNameELe).clear ();
        driver.findElement (lastNameELe).sendKeys (lastName);
        driver.findElement (zipPostalCodeELe).clear ();
        driver.findElement (zipPostalCodeELe).sendKeys (postalCode);
    }

    public void clickOnContinueBtn(){
        driver.findElement (continueBtnEle).click ();
    }

    public void clickOnFinishBtn(){
        driver.findElement (finishBtnELe).click ();
    }

    public void validateOrderPlacementMsg(){
        Logger.getGlobal ().info (driver.findElement (orderPlacementELeOne).getText ().trim ());
        Logger.getGlobal ().info (driver.findElement (orderPlacementEleTwo).getText ().trim ());
        Assert.assertEquals (driver.findElement (orderPlacementELeOne).getText ().trim (),"THANK YOU FOR YOUR ORDER");
        Assert.assertEquals (driver.findElement (orderPlacementEleTwo).getText ().trim (),"Your order has been dispatched, and will arrive just as fast as the pony can get\n" +
                "                there!\n" +
                "            ");
    }

}
