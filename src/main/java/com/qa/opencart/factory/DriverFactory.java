package com.qa.opencart.factory;

import com.qa.opencart.framework_exception.FrameworkException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class DriverFactory {

    //WebDriver driver;
    OptionsManager optionsManager;
    public static String highlightElement;

    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public WebDriver initDriver(Properties prop) {

        String browserName = prop.getProperty("browser");

        //If we want input from command line of this parameter
        //String browserName = System.getProperty("browser");

        System.out.println("Browser name is: " + browserName);

        highlightElement = prop.getProperty("highlight");

        optionsManager = new OptionsManager(prop);

        switch (browserName.toLowerCase().trim()) {
            case "chrome":
                //driver = new ChromeDriver(optionsManager.getChromeOptions());
                tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
                break;
            case "edge":
                //driver = new EdgeDriver(optionsManager.getEdgeOptions());
                tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
                break;
            case "safari":
                //driver = new SafariDriver();
                tlDriver.set(new SafariDriver());
                break;
            case "firefox":
                //driver = new FirefoxDriver(optionsManager.getFireFoxOptions());
                tlDriver.set(new FirefoxDriver(optionsManager.getFireFoxOptions()));
                break;
            default:
                System.out.println(browserName + " is not a valid browser, please enter a valid one");
                throw new FrameworkException("NO_BROWSER_FOUND_EXCEPTION");
        }
        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
        getDriver().get(prop.getProperty("url"));
        //return driver;
        return getDriver();
    }

    //Return the thread Local copy of the driver. Individual for each thread
    public synchronized static WebDriver getDriver() {
        return tlDriver.get();
    }

    public Properties initProperties(){

        /*
          With the help of command line (CL), we can use the -D option to pass any system
          properties that our Maven project or plugins might require. For instance, we can
          pass properties related to configurations, profiles, versions, or any other custom
          properties needed for the build. In the example below, we created the variable env
          with value a "qa"

         */

        /*
          mvm clean install -Denv="qa"  ->  here we create pass the variable env with value "qa"
          mvm clean install             -> Here we don't specify the environment
         */

        //C:\Users\RobinM\OneDrive\Escritorio\IntelliJProjets\Jan2023POMSeries>mvm clean install -Denv="qa"
        //C:\Users\RobinM\OneDrive\Escritorio\IntelliJProjets\Jan2023POMSeries>mvm clean install

        Properties properties = new Properties();
        FileInputStream fileInputStream = null;

        String envName = System.getProperty("env"); // We will read the variable env from CL with
                                                    // the help of System class
        System.out.println("Environment name is: " + envName);

        try {
            if (envName == null) {  // if we don't specify environment as example above then...
                System.out.println("No env is given..hence running it on QA environment");
                fileInputStream = new FileInputStream("./src/main/resources/config/qa.config.properties");
            } else {                // if we create the variable env as example above, then...
                System.out.println("Running test cases on env: " + envName);
                switch (envName.toLowerCase().trim()) {
                    case "qa":
                        fileInputStream = new FileInputStream("./src/main/resources/config/qa.config.properties");
                        break;
                    case "dev":
                        fileInputStream = new FileInputStream("./src/main/resources/config/dev.config.properties");
                        break;
                    case "stage":
                        fileInputStream = new FileInputStream("./src/main/resources/config/stage.config.properties");
                        break;
                    case "uat":
                        fileInputStream = new FileInputStream("./src/main/resources/config/uat.config.properties");
                        break;
                    case "prod":
                        fileInputStream = new FileInputStream("./src/main/resources/config/config.properties");
                        break;
                    default:
                        System.out.println(envName + " is an invalid environment name, please entry a valid one");
                        throw new FrameworkException("NON_VALID_ENV_NAME_GIVE");
                }
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        try {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * Take screenshot
     */

    public static String getScreenshot() {
        File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
        File destination = new File(path);

        try {
            FileUtils.copyFile(srcFile, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }
}
