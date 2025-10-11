# 🧪 Pruebas UI con Cucumber + Selenium + JUnit 5

Este proyecto demuestra cómo automatizar pruebas de interfaz gráfica (UI) usando **Cucumber**, **Selenium WebDriver** y **JUnit 5 (JUnit Platform)** en **Java 17**.  
El escenario de ejemplo busca el término **“Selenium (software)”** en **Wikipedia** y valida que aparezca un elemento `<span>` visible cuyo texto contenga la palabra “selenium”.

---

## 🚀 Tecnologías utilizadas

- **Java 21**
- **JUnit 5** (JUnit Platform)
- **Cucumber 7.x**
- **Selenium WebDriver 4.x**
- **Maven 3.9+**

---
## Ejecución de todas las pruebas

```bash
mvn test
```

## Ejecución de un único test

```bash
mvn -Dtest=BasicBlogSmokeTest test
```

## Ejecución sólo las pruebas de Cucumber

```bash
mvn -Dtest=CucumberTest test
```

## Ejecución de un único feature

```bash
mvn -Dtest=CucumberTest -Dcucumber.features=src/test/resources/features/wiki_search.feature -Dcucumber.glue=com.cursosdedesarrollo.steps test

```



