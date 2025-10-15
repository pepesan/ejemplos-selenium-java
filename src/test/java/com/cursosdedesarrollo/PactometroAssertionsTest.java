package com.cursosdedesarrollo;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class PactometroAssertionsTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1366, 900));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    void pactometroPPNoes() {
        driver.get("https://pactometro.cursosdedesarrollo.com/");

        // 1) Asegura que el documento ha cargado
        wait.until(d -> ((JavascriptExecutor) d)
                .executeScript("return document.readyState").equals("complete"));

        // 2) Si hubiese iframes, intenta cambiar a aquel que contenga los partidos
        for (WebElement frame : driver.findElements(By.tagName("iframe"))) {
            driver.switchTo().frame(frame);
            if (!driver.findElements(By.cssSelector("[id^='part-']")).isEmpty()) {
                break;
            }
            driver.switchTo().defaultContent();
        }

        // 3) Espera a que exista al menos un partido
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[id^='part-']")));

        // --- Aserciones iniciales de la página ---
        String titulo = driver.getTitle();
        assertThat(titulo).isNotBlank().containsIgnoringCase("pactómetro");

        WebElement numSies = driver.findElement(By.id("num-sies"));
        WebElement numAbst = driver.findElement(By.id("num-abst"));
        WebElement numNoes = driver.findElement(By.id("num-noes"));

        assertThat(numSies.getText()).isEqualTo("0");
        assertThat(numAbst.getText()).isEqualTo("0");
        assertThat(numNoes.getText()).isEqualTo("0");

        // Todos los contadores son numéricos y empiezan en 0
        assertThat(List.of(numSies, numAbst, numNoes))
                .allSatisfy(e -> {
                    assertThat(e.getText()).matches("\\d+");
                    assertThat(Integer.parseInt(e.getText())).isZero();
                });

        // 4) Localiza PP de forma robusta (id conocido o por texto)
        WebElement partPP;
        if (!driver.findElements(By.id("part-PP")).isEmpty()) {
            partPP = driver.findElement(By.id("part-PP"));
        } else {
            partPP = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[starts-with(@id,'part-')][normalize-space()='PP']")));
        }

        // Aserciones sobre el elemento de PP
        assertThat(partPP).as("Debe existir el elemento del partido PP").isNotNull();
        assertThat(partPP.isDisplayed()).isTrue();
        assertThat(partPP.getTagName()).isIn("div", "span", "button");
        assertThat(partPP.getAttribute("id")).startsWith("part-").contains("PP");
        assertThat(partPP.getText()).isNotBlank().contains("137");

        // 5) Interacción: click en PP
        wait.until(ExpectedConditions.elementToBeClickable(partPP)).click();

        // 6) Localiza el "botón" NOES de forma tolerante a cambios de etiqueta
        WebElement btnNoes = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#noes, [data-testid='noes'], [role='button']#noes")
        ));

        // Aserciones sensatas sobre el "botón" (puede ser <div>, <button> o <a>)
        assertThat(btnNoes.isDisplayed()).isTrue();
        assertThat(btnNoes.isEnabled()).isTrue();
        assertThat(btnNoes.getTagName()).isIn("button", "div", "a");
        // Opcionales si tu app usa atributos de accesibilidad
        String role = btnNoes.getAttribute("role");
        assertThat(role).isIn(null, "", "button");

        // 7) Click en NOES
        btnNoes.click();

        // 8) Verificación: el contador de "noes" cambia de 0 a un número
        WebElement numNoesFinal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("num-noes")));
        wait.until(d -> !numNoesFinal.getText().trim().equals("0"));

        String valorNoes = numNoesFinal.getText().trim();
        assertThat(valorNoes).matches("\\d+");
        // Negocio esperado: para PP el total de "noes" debe ser 137 (ajusta si tu lógica cambia)
        assertThat(valorNoes).isEqualTo("137");

        int valorInt = Integer.parseInt(valorNoes);
        assertThat(valorInt).isGreaterThan(0).isLessThan(400);

        // 9) Aserciones adicionales de sanidad de página
        String urlActual = driver.getCurrentUrl();
        assertThat(urlActual).startsWith("https://").contains("pactometro");

        // No deberían aparecer alertas/errores visibles genéricos
        List<WebElement> alerts = driver.findElements(By.cssSelector(".alert, .error"));
        assertThat(alerts).isEmpty();
    }
}
