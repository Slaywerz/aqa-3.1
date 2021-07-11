import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.By.cssSelector;

public class AppOrderTest {
    private WebDriver driver;

    @BeforeAll
    public static void setupWebDriverManager() {
        WebDriverManager.chromedriver().setup();

    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldDoneOrderWithSpace() {
        driver.get("http://localhost:9999/");

        driver.findElement(cssSelector("[class ='input__control'][type ='text']")).sendKeys("Андрей Семенов");
        driver.findElement(cssSelector("[class ='input__control'][type ='tel']")).sendKeys("+12345678901");
        driver.findElement(cssSelector(".checkbox__box")).click();
        driver.findElement(cssSelector(".button__text")).click();
        String text = driver.findElement(cssSelector(".paragraph")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void shouldDoneOrderWithDash() {
        driver.get("http://localhost:9999/");

        driver.findElement(cssSelector("[class ='input__control'][type ='text']")).sendKeys("Андрей-Сергей");
        driver.findElement(cssSelector("[class ='input__control'][type ='tel']")).sendKeys("+12345678901");
        driver.findElement(cssSelector(".checkbox__box")).click();
        driver.findElement(cssSelector(".button__text")).click();
        String text = driver.findElement(cssSelector(".paragraph")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void shouldDoneOrderWithOnlyName() {
        driver.get("http://localhost:9999/");

        driver.findElement(cssSelector("[class ='input__control'][type ='text']")).sendKeys("Андрей");
        driver.findElement(cssSelector("[class ='input__control'][type ='tel']")).sendKeys("+12345678901");
        driver.findElement(cssSelector(".checkbox__box")).click();
        driver.findElement(cssSelector(".button__text")).click();
        String text = driver.findElement(cssSelector(".paragraph")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }
}
