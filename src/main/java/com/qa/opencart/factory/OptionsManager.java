package com.qa.opencart.factory;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class OptionsManager {
    private Properties prop;
    private ChromeOptions co;
    private FirefoxOptions fo;
    private EdgeOptions eo;

    public OptionsManager(Properties prop){
        this.prop=prop;
    }
    public ChromeOptions getChromeOptions(){
        co = new ChromeOptions();
        if(Boolean.parseBoolean((prop.getProperty("headless")))){
            co.addArguments("--headless");
            System.out.println("headless mode activated");
        }
        if(Boolean.parseBoolean((prop.getProperty("incognito")))){
            co.addArguments("--incognito");
            System.out.println("incognito mode activated");
        }
        if(Boolean.parseBoolean((prop.getProperty("remote")))){
            co.setCapability("browserName","chrome");
            co.setBrowserVersion(prop.getProperty("browserversion"));

            //Options for visualization in Selenoid
            Map<String, Object> selenoidOptions = new HashMap<>();
            selenoidOptions.put("screenResolution", "1280x1024x24");
            selenoidOptions.put("enableVNC", true);
            selenoidOptions.put("name", prop.getProperty("testname"));
            co.setCapability("selenoid:options", selenoidOptions);
        }
        return co;

        /* ***************** Another options for Chrome***************

        co.addArguments("--window-size=1920,1080");
        co.addArguments("--no-sandbox");
        co.addArguments("--disable-gpu");
        co.addArguments("--disable-crash-reporter");
        co.addArguments("--disable-extensions");
        co.addArguments("--disable-in-process-stack-traces");
        co.addArguments("--disable-logging");
        co.addArguments("--disable-dev-shm-usage");
        co.addArguments("--log-level=3");
        co.addArguments("--output=/dev/null");
        */
    }
    public FirefoxOptions getFireFoxOptions(){
        fo = new FirefoxOptions();
        if(Boolean.parseBoolean((prop.getProperty("headless")))){
            fo.addArguments("--headless");
            System.out.println("headless mode activated");
        }
        if(Boolean.parseBoolean((prop.getProperty("incognito")))){
            fo.addArguments("--incognito");
            System.out.println("incognito mode activated");
        }
        if(Boolean.parseBoolean((prop.getProperty("remote")))){
            fo.setCapability("browserName","firefox");
            fo.setBrowserVersion(prop.getProperty("browserversion"));

            //Options for visualization in Selenoid
            Map<String, Object> selenoidOptions = new HashMap<>();
            selenoidOptions.put("screenResolution", "1280x1024x24");
            selenoidOptions.put("enableVNC", true);
            selenoidOptions.put("name", prop.getProperty("testname"));
            fo.setCapability("selenoid:options", selenoidOptions);
        }
        return fo;
    }

    public EdgeOptions getEdgeOptions(){
        eo = new EdgeOptions();
        if(Boolean.parseBoolean((prop.getProperty("headless")))){
            eo.addArguments("--headless");
            System.out.println("headless mode activated");
        }
        if(Boolean.parseBoolean((prop.getProperty("incognito")))){
            eo.addArguments("--incognito");
            System.out.println("incognito mode activated");
        }
        if(Boolean.parseBoolean((prop.getProperty("remote")))){
            eo.setCapability("browserName","MicrosoftEdge");
            eo.setBrowserVersion(prop.getProperty("browserversion"));
        }
        return eo;
    }

}
