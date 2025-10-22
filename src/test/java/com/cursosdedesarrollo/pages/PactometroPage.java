package com.cursosdedesarrollo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PactometroPage {

    private final WebDriver driver;

    // Selectores centralizados
    private final By numSies = By.id("num-sies");
    private final By numAbst = By.id("num-abst");
    private final By numNoes = By.id("num-noes");

    private final By partPP = By.cssSelector("#part-PP");
    private final By botonNoes = By.id("noes");

    public PactometroPage(WebDriver driver) {
        this.driver = driver;
    }

    /** Navega a la URL principal del pactómetro */
    public PactometroPage open() {
        driver.get("https://pactometro.cursosdedesarrollo.com/");
        return this;
    }

    // ------ Lecturas de estado ------
    public String getNumSies() {
        return getText(numSies);
    }

    public String getNumAbstenciones() {
        return getText(numAbst);
    }

    public String getNumNoes() {
        return getText(numNoes);
    }

    // ------ Interacciones ------
    /** Pulsa el bloque del partido PP */
    public PactometroPage clickPP() {
        driver.findElement(partPP).click();
        return this;
    }

    /** Pulsa el botón de "Noes" */
    public PactometroPage clickNoes() {
        driver.findElement(botonNoes).click();
        return this;
    }

    // ------ Utilidades internas ------
    private String getText(By locator) {
        WebElement el = driver.findElement(locator);
        return el.getText();
    }
}

