package com.ecei.proyecto.pom;

import com.ecei.proyecto.components.HeaderComponent;
import com.ecei.proyecto.components.MenuComponent;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public abstract class BaseScreen {

    protected AndroidDriver driver;
    protected final Duration timeout = Duration.ofSeconds(10);

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/header")
    protected WebElement headerContainer;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/menuRV")
    private WebElement menuContainer;

    protected BaseScreen(AndroidDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, timeout), this);
    }

    protected HeaderComponent header() {
        return new HeaderComponent(driver, headerContainer);
    }

    protected MenuComponent menu() {
        return new MenuComponent(driver, menuContainer);
    }





}


