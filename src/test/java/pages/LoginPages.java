package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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

    }
}
