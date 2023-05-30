package com.qa.opencart.pages;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.*;

public class ProductInfoPage {
    private WebDriver driver;
    private ElementUtil elementUtil;
    //1. Constructor of the class
    public ProductInfoPage(WebDriver driver) {
        this.driver = driver;
        elementUtil = new ElementUtil(this.driver);
    }
    //2. Private Static By locators
    private By productHeader = By.cssSelector("div#content h1");
    private By productImages = By.cssSelector("ul.thumbnails img");
    private By productMetaData = By.xpath("(//div[@id='product-product']//ul[@class='list-unstyled'])[1]/li");
    private By productPriceData = By.xpath("(//div[@id='product-product']//ul[@class='list-unstyled'])[2]/li");
    private By quantity = By.id("input-quantity");
    private By addToCartBtn = By.id("button-cart");

    private Map<String,String> productInfoMap;

    //3. Public page actions/methods
    public String getProductHeaderName(){
        return elementUtil.doGetElementText(productHeader);
    }
    public int getProductImagesCount(){
        return elementUtil.waitForElementsVisible(productImages, AppConstants.MEDIUM_DEFAULT_TIME).size();
    }

    public Map<String, String> getProductInfo(){
        //productInfoMap = new HashMap<String,String>();       //->Do NOT maintain insertion order
        //productInfoMap = new LinkedHashMap<String,String>(); //->Maintain insertion order
        productInfoMap = new TreeMap<String,String>();         //->Sorted order (a-z,A-Z,0-9)

        productInfoMap.put("Product Name",getProductHeaderName()); // My Own custom key + implementation of getProductHeaderName to get the product's name
        getProductMetaData();
        getProductPriceData();
        return productInfoMap;
    }

    // Brand:Apple
    // Product: Product 18
    // Reward Points: 800
    // Availability: In Stock
    private void getProductMetaData(){
        List<WebElement> metaList = elementUtil.getElements(productMetaData);
        for (WebElement e : metaList) {
            String metaText = e.getText();
            String[] metaInfo = metaText.split(":");
            String key = metaInfo[0].trim();
            String value = metaInfo[1].trim();
            productInfoMap.put(key,value);
        }
    }
    // $2,000.00 -> I need to provide my own custom key
    // Ex Tax: $2,000.00
    private void getProductPriceData(){
        List<WebElement> priceList = elementUtil.getElements(productPriceData);
        String priceValue = priceList.get(0).getText(); // $2,000.00
        String exTaxPrice = priceList.get(1).getText(); // Ex Tax: $2,000.00
        String[] exTaxPriceInfo = exTaxPrice.split(":"); //[Ex Tax , $2,000.00]
        String key = exTaxPriceInfo[0].trim(); // Ex Tax
        String value = exTaxPriceInfo[1].trim();// $2,000.00
        productInfoMap.put("Product Price",priceValue); // My own custom key
        productInfoMap.put(key,value);
    }
}
