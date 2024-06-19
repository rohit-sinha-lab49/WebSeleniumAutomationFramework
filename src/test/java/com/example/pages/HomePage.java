package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class HomePage {

    private WebDriver driver;

    private By openMenu = By.xpath ("//button[normalize-space()='Open Menu']");

    public HomePage (WebDriver driver) {
        this.driver = driver;
    }

    public void clickLoginButton () {
        driver.findElement (openMenu).click ();
    }

    public void assertItemsOnOpenPanel(){
        List<WebElement> lisOfItems = driver.findElements (By.xpath ("//div[@class='bm-menu']//nav//a"));
        String[] arr = {"All Items","About","Logout","Reset App State"};

        if(lisOfItems.size ()==arr.length){
            int i=0;
            for(WebElement element : lisOfItems) {
                Assert.assertEquals (element.getText ().trim (),arr[i]);
                i++;
            }
        }else{
            Assert.fail ("Size is not equal : "+lisOfItems.size ()+" vs "+arr.length);
        }
    }

}
