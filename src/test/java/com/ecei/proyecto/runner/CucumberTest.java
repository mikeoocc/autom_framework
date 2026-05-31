package com.ecei.proyecto.runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.ecei.proyecto.appiumgluecode"},
        plugin = {"pretty", "html:target/cucumber-report.html", "com.ecei.proyecto.reporting.DashboardReporter"},
        monochrome = true
)
public class CucumberTest {
}

