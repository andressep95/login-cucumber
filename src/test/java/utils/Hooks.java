package utils;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Hooks {

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
}
