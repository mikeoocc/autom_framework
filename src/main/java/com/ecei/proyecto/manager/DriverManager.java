package com.ecei.proyecto.manager;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import java.net.URL;
import java.net.MalformedURLException;

public class DriverManager {

    private static ThreadLocal<AndroidDriver> DRIVER = new ThreadLocal<>();

    public static void create() {
        
        if (DRIVER.get() != null) {
            return;
        }

        UiAutomator2Options options = CapabilitiesManager.buildOptions();
        URL url;

        try {

            if (CapabilitiesManager.isSauceLabs()) {
                url = new URL(Config.get("SAUCELABS_TEST_URL"));
            } else {
                ServerManager.start();
                url = new URL(ServerManager.getUrl());
            }

        } catch (MalformedURLException e) {
            throw new IllegalStateException("Invalid Appium URL", e);
        }

        AndroidDriver driver = new AndroidDriver(url, options);
        DRIVER.set(driver);
    }

    public static AndroidDriver get() {
        AndroidDriver driver = DRIVER.get();
        return driver;
    }

    public static void quit() {
        AndroidDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
        if (!CapabilitiesManager.isSauceLabs()) {
            ServerManager.stop();
        }
    }

}