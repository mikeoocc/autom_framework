package com.ecei.proyecto.runner;

import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import com.ecei.proyecto.manager.DriverManager;

public class TestRunner {

    private static AndroidDriver driver;

    @BeforeAll
    static void setup() {
        DriverManager.create();
    }

    @Test
    void comprobarAppAbierta() {
        Assertions.assertNotNull(DriverManager.get().getSessionId());
        DriverManager.get().findElement(By.id("com.saucelabs.mydemoapp.android:id/menuIV")).click();
    }

    @AfterAll
    static void tearDown() {
        DriverManager.quit();
    }

}

