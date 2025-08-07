Feature: Login de práctica con DataTables y screenshots

  Scenario Outline: Login con múltiples credenciales
    Given el usuario abre la página de login
    When el usuario ingresa las siguientes credenciales:
      | usuario   | contraseña   |
      | <usuario> | <contraseña> |
    Then debería ver el mensaje "<mensaje>"
    And la URL debería contener "<urlEsperada>"
    And el botón "<boton>" debería estar "<estado>"

    Examples:
      | usuario       | contraseña        | mensaje                   | estado   | boton   | urlEsperada            |
      | student       | Password123       | Logged In Successfully    | válido   | Log out | logged-in-successfully |
      | incorrectUser | Password123       | Your username is invalid! | inválido |         |                        |
      | student       | incorrectPassword | Your password is invalid! | inválido |         |                        |
