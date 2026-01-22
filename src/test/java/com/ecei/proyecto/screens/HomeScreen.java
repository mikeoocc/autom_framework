package com.ecei.proyecto.screens;

import com.ecei.proyecto.components.HeaderComponent;
import com.ecei.proyecto.components.MenuComponent;
import com.ecei.proyecto.pom.BaseScreen;

import org.openqa.selenium.WebElement;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class HomeScreen extends BaseScreen {

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/productTV")
    private WebElement productsTitle;

    private HeaderComponent header;
    private MenuComponent menu;

    public HomeScreen(AndroidDriver driver) {
        super(driver);
    }

    public HeaderComponent header() {
        if (header == null) header = super.header();
        return header;
    }

    public MenuComponent menu() {
        if (menu == null) menu = super.menu();
        return menu;
    }

    public boolean isDisplayed() {
        return productsTitle.isDisplayed();
    }

    public void openMenu() {
        header().tapMenu();
    }
}



