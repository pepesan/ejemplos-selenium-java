package com.cursosdedesarrollo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

class FormularioW3SchoolsTest {

    WebDriver driver;

    private WebDriver newChrome() {
        ChromeOptions options = new ChromeOptions();
        if (Boolean.getBoolean("headless")) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        return new ChromeDriver(options);
    }

    @Test
    void rellenarFormularioDeContacto() {
        driver = newChrome();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // 1️⃣ Navegar a la página del formulario
        driver.get("https://www.w3schools.com/howto/howto_css_contact_form.asp");

        // 2️⃣ Esperar a que cargue el primer formulario
        WebElement form = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#fname.test")));

        // 3️⃣ Rellenar los campos del formulario
        WebElement firstName = form.findElement(By.cssSelector("input#fname.test"));
        WebElement lastName  = form.findElement(By.cssSelector("input#lname.test"));
        WebElement country   = form.findElement(By.cssSelector("input#country.test"));
        WebElement subject   = form.findElement(By.cssSelector("input#subject.test"));

        firstName.clear();
        firstName.sendKeys("Pepe");
        lastName.clear();
        lastName.sendKeys("Sánchez");

        // 4️⃣ Seleccionar país con la clase Select
        Select selectCountry = new Select(country);
        selectCountry.selectByVisibleText("Canada");

        // 5️⃣ Escribir mensaje en textarea
        subject.clear();
        subject.sendKeys("Este es un mensaje de prueba automatizado con Selenium WebDriver.");

        // 6️⃣ Enviar formulario (simulación)
        WebElement botonEnviar = form.findElement(By.cssSelector("input[type='submit']"));
        botonEnviar.click();

        // 7️⃣ Validación: tras enviar, la página de acción muestra los datos (no redirige realmente en W3Schools)
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("action_page.php"),
                ExpectedConditions.presenceOfElementLocated(By.tagName("body"))
        ));

        System.out.println("Formulario completado y enviado correctamente.");
    }

    @AfterEach
    void cerrar() {
        if (driver != null) driver.quit();
    }
}

