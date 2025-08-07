package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPages {

    private final WebDriver driver;

    private final By usernameInput = By.id("username");
    private final By passwordInput = By.id("password");
    private final By submitButton = By.id("submit");
    private final By messageLocator = By.id("error");

    public LoginPages(WebDriver driver) {
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(messageLocator));
            return driver.findElement(messageLocator).getText();
        } catch (Exception ignored) {
        }
        return "";
    }
}
