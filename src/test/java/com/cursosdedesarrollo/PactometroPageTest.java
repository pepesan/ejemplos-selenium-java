package com.cursosdedesarrollo;

import com.cursosdedesarrollo.pages.PactometroPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class PactometroPageTest {

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
    void pactometro_con_patron_page_object() {
        // Given - Page Object
        PactometroPage page = new PactometroPage(driver).open();

        // Then - Verificaciones iniciales
        assertThat(page.getNumSies()).isEqualTo("0");
        assertThat(page.getNumAbstenciones()).isEqualTo("0");
        assertThat(page.getNumNoes()).isEqualTo("0");

        // When - Interacciones a través de la página
        page
                .clickPP()   // selecciona PP
                .clickNoes(); // cambia a "Noes"

        // Then - Resultado final esperado
        assertThat(page.getNumNoes()).isEqualTo("137");
    }
}

