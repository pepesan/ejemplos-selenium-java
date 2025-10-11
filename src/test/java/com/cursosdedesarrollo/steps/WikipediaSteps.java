package com.cursosdedesarrollo.steps;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

public class WikipediaSteps {

    private final World world;
    private WebDriverWait wait;
    private WebDriver driver;
    public WikipediaSteps(World world) {
        this.world = world;
    }

    @Dado("que abro la portada de Wikipedia")
    public void que_abro_la_portada_de_wikipedia() {
        driver = world.getDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get("https://www.wikipedia.org/");
        // Asegura que el buscador esté visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchInput")));
    }

    @Cuando("busco {string}")
    public void busco(String termino) {
        driver = world.getDriver();
        WebElement searchBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("searchInput"))
        );
        searchBox.clear();
        searchBox.sendKeys(termino);
        searchBox.submit();
    }

    @Entonces("debería ver un span visible que contenga {string}")
    public void deberia_ver_un_span_visible_que_contenga(String texto) {
        WebDriver driver = world.getDriver();

        // 1) Espera a que la navegación ocurra y haya contenido “estable”
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.or(
                        ExpectedConditions.presenceOfElementLocated(By.id("firstHeading")),
                        ExpectedConditions.presenceOfElementLocated(By.id("mw-content-text"))
                ));

        // 2) Si existe el firstHeading, úsalo directamente (mucho más estable)
        try {
            WebElement heading = driver.findElement(By.id("firstHeading"));
            String h = heading.getText();
            if (h != null && h.toLowerCase().contains(texto.toLowerCase())) {
                return; // OK
            }
        } catch (NoSuchElementException ignored) {
            // seguimos con la alternativa genérica
        }

        // 3) Alternativa genérica: buscar cualquier nodo con el texto (sin “guardar” referencias viejas)
        boolean found = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> d.findElements(By.xpath("//*[contains(translate(normalize-space(text()), " +
                                "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), " +
                                "'" + texto.toLowerCase() + "')]"))
                        .stream()
                        .anyMatch(WebElement::isDisplayed));

        Assertions.assertTrue(found,
                "No se encontró un elemento visible con texto que contenga '" + texto + "'.");
    }

}
