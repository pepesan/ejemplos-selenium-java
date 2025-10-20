package com.cursosdedesarrollo;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class GridChromeExample {
    public static void main(String[] args) {
        WebDriver driver = null;

        try {
            // Configurar las opciones del navegador Chrome
            ChromeOptions options = new ChromeOptions();

            // (opcional) ejecutar sin interfaz gráfica:
            // options.addArguments("--headless=new");

            // clave para forzar nodo Linux
            options.setCapability("platformName", "linux"); // o "LINUX"
            // Crear conexión con el Selenium Grid remoto
            // Asegúrate de que el Grid esté ejecutándose en el puerto 4444
            driver = new RemoteWebDriver(
                    new URL("http://localhost:4444/wd/hub"),
                    options
            );

            // Abrir la página del blog
            driver.get("https://blog.cursosdedesarrollo.com");

            // Mostrar el título de la página en consola
            System.out.println("Título de la página: " + driver.getTitle());
            // Aserción mínima y estable sobre el título
            String title = driver.getTitle();
            Assertions.assertNotNull(title, "El título es nulo");
            Assertions.assertFalse(title.isBlank(), "El título está vacío");
            Assertions.assertTrue(
                    title.toLowerCase().contains("cursos"),
                    "El título no contiene 'cursos'. Título actual: " + title
            );
            Thread.sleep(10000);

        } catch (MalformedURLException e) {
            System.err.println("URL del Grid no válida: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // 5️⃣ Cerrar el navegador al finalizar
            if (driver != null) {
                driver.quit();
            }
        }
    }
}

