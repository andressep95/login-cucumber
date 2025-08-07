# 1 Creacion del proyecto

```bash
mvn archetype:generate \
  -DgroupId=cl.playground.bootcamp \
  -DartifactId=login \
  -DarchetypeArtifactId=maven-archetype-quickstart \
  -DinteractiveMode=false
```

Generamos un arquetipo de maven (arquitectura) para poder comenzar a trabajar
El unico motivo por el que usamos Quickstart es que me trae el pom.xml


# 2 Configuracion del POM

```xml

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>cl.playground.bootcamp</groupId>
    <artifactId>login</artifactId>
    <packaging>jar</packaging>
    <version>1.0-SNAPSHOT</version>
    <name>login</name>
    <url>http://maven.apache.org</url>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
    </properties>

    <dependencies>
        <!-- Cucumber core -->
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-java</artifactId>
            <version>7.15.0</version>
            <scope>test</scope>
        </dependency>
        <!-- Cucumber JUnit 4 -->
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-junit</artifactId>
            <version>7.15.0</version>
            <scope>test</scope>
        </dependency>
        <!-- Selenium -->
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-java</artifactId>
            <version>4.20.0</version>
        </dependency>
        <!-- JUnit 4 -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.13.2</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!-- Maven Surefire Plugin -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.2.5</version>
                <configuration>
                    <includes>
                        <include>**/TestRunner.java</include>
                    </includes>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

Se asignan las dependencias necesarias para el proyecto, como Cucumber, Selenium y JUnit.
Ademas de configurar el plugin de Maven Surefire para ejecutar las pruebas.


# 3 Presentacion del escenario en lenguje Gherkin

```gherkin
Feature: Login de práctica con DataTables y screenshots

  Scenario Outline: Login con múltiples credenciales
    Given el usuario abre la página de login
    When el usuario ingresa las siguientes credenciales:
      | usuario   | contraseña   |
      | <usuario> | <contraseña> |
    Then debería ver el mensaje "<mensaje>"
    And la URL debería contener "<urlEsperada>"
    And el botón "<boton>" deberia estar "<estado>"

    Examples:
      | usuario       | contraseña        | mensaje                   | estado   | boton   | urlEsperada            |
      | student       | Password123       | Logged In Successfully    | válido   | Log out | logged-in-successfully |
      | incorrectUser | Password123       | Your username is invalid! | inválido |         |                        |
      | student       | incorrectPassword | Your password is invalid! | inválido |         |                        |
```


# 4 Configuramos los Hooks (codigo reutilizable)

```java
private static WebDriver driver;

public static WebDriver getDriver() {
    return driver;
}

@Before(order = 0)
public void setup() {
    // Si el driver es nulo lo inicializamos.
    if (driver == null) {
        driver = new ChromeDriver(); // Se inicializa el driver de Chrome
        driver.manage().window().fullscreen(); // Cambiado a fullscreen
    }
}

@After(order = 0)
public void teardown() {
    if (driver != null) {
        driver.quit();
        driver = null;
    }
}

@AfterStep
public void afterStep(Scenario scenario) {
    if (driver != null) {
        final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", "Step Screenshot");
    }
}
```