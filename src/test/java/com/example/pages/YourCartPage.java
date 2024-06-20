package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class YourCartPage {

    private WebDriver driver;

    private By qtyEle = By.xpath ("//div[@class='cart_quantity_label']");
    private By descriptionEle = By.xpath ("//div[@class='cart_desc_label']");
    private By itemNameELe = By.xpath ("//div[@class='inventory_item_name']");
    private By priceEle = By.xpath ("//div[@class='inventory_item_price']");
    private By checkoutBtnEle = By.xpath ("//a[@class='btn_action checkout_button']");


    public YourCartPage (WebDriver driver) {
        this.driver = driver;
    }

    public void validateVisibility(){
        if(driver.findElement (qtyEle).isDisplayed () && driver.findElement (descriptionEle).isDisplayed ()){
            Assert.assertTrue (driver.findElement (qtyEle).isDisplayed ());
            Assert.assertTrue (driver.findElement (descriptionEle).isDisplayed ());
        }else{
            Assert.fail ("Not visible");
        }
    }

    public void validateProductAndPrice (String productName , String price) {
        String itemOne = driver.findElement (itemNameELe).getText ().trim ();
        String priceOne = driver.findElement (priceEle).getText ().trim ();
        Assert.assertEquals ( itemOne, productName,"Actual name : "+itemOne+" vs Expected name : "+productName);
        Assert.assertEquals (priceOne , price,"Actual name : "+priceOne+" vs Expected name : "+price);
    }

    public void clickOnCheckoutBtn(){
        driver.findElement (checkoutBtnEle).click ();
    }
}
