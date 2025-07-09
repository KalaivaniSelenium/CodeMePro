package CodeMePro.DDT;

import Utility.ExcelUtil;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Registration extends BaseTest {
	private static final Logger logger = LogManager.getLogger(Registration.class);
    WebDriverWait wait;
    @DataProvider(name = "registrationData")
    public Object[][] getData() {
        String filePath = "D:\\Kalai_TestAutomation\\DDT\\src\\test\\resources\\TestData\\RegistrationData.xlsx";
        Object[][] rawData = ExcelUtil.getExcelData(filePath, "Sheet1");

        if (rawData.length == 0) {
            return new Object[0][];
        }

        int totalCols = rawData[0].length;
        int actualCols = totalCols - 2; // exclude Status & Remarks
        Object[][] finalData = new Object[rawData.length][actualCols + 1]; // +1 for rowIndex

        for (int i = 0; i < rawData.length; i++) {
            for (int j = 0; j < actualCols; j++) {
                finalData[i][j] = rawData[i][j];
            }
            finalData[i][actualCols] = i + 1; // row index for writing results
        }

        return finalData;
    }    
    
    @Test(dataProvider = "registrationData")
    public void registerUser(String firstName, String lastName, String email,
                             String country, String phoneNo, String service, String message, int rowIndex) {

        String filePath = "D:\\Kalai_TestAutomation\\DDT\\src\\test\\resources\\TestData\\RegistrationData.xlsx";

        try {
            logger.info("==== Starting Registration Test for: " + firstName + " " + lastName + " ====");

            wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            WebElement regForm = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[text()='Let us know how we can help you! ']")));
            scrollToElement(driver, regForm);
            Thread.sleep(1000);

            Assert.assertEquals(regForm.getText().trim(), "Let us know how we can help you!");
            logger.info("Registration form is displayed");

            WebElement firstNameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("ff_6_names_first_name_")));
            firstNameField.sendKeys(firstName);
            logger.debug("Entered First Name: " + firstName);

            driver.findElement(By.id("ff_6_names_last_name_")).sendKeys(lastName);
            logger.debug("Entered Last Name: " + lastName);

            driver.findElement(By.name("email")).sendKeys(email);
            logger.debug("Entered Email: " + email);

            WebElement countryDropdown = driver.findElement(By.name("country-list")); 
            dropDownSelection(countryDropdown, country);
            logger.debug("Selected Country: " + country);

            driver.findElement(By.name("numeric-field")).sendKeys(phoneNo);
            logger.debug("Entered Phone Number: " + phoneNo);

            WebElement serviceDropdown = driver.findElement(By.name("dropdown"));
            dropDownSelection(serviceDropdown, service);
            logger.debug("Selected Service: " + service);

            driver.findElement(By.name("message")).sendKeys(message);
            logger.debug("Entered Message: " + message);

            // Update Excel with PASS
            ExcelUtil.writeTestResult(filePath, "Sheet1", rowIndex, "Pass", "Registeration Success");
            logger.info("==== Registration Test PASSED for: " + firstName + " ====");

        } catch (Exception e) {
            // Update Excel with FAIL
            ExcelUtil.writeTestResult(filePath, "Sheet1", rowIndex, "Fail", e.getMessage());
            logger.error("==== Registration Test FAILED for: " + firstName + " ====");
            logger.error("Error: " + e.getMessage());
            Assert.fail("Test failed for user: " + firstName + " due to: " + e.getMessage());
        }
    }

}
