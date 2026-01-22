package com.ecei.proyecto.screens;

import com.ecei.proyecto.pom.BaseScreen;

import org.openqa.selenium.WebElement;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class AboutScreen extends BaseScreen {

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/aboutTV")
    private WebElement aboutTitleLocator;

    public AboutScreen(AndroidDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return aboutTitleLocator.isDisplayed();
    }
}
