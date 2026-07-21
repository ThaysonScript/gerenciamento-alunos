package br.com.gerenciamento.aceitacao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class GerenciamentoAceitacaoTest {

    private WebDriver driver;
    private final String BASE_URL = "http://localhost:8080";

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
    }

    @Test
    public void cadastrarUsuarioAcceptanceTest() {
        driver.get(BASE_URL + "/cadastro");

        driver.findElement(By.id("email")).sendKeys("testeselenium@escola.com");
        driver.findElement(By.id("user")).sendKeys("testeselenium");
        driver.findElement(By.id("senha")).sendKeys("12345");

        WebElement btnCadastrar = driver.findElement(By.cssSelector("button[type='submit']"));
        btnCadastrar.click();

        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals(BASE_URL + "/", currentUrl);
    }

    @Test
    public void cadastrarAlunoAcceptanceTest() {
        driver.get(BASE_URL + "/inserirAlunos");

        driver.findElement(By.id("nome")).sendKeys("Marcos Selenium da Silva");
        driver.findElement(By.id("curso")).sendKeys("INFORMATICA");
        driver.findElement(By.id("turno")).sendKeys("NOTURNO");
        driver.findElement(By.id("status")).sendKeys("ATIVO");

        WebElement btnGerar = driver.findElement(By.xpath("//button[text()='Gerar']"));
        btnGerar.click();

WebElement btnSalvar = driver.findElement(By.cssSelector("button.btn-outline-success"));        btnSalvar.click();

        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals(BASE_URL + "/alunos-adicionados", currentUrl);
        
        String textoPagina = driver.findElement(By.tagName("body")).getText();
        Assertions.assertTrue(textoPagina.contains("Marcos Selenium da Silva"));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}