import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.HashMap;

public class AddRemoveElement {
    //в "общем" классе объявляем драйвер и асёрт

    WebDriver driver;
    SoftAssert softAssert;

    @BeforeMethod
    public void setup(){
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
    public void addRemoveElement(){
    driver.get("https://the-internet.herokuapp.com/add_remove_elements/");
    driver.findElement(By.xpath("//button[text()='Add Element']")).click();
    String element1 =driver.findElement(By.xpath("//*[@id=\"elements\"]/button")).getText();
    softAssert.assertEquals(element1, "Delete");
    driver.findElement(By.xpath("//button[text()='Add Element']")).click();
    String element2 =driver.findElement(By.xpath("//button[text()='Delete']")).getText();
    softAssert.assertEquals(element2, "Delete");
    driver.findElement(By.xpath("//button[text()='Delete']")).click();
    String oneElement = driver.findElement(By.xpath("//button[text()='Delete']")).getText();
    softAssert.assertEquals(oneElement,"Delete");

    softAssert.assertAll();
    }
    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        driver.quit();
    }

}
