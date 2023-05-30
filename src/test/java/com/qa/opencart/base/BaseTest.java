package com.qa.opencart.base;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import java.io.FileNotFoundException;
import java.util.Properties;

public class BaseTest {
    WebDriver driver;
    protected DriverFactory driverFactory;
    protected Properties properties;
    // Page References
    protected LoginPage loginPage;
    protected AccountsPage accountsPage;
    protected ResultsPage resultsPage;
    protected ProductInfoPage productInfoPage;
    protected RegisterPage registerPage;
    // Soft Assertion reference
    protected SoftAssert softAssert;


    @Parameters({"browser"}) // This value will be given to set up method
    @BeforeTest
    public void setup(@Optional String browserName) {     // Here the browser's value
        driverFactory = new DriverFactory();
        properties = driverFactory.initProperties(); // properties will take the values from
                                                     // config.properties by default
            if(browserName!=null){
                properties.setProperty("browser",browserName); // If we decide provide a different
                                                               // browser name here we will change it
                                                               // based in the browserName value
            }

        driver = driverFactory.initDriver(properties);

        loginPage = new LoginPage(driver);
        softAssert = new SoftAssert();
    }
    @AfterTest
    public void tearDown(){
        driver.quit();
    }
}
