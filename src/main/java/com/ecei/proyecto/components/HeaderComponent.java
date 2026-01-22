package com.ecei.proyecto.components;

import com.ecei.proyecto.pom.BaseComponent;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;

public class HeaderComponent extends BaseComponent {

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/menuIV")
    private WebElement menuButton;

    public HeaderComponent(AndroidDriver driver, WebElement container) {
        super(driver, container);
    }

    public void tapMenu() {
        menuButton.click();
    }
}

