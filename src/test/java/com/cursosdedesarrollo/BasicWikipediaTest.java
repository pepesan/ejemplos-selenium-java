package com.cursosdedesarrollo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

class BasicWikipediaTest {

    WebDriver driver;

    private WebDriver newChrome() {
        ChromeOptions options = new ChromeOptions();
        // Activa headless en CI con: -Dheadless=true
        if (Boolean.getBoolean("headless")) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        return new ChromeDriver(options);
    }

    @Test
    void buscarEnWikipedia() {
        driver = newChrome();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get("https://www.wikipedia.org/");

        // Buscar "Selenium (software)" en la portada
        WebElement searchBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("searchInput"))
        );
        searchBox.sendKeys("Selenium (software)");
        searchBox.submit();

        // Espera a que exista un <span> visible cuyo texto, en minúsculas, contenga "selenium"

        WebElement span = wait.until(d ->
                Objects.requireNonNull(d.findElements(By.tagName("span")).stream()
                        .filter(WebElement::isDisplayed)
                        .filter(e -> {
                            String t = e.getText();
                            return t != null && t.toLowerCase().contains("selenium");
                        })
                        .findFirst()
                        .orElse(null))
        );

        // Aserción: el <span> encontrado contiene "selenium"
        Assertions.assertTrue(
                span.getText().toLowerCase().contains("selenium"),
                "No se encontró un <span> visible con texto que contenga 'selenium'."
        );
    }

    @AfterEach
    void cerrar() {
        if (driver != null) driver.quit();
    }
}
