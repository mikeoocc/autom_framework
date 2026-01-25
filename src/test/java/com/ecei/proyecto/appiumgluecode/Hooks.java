package com.ecei.proyecto.appiumgluecode;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import com.ecei.proyecto.manager.DriverManager;

public class Hooks {

    private static int numberOfCase = 0;

    @Before
    public void setUp() {
        numberOfCase++;
        System.out.println("Ejecutando escenario " + numberOfCase);
        DriverManager.create();
        DriverManager.get().terminateApp("com.saucelabs.mydemoapp.android");
        DriverManager.get().activateApp("com.saucelabs.mydemoapp.android");
    }

    @After
    public void tearDown() {
        System.out.println("El escenario " + numberOfCase + " ha terminado");
        DriverManager.quit();
    }
}
