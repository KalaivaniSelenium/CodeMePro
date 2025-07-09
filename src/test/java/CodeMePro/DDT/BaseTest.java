package CodeMePro.DDT;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
    protected WebDriver driver;
    

    @BeforeMethod
    public void setup() {
    	WebDriverManager.chromedriver().setup();
//    	EdgeOptions options = new EdgeOptions();
//    	options.addArguments("headless");
//    	options.addArguments("disable-gpu");
//    	options.addArguments("window-size=1920,1080");
//    	driver = new EdgeDriver(options);
    	driver = new ChromeDriver();
    	driver.manage().window().maximize();

    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void dropDownSelection(WebElement dropdownElement, String text) {
    	Select select = new Select(dropdownElement);
        select.selectByVisibleText(text);
    }
    public static void scrollToElement(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }
}
