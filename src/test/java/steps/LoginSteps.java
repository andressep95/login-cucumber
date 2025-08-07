package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import io.cucumber.java.Scenario;
import org.junit.Assert;
import org.openqa.selenium.*;
import pages.LoginPage;
import utils.Hooks;

import java.util.List;
import java.util.Map;

public class LoginSteps {

    WebDriver driver = Hooks.getDriver();
    LoginPage loginPage = new LoginPage(driver);
    private Scenario scenario;

    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
    }

    @Given("el usuario abre la página de login")
    public void abrirPagina() {
        loginPage.openLoginPage();
    }

    @When("el usuario ingresa las siguientes credenciales:")
    public void ingresarCredencialesConTabla(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            String usuario = row.get("usuario");
            String password = row.get("contraseña");
            loginPage.login(usuario, password);
            takeScreenshot("Login_" + usuario);
        }
    }

    @Then("debería ver el mensaje {string}")
    public void verificarMensaje(String mensajeEsperado) {
        String mensajeReal = loginPage.getMessage();
        takeScreenshot("VerificarMensaje");
        Assert.assertEquals(mensajeEsperado, mensajeReal);
    }

    @Then("la URL debería contener {string}")
    public void verificarUrlContiene(String urlParcial) {
        if (!urlParcial.isEmpty()) {
            String urlActual = driver.getCurrentUrl();
            Assert.assertTrue("La URL no contiene el texto esperado: " + urlParcial,
                urlActual.contains(urlParcial));
        }
    }


    @Then("el botón {string} debería estar {string}")
    public void verificarEstadoBoton(String botonTexto, String estado) {
        if (botonTexto.trim().isEmpty()) {
            // Nada que verificar
            return;
        }

        boolean debeEstarVisible = estado.equalsIgnoreCase("válido") || estado.equalsIgnoreCase("visible");

        List<WebElement> elements = driver.findElements(By.xpath("//*[normalize-space(text())='" + botonTexto + "']"));

        boolean isVisible = false;
        for (WebElement el : elements) {
            if (el.isDisplayed()) {
                isVisible = true;
                break;
            }
        }

        if (debeEstarVisible) {
            Assert.assertTrue("El botón '" + botonTexto + "' no está visible", isVisible);
        } else {
            Assert.assertFalse("El botón '" + botonTexto + "' debería estar oculto", isVisible);
        }
    }

    // Método para tomar screenshot y adjuntarlo al reporte
    public void takeScreenshot(String screenshotName) {
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", screenshotName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
