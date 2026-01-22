Feature: Drawing Feature
  Scenario: Acceder a la screen "Drawing" y dibujar una cara sonriente
    Given El usuario esta en Home
    When Abre el Menu y entra en Drawing
    And Llega a Drawing
    Then Dibuja una cara sonriente