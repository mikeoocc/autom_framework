package com.ecei.proyecto.pom;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public abstract class BaseComponent {

    protected AndroidDriver driver;
    protected WebElement container;
    protected Duration timeout = Duration.ofSeconds(10);

    protected WebDriverWait wait;

    protected BaseComponent(AndroidDriver driver, WebElement container) {
        this.driver = driver;
        this.container = container;
        this.wait = new WebDriverWait(driver, timeout);

        PageFactory.initElements(
                new AppiumFieldDecorator(driver, timeout),
                this
        );
    }

    public void clickWhenVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.click();
    }

    public boolean isVisible(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}


