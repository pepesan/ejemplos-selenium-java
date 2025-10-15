package com.cursosdedesarrollo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.function.Function;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class WaitsWikipediaTest {

    WebDriver driver;

    private WebDriver newChrome() {
        ChromeOptions options = new ChromeOptions();
        if (Boolean.getBoolean("headless")) options.addArguments("--headless=new");
        return new ChromeDriver(options);
    }

    @Test
    void ejemploDeWaits() {
        driver = newChrome();
        driver.get("https://www.wikipedia.org/");

        // Implicit Wait → se aplica a TODAS las búsquedas findElement()
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        WebElement searchBox = driver.findElement(By.id("searchInput"));
        searchBox.sendKeys("Selenium (software)");
        searchBox.submit();

        // Explicit Wait → espera hasta que se cumpla una condición específica
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstHeading")));

        System.out.println("Título encontrado: " + heading.getText());

        assertThat(heading.getText()).isEqualTo("Resultados de la búsqueda");

        // Fluent Wait → igual que Explicit, pero con control de polling y excepciones ignoradas
        Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofMillis(500)) // revisa cada medio segundo
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        WebElement spanConSelenium = fluentWait.until(new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver d) {
                return d.findElements(By.tagName("span")).stream()
                        .filter(WebElement::isDisplayed)
                        .filter(e -> e.getText().toLowerCase().contains("selenium"))
                        .findFirst()
                        .orElse(null);
            }
        });


        System.out.println("Span encontrado con texto: " + spanConSelenium.getText());
    }

    @AfterEach
    void cerrar() {
        if (driver != null) driver.quit();
    }
}

