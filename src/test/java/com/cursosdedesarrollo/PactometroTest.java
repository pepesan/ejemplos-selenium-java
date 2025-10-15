package com.cursosdedesarrollo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PactometroTest {
    private WebDriver driver;
    private Map<String, Object> vars;
    private JavascriptExecutor js;

    @BeforeEach
    void setUp() {
        // Given
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<>();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void pactometro() {
        // When
        driver.get("https://pactometro.cursosdedesarrollo.com/");


        // Then
        // Busco el elemento por id
        WebElement elemento = driver.findElement(By.id("num-sies"));
        // Pillo lo que hay dentro del elemento
        String textoDentroDelElemento = elemento.getText();
        // Verificar textos iniciales
        assertThat(textoDentroDelElemento).isEqualTo("0");
        assertThat(driver.findElement(By.id("num-abst")).getText()).isEqualTo("0");
        assertThat(driver.findElement(By.id("num-noes")).getText()).isEqualTo("0");

        // Verificar presencia del elemento PP
        List<WebElement> elements = driver.findElements(By.cssSelector("#part-PP"));
        assertThat(elements).isNotEmpty();
        assertThat(elements.size()).isEqualTo(1);

        // When
        // Interacciones
        // Pulsar en PP
        driver.findElement(By.cssSelector("#part-PP")).click();
        // Pulsar en Noes
        driver.findElement(By.id("noes")).click();

        // Then
        // Verificar resultado final
        assertThat(driver.findElement(By.id("num-noes")).getText()).isEqualTo("137");
    }
}
