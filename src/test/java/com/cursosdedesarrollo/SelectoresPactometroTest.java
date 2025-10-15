package com.cursosdedesarrollo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

class SelectoresPactometroTest {

    WebDriver driver;

    private WebDriver newChrome() {
        ChromeOptions opts = new ChromeOptions();
        if (Boolean.getBoolean("headless")) {
            opts.addArguments("--headless=new");
        }
        return new ChromeDriver(opts);
    }

    @Test
    void ejemplosDeByEnPactometro() {
        driver = newChrome();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://pactometro.cursosdedesarrollo.com/");

        // Ejemplo con By.tagName: localizar el botón <button>
        WebElement botonReset = wait.until(
                ExpectedConditions.elementToBeClickable(By.tagName("button"))
        );
        System.out.println("Botón texto: " + botonReset.getText());  // debería mostrar “Reset”

        // Ejemplo con By.xpath (solo como comparación, aunque dijiste que evitaras):
        // localizar el h1 que contiene “Acciones”.
        WebElement h1Acciones = driver.findElement(By.xpath("//h1[text()='Acciones']"));
        System.out.println("Título h1: " + h1Acciones.getText());

        // Ejemplo con By.id: localizar la caja de texto del numero los sies
        WebElement numSies = driver.findElement(By.id("num-sies"));
        System.out.println(numSies.getText());
        // Ejemplo con By.cssSelector: localizar el <h2> bajo “A favor”
        // Aquí asumimos que el primer <h2> corresponde a “A favor”
        WebElement h2AFavor = driver.findElement(By.cssSelector("h1 + h2"));
        System.out.println("A favor texto: " + h2AFavor.getText());

        // Ejemplo con By.linkText o By.partialLinkText: si hubiera enlaces, pero en esta página no hay <a>.
        // (Por ejemplo: driver.findElement(By.linkText("Reset")) si fuera un enlace)

        // Ejemplo con By.className o By.id: esta página no tiene id ni clases específicas,
        // así que no se puede usar aquí, pero te muestro cómo sería:
        // WebElement algo = driver.findElement(By.id("mi-id"));
        // WebElement otro = driver.findElement(By.className("mi-clase"));

        // Ejemplo de validación: tras hacer clic en Reset, los valores vuelven a 0
        botonReset.click();

        // Espera que los <h2> sigan siendo “0”
        WebElement h2AFavorDespues = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1 + h2")));
        String valorAFavor = h2AFavorDespues.getText();
        assert valorAFavor.equals("0") : "Esperaba que A favor fuera 0 tras Reset, fue: " + valorAFavor;

        System.out.println("Valor tras reset (A favor): " + valorAFavor);
    }

    @AfterEach
    void cerrar() {
        if (driver != null) driver.quit();
    }
}

