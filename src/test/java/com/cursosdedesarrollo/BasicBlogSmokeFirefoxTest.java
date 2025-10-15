package com.cursosdedesarrollo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

class BasicBlogSmokeFirefoxTest {

    private WebDriver driver;

    private WebDriver newFirefox() {
        FirefoxOptions options = new FirefoxOptions();
        if (Boolean.getBoolean("headless")) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        // Binario real del snap (el symlink "current" existe) en el caso de usar Firefox en Ubuntu
        String snapBinary = "/snap/firefox/current/usr/lib/firefox/firefox";
        if (new java.io.File(snapBinary).canExecute()) {
            options.setBinary(snapBinary);
        }
        return new FirefoxDriver(options);
    }

    @Test
    void home_titulo_contiene_cursos() {
        driver = newFirefox();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get("https://blog.cursosdedesarrollo.com/");

        // Espera a que el documento termine de cargar
        ExpectedCondition<Boolean> domCargado = wd ->
                ((JavascriptExecutor) wd)
                        .executeScript("return document.readyState")
                        .toString().equals("complete");
        wait.until(domCargado);

        // Aserción mínima y estable sobre el título
        String title = driver.getTitle();
        Assertions.assertNotNull(title, "El título es nulo");
        Assertions.assertFalse(title.isBlank(), "El título está vacío");
        Assertions.assertTrue(
                title.toLowerCase().contains("cursos"),
                "El título no contiene 'cursos'. Título actual: " + title
        );
    }

    @AfterEach
    void cerrar() {
        if (driver != null) driver.quit();
    }
}

