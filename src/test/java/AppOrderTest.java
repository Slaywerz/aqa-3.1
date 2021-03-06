import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.By.cssSelector;

public class AppOrderTest {
    private WebDriver driver;

    @BeforeAll
    public static void setUpWebDriverManager() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldDoneOrder() {
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Андрей Семенов");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+12345678901");
        driver.findElement(cssSelector(".checkbox__box")).click();
        driver.findElement(cssSelector(".button__text")).click();
        String text = driver.findElement(cssSelector("[data-test-id='order-success']")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void shouldDoneOrderWithDoubleSurname() {
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Семенов-Васильев Андрей");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+12345678901");
        driver.findElement(cssSelector(".checkbox__box")).click();
        driver.findElement(cssSelector(".button__text")).click();
        String text = driver.findElement(cssSelector("[data-test-id='order-success']")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void shouldDoneOrderWithDoubleFullName() {
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Семенов-Васильев Андрей-Сергей");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+12345678901");
        driver.findElement(cssSelector(".checkbox__box")).click();
        driver.findElement(cssSelector(".button__text")).click();
        String text = driver.findElement(cssSelector("[data-test-id='order-success']")).getText();

        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    //Задача №2 - Проверка валидации

    @Test
    void shouldShowInvalidName() {
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Andrey Semyonov");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+12345678901");
        driver.findElement(cssSelector(".checkbox__box")).click();
        driver.findElement(cssSelector(".button__text")).click();
        String text = driver.findElement(cssSelector(".input_invalid[data-test-id=name] .input__sub")).getText();

        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }


    @Test
    void shouldShowInvalidPhoneNumber() {
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Андрей Семенов");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("12345678901");
        driver.findElement(cssSelector(".checkbox__box")).click();
        driver.findElement(cssSelector(".button__text")).click();
        String text = driver.findElement(cssSelector(".input_invalid[data-test-id=phone] .input__sub")).getText();

        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void shouldShowInvalidCheckbox() {
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Андрей Семенов");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+12345678901");
        driver.findElement(cssSelector(".button__text")).click();
        String text = driver.findElement(cssSelector(".input_invalid .checkbox__text")).getCssValue("color");

        assertEquals("rgba(255, 92, 92, 1)", text);
    }

    @Test
    void shouldDoNotOrderWithoutFullName(){
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+12345678901");
        driver.findElement(cssSelector(".checkbox__box")).click();
        driver.findElement(cssSelector(".button__text")).click();
        String text = driver.findElement(cssSelector(".input_invalid[data-test-id='name'] [class='input__sub']")).getText();

        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldDoNotOrderWithoutPhone(){
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Андрей Семенов");
        driver.findElement(cssSelector(".checkbox__box")).click();
        driver.findElement(cssSelector(".button__text")).click();
        String text = driver.findElement(cssSelector(".input_invalid[data-test-id='phone'] [class='input__sub']")).getText();

        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldShowErrorWithoutSurname() {
        driver.findElement(cssSelector("[data-test-id=name] input")).sendKeys("Андрей");
        driver.findElement(cssSelector("[data-test-id=phone] input")).sendKeys("+12345678901");
        driver.findElement(cssSelector(".checkbox__box")).click();
        driver.findElement(cssSelector(".button__text")).click();
        String text = driver.findElement(cssSelector(".input_invalid[data-test-id=name] .input__sub")).getText();

        assertEquals("Пожалуйста, укажите свою фамилию", text.trim());
    }
}
