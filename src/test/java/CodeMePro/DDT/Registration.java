package CodeMePro.DDT;

import Utility.ExcelUtil;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Registration extends BaseTest {
	
    WebDriverWait wait;
    @DataProvider(name = "registrationData")
    public Object[][] getData() {
        String filePath = "D:\\Kalai_TestAutomation\\DDT\\src\\test\\resources\\TestData\\RegistrationData.xlsx";
        return ExcelUtil.getExcelData(filePath, "Sheet1");
    }

    @Test(dataProvider = "registrationData")
    public void registerUser(String firstName, String lastName, String email,
                             String country, String phoneNo, String service, String message) throws InterruptedException {
        
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement regForm = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[text()='Let us know how we can help you! ']")));
        scrollToElement(driver, regForm);
        WebElement firstNameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("ff_6_names_first_name_")));
        firstNameField.sendKeys(firstName);
        driver.findElement(By.id("ff_6_names_last_name_")).sendKeys(lastName);
        driver.findElement(By.name("email")).sendKeys(email);
        WebElement countryDropdown = driver.findElement(By.name("country-list")); 
        dropDownSelection(countryDropdown, country);
        driver.findElement(By.name("numeric-field")).sendKeys(phoneNo);
        WebElement serviveDropdown = driver.findElement(By.name("dropdown"));
        dropDownSelection(serviveDropdown, service);
        driver.findElement(By.name("message")).sendKeys(message);
        driver.navigate().refresh();
        Thread.sleep(3000);
        
       
    }
}
