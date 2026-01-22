package com.ecei.proyecto.appiumgluecode;

import com.ecei.proyecto.components.MenuComponent;
import com.ecei.proyecto.manager.DriverManager;
import com.ecei.proyecto.screens.AboutScreen;
import com.ecei.proyecto.screens.DrawingScreen;
import com.ecei.proyecto.screens.HomeScreen;

import io.appium.java_client.android.AndroidDriver;

public class BaseTest {

    protected AndroidDriver driver() {
        return DriverManager.get();
    }

    private HomeScreen homeScreen;
    private MenuComponent menu;
    private AboutScreen about;

    private DrawingScreen drawing;

    protected HomeScreen home() {
        if (homeScreen == null) {
            homeScreen = new HomeScreen(driver());
        }
        return homeScreen;
    }

    protected AboutScreen about() {
        if (about == null) about = new AboutScreen(driver());
        return about;
    }

    protected DrawingScreen drawing() {
        if (drawing == null) drawing = new DrawingScreen(driver());
        return drawing;
    }
}


