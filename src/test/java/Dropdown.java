import org.openqa.selenium.By;
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

public class Dropdown {
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
    public void dropdown() {
        driver.get("https://the-internet.herokuapp.com/dropdown");
        WebElement dropdownElement = driver.findElement(By.id("dropdown"));
        Select dropdown = new Select(dropdownElement);
        List<WebElement> options = dropdown.getOptions();
        softAssert.assertEquals(options.size(), 3);
        //проверить наличие каждой опции
        for (WebElement option : options) {
            softAssert.assertTrue(option.isDisplayed());
        }

        WebElement option1 = options.get(1);
        if (!option1.isSelected()) {
            dropdown.selectByValue("1");

            //проверить, что опция 1 выбрана, если не выбрана -кликаем.
            softAssert.assertTrue(option1.isSelected());
            if (!option1.isSelected()) {
                option1.click();
            }
            WebElement option2 = options.get(2);
            if (!option2.isSelected()) {
                dropdown.selectByValue("2");
            }

            //проверить, что опция 2 выбрана, если не выбрана -кликаем.
            softAssert.assertTrue(option2.isSelected());
            if (!option2.isSelected()) {
                option2.click();
            }
        }
        softAssert.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }
}


