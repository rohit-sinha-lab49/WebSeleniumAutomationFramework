package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private WebDriver driver;

    private By usernameField = By.xpath ("//input[@id='user-name']");
    private By passwordField = By.xpath ("//input[@id='password']");
    private By loginButton = By.xpath ("//input[@id='login-button']");

    public LoginPage (WebDriver driver) {
        this.driver = driver;
    }

    public void setUsername (String username) {
        WebElement userField = driver.findElement (usernameField);
        userField.clear ();
        userField.sendKeys (username);
    }

    public void setPassword (String password) {
        WebElement passField = driver.findElement (passwordField);
        passField.clear ();
        passField.sendKeys (password);
    }

    public void clickLoginButton () {
        driver.findElement (loginButton).click ();
    }
}
