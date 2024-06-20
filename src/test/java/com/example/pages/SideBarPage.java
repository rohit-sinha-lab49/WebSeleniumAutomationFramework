package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SideBarPage {

    private WebDriver driver;

    private By allItemsEle = By.xpath ("//div[@class='bm-menu']//nav//a[1]");
    private By aboutEle = By.xpath ("//div[@class='bm-menu']//nav//a[2]");
    private By logoutEle = By.xpath ("//div[@class='bm-menu']//nav//a[3]");
    private By resetAppStateEle = By.xpath ("//div[@class='bm-menu']//nav//a[4]");

    public SideBarPage (WebDriver driver) {
        this.driver = driver;
    }

    public void clickAllItemsLink () {
        driver.findElement (allItemsEle).click ();
    }

    public void clickAboutLink () {
        driver.findElement (aboutEle).click ();
    }

    public void clickLogoutLink () {
        driver.findElement (logoutEle).click ();
    }

    public void clickResetAppStateLink () {
        driver.findElement (resetAppStateEle).click ();
    }
}
