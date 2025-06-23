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

public class Typos {
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
    public void checkTypo() {
        driver.get("https://the-internet.herokuapp.com/typos");
        driver.navigate().refresh();
        WebElement typo = driver.findElement(By.xpath("//*[@id=\"content\"]/div/p[2]"));
        String expectedResult = "Sometimes you'll see a typo, other times you won't.";
        String actualResult = typo.getText();
        softAssert.assertEquals(actualResult, expectedResult);
        driver.navigate().refresh();

        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }

}
