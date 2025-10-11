Feature: Búsqueda en Wikipedia
  Como usuario
  Quiero buscar un término en Wikipedia
  Para verificar que aparece texto relacionado en la página de resultados

  @ui @wikipedia
  Scenario: Buscar "Selenium (software)" y comprobar que aparece "selenium" en un span visible
    Given que abro la portada de Wikipedia
    When busco "Selenium (software)"
    Then debería ver un span visible que contenga "selenium"
