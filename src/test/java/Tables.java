import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.HashMap;

public class Tables {
    //в "общем" классе объявляем драйвер и асёрт

    WebDriver driver;
    SoftAssert softAssert;

    @BeforeMethod
    public void setup() {
        // инициализируем асёрт в бифор методе ассёрт
        softAssert = new SoftAssert();

        //убираем окна в браузере
        ChromeOptions options = new ChromeOptions();
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("credentials_enable_service", false);
        chromePrefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", chromePrefs);
        options.addArguments("--incognito");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-infobars");

        //инициализируем драйвер и прокинуть сюда опции в скобки
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    @Test
    public void checkTable() {
        driver.get("https://the-internet.herokuapp.com/tables");
        WebElement table1 = driver.findElement(By.id("table1"));
        WebElement slot1 = driver.findElement(By.xpath("//*[@id=\"table1\"]/tbody/tr[1]/td[1]"));
        softAssert.assertEquals(slot1.getAttribute("value"), "Smith");
        WebElement slot2 = driver.findElement(By.xpath("//*[@id=\"table1\"]/tbody/tr[1]/td[2]"));
        softAssert.assertEquals(slot2.getAttribute("value"), "John");
        WebElement slot3 = driver.findElement(By.xpath("//*[@id=\"table1\"]/tbody/tr[1]/td[3]"));
        softAssert.assertEquals(slot3.getAttribute("value"), "jsmith@gmail.com");
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }
}
