package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;
import java.util.logging.Logger;

public class ProductsPage {

    private WebDriver driver;

    private By shoppingCartEle = By.xpath ("//div[@id='shopping_cart_container']/a");

    public ProductsPage (WebDriver driver) {
        this.driver = driver;
    }

    public void numberOfItems (String productName) {
        String xpath = "//div[@id='inventory_container']//div[@id='inventory_container']//div[@class='inventory_list']/div";
        List<WebElement> listOfItems = driver.findElements (By.xpath (xpath));
        Assert.assertEquals (listOfItems.size (),6);
        if(listOfItems.size ()==6){
            for(WebElement ele : listOfItems){
                String label = ele.findElement (By.xpath ("//div[@class='inventory_item_label']//a/div")).getText ().trim ();
                if(productName.equalsIgnoreCase (label)){
                    String price = ele.findElement (By.xpath ("//div[@class='pricebar']")).getText ().trim ();
                    WebElement btn = ele.findElement (By.xpath ("//div[@class='pricebar']//button"));
                    btn.click ();

                    Logger.getGlobal ().info ("Product : "+productName+" is added to the cart whose price is : "+price);
                    break;
                }
            }
        }else {
            Assert.fail ("Size is incorrect : "+listOfItems.size ());
        }
    }

    public void clickOnCartLink(){
        driver.findElement (shoppingCartEle).click ();
    }

}
