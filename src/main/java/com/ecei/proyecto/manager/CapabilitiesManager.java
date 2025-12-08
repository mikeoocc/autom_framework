package com.ecei.proyecto.manager;

import io.appium.java_client.android.options.UiAutomator2Options;
import java.util.Map;
import java.util.HashMap;

public class CapabilitiesManager {

    // Verifica si es local o sauce labs.
    public static boolean isSauceLabs() {
        return "saucelabs".equalsIgnoreCase(Config.get("RUN_TARGET"));
    }

    // Construye las capabilities dependiendo de si es local o sauce labs.
    public static UiAutomator2Options buildOptions() {
        return isSauceLabs() ? sauceOptions() : sauceOptions();
    }

    // Construye las capabilities para sauce labs.
    private static UiAutomator2Options sauceOptions() {
        
        UiAutomator2Options options = new UiAutomator2Options();

        options.setPlatformName(Config.get("PLATFORM_NAME"));
        options.setDeviceName(Config.get("DEVICE_NAME"));
        options.setPlatformVersion(Config.get("PLATFORM_VERSION"));
        //options.setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2);
        options.setAutomationName(Config.get("AUTOMATION_NAME"));
        options.setApp(Config.get("SAUCE_APP"));
        
        Map<String, Object> sauce = new HashMap<>();
        sauce.put("username", Config.get("SAUCE_USERNAME"));
        sauce.put("accessKey", Config.get("SAUCE_ACCESS_KEY"));
        sauce.put("build", Config.get("SAUCE_BUILD"));
        sauce.put("name", Config.get("SAUCE_JOB_NAME"));
        sauce.put("appiumVersion", Config.get("SAUCE_APPIUM_VERSION"));
        
        options.setCapability("sauce:options", sauce);
        return options;
    }
}

