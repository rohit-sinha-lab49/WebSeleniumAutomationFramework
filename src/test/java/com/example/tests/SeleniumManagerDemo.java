package com.example.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SeleniumManagerDemo {

    @Test
    public void executeTest() throws InterruptedException {
        WebDriver driver = new FirefoxDriver ();
        driver.get ("https://the-internet.herokuapp.com/tables");

        Thread.sleep (5000);
        WebElement lastNameEle = driver.findElement (By.xpath ("//table[@id='table1']//span[text()='Last Name']"));
        lastNameEle.click ();

        List<WebElement> rows = driver.findElements (By.xpath ("//table[@id='table1']/tbody/tr"));
        Map<String,String> linkedHashMap = new LinkedHashMap<> ();
        Map<String,String> treeMap = new TreeMap<> ();
        for(WebElement row : rows){
            String lastName = row.findElement (By.xpath ("td[1]")).getText ();
            String emailID = row.findElement (By.xpath ("td[3]")).getText ();
            linkedHashMap.put (lastName,emailID);
            treeMap.put (lastName,emailID);
        }

        System.out.println (linkedHashMap);
        System.out.println (treeMap);

        System.out.println (driver.getTitle ());
        Thread.sleep (5000);

        Assert.assertEquals (linkedHashMap,treeMap);
        driver.quit ();
    }
}
