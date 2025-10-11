package com.cursosdedesarrollo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class GridChromeExample {
    public static void main(String[] args) {
        WebDriver driver = null;

        try {
            // 1️⃣ Configurar las opciones del navegador Chrome
            ChromeOptions options = new ChromeOptions();

            // (opcional) ejecutar sin interfaz gráfica:
            // options.addArguments("--headless=new");

            // 2️⃣ Crear conexión con el Selenium Grid remoto
            // Asegúrate de que el Grid esté ejecutándose en el puerto 4444
            driver = new RemoteWebDriver(
                    new URL("http://localhost:4444/wd/hub"),
                    options
            );

            // 3️⃣ Abrir la página del blog
            driver.get("https://blog.cursosdedesarrollo.com");

            // 4️⃣ Mostrar el título de la página en consola
            System.out.println("Título de la página: " + driver.getTitle());

        } catch (MalformedURLException e) {
            System.err.println("URL del Grid no válida: " + e.getMessage());
        } finally {
            // 5️⃣ Cerrar el navegador al finalizar
            if (driver != null) {
                driver.quit();
            }
        }
    }
}

