package com.ecei.proyecto.components;

import com.ecei.proyecto.pom.BaseComponent;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class MenuComponent extends BaseComponent {

    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.saucelabs.mydemoapp.android:id/itemTV' and @text='Catalog']")
    private WebElement catalogOption;

    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id='com.saucelabs.mydemoapp.android:id/itemTV' and @text='About']")
    private WebElement aboutOption;

    private final By aboutBy = By.xpath("//android.widget.TextView[@resource-id=\"com.saucelabs.mydemoapp.android:id/itemTV\" and @text=\"About\"]");
    private final By catalogBy = By.xpath("//android.widget.TextView[@resource-id=\"com.saucelabs.mydemoapp.android:id/itemTV\" and @text=\"Catalog\"]");

    @AndroidFindBy(xpath = "//android.widget.TextView[@resource-id=\"com.saucelabs.mydemoapp.android:id/itemTV\" and @text=\"Drawing\"]")
    private WebElement drawingOption;

    public MenuComponent(AndroidDriver driver, WebElement container) {
        super(driver, container);
    }

    public boolean isOpen() {
        return isVisible(catalogBy) || isVisible(aboutBy);
    }

    public void tapAbout() {
        clickWhenVisible(aboutOption);
    }

    public void tapDrawing() {
        clickWhenVisible(drawingOption);
    }

}


