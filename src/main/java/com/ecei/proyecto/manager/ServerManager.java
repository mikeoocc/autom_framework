package com.ecei.proyecto.manager;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.io.File;
import java.time.Duration;

public class ServerManager {

    private static ThreadLocal<AppiumDriverLocalService> SERVICE = new ThreadLocal<>();

    public static void start() {
        if (SERVICE.get() != null && SERVICE.get().isRunning()) {
            return;
        }
        String ip = Config.get("APPIUM_IP");
        int port = Integer.parseInt(Config.get("APPIUM_PORT"));
        String nodePath = Config.get("NODE_PATH");
        String appiumPath = Config.get("APPIUM_PATH");

        AppiumServiceBuilder builder = new AppiumServiceBuilder()
            .withIPAddress(ip)
            .usingPort(port)
            .usingDriverExecutable(new File(nodePath))
            .withAppiumJS(new File(appiumPath))
            .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
            .withTimeout(Duration.ofSeconds(60));

        AppiumDriverLocalService service = AppiumDriverLocalService.buildService(builder);
        service.start();
        SERVICE.set(service);
    }

    public static void stop() {
        AppiumDriverLocalService service = SERVICE.get();
        if (service != null) {
            service.stop();
            SERVICE.remove();
        }
    }

    public static String getUrl() {
        AppiumDriverLocalService service = SERVICE.get();
        return (service != null && service.isRunning()) ? service.getUrl().toString() : null;
    }
}