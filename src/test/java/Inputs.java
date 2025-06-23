import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
public class Inputs {
    //в "общем" классе объявляем драйвер и асёрт

    WebDriver driver;
    SoftAssert softAssert;

    @BeforeMethod
    public void input() {
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
    public void inputs() {
        driver.get("https://the-internet.herokuapp.com/inputs");
        WebElement inputField = driver.findElement(By.tagName("input"));
        inputField.sendKeys("3");
        inputField.sendKeys(Keys.ARROW_UP);
        softAssert.assertEquals(inputField.getAttribute("value"),"4");
        inputField.sendKeys(Keys.ARROW_DOWN);
        softAssert.assertEquals(inputField.getAttribute("value"),"3");
        inputField.clear();
        inputField.sendKeys("a");
        softAssert.assertEquals(inputField.getAttribute("value"),"");

        softAssert.assertAll();
    }
   @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }
}
