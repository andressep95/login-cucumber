package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;

    private final By usernameInput = By.id("username");
    private final By passwordInput = By.id("password");
    private final By submitButton = By.id("submit");
    private final By messageLocator = By.id("error"); // para mensajes de error
    private final By successLocator = By.tagName("h1"); // para login exitoso

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openLoginPage() {
        driver.get("https://practicetestautomation.com/practice-test-login/");
    }

    public void login(String username, String password) {
        driver.findElement(usernameInput).sendKeys(username);
        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(submitButton).click();
    }

    public String getMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(successLocator));
            return driver.findElement(successLocator).getText();
        } catch (Exception ignored) {
        }

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(messageLocator));
            return driver.findElement(messageLocator).getText();
        } catch (Exception ignored) {
        }

        return "";
    }
}
