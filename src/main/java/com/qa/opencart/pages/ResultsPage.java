package com.qa.opencart.pages;

import com.qa.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ResultsPage {
    private WebDriver driver;
    private ElementUtil elementUtil;


    //1.Constructor of the class
    public ResultsPage(WebDriver driver) {
        this.driver = driver;
        elementUtil = new ElementUtil(this.driver);
    }
    //2.Static Private By locators
    private By resultProduct = By.cssSelector("div.product-layout.product-grid");

    //3.Page Actions/Methods
    public String getResultPageTitle(String searchKey){
        return elementUtil.waitForTitleIsAndCapture(searchKey,5);
    }
    public int getProductResultsCount(){
        int resultCount = elementUtil.waitForElementsVisible(resultProduct,10).size();
        System.out.println("Product search result count = " + resultCount);
        return resultCount;
    }
    public ProductInfoPage selectProduct(String productName){
        // Dynamic By locator
        By productNameLocator = By.linkText(productName);
        elementUtil.doClick(productNameLocator);
        return new ProductInfoPage(driver);
    }
}
