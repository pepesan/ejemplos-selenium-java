package com.cursosdedesarrollo.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverHooks {

    private final World world;

    public DriverHooks(World world) {
        this.world = world;
    }

    @Before(order = 0)
    public void initDriver() {

        ChromeOptions options = new ChromeOptions();
        if (Boolean.getBoolean("headless")) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");

        WebDriver driver = new ChromeDriver(options);
        world.setDriver(driver);
    }

    @After(order = 100)
    public void quitDriver() {
        if (world.getDriver() != null) {
            world.getDriver().quit();
            world.setDriver(null);
        }
    }
}
