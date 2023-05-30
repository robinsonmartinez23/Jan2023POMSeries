package com.qa.opencart.tests;


import com.qa.opencart.base.BaseTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class ProductInfoPageTest extends BaseTest {

    @BeforeClass
    public void productInfoPageSetup(){
        accountsPage = loginPage.doLogin(properties.getProperty("username"),properties.getProperty("password"));
    }

    @Test
    public void productInfoTest(){
        resultsPage = accountsPage.doSearch("Macbook");
        productInfoPage = resultsPage.selectProduct("MacBook Pro");
        Map<String,String> productInfoMap = productInfoPage.getProductInfo();
        System.out.println(productInfoMap);
        // With hashMap -> {Brand=Apple, Availability=In Stock, Ex Tax=$2,000.00, Product Code=Product 18,
        //  Reward Points=800, Product Name=MacBook Pro, ProductPrice=$2,000.00}

        // With linkedHashMap -> {Product Name=MacBook Pro, Brand=Apple, Product Code=Product 18,
        //  Reward Points=800, Availability=In Stock, Product Price=$2,000.00, Ex Tax=$2,000.00}

        // With treeMap -> {Availability=In Stock, Brand=Apple, Ex Tax=$2,000.00, Product Code=Product 18,
        //  Product Name=MacBook Pro, Product Price=$2,000.00, Reward Points=800}

        //Hard Assertions are static we access to them by class name Assert.

//        Assert.assertEquals(productInfoMap.get("Brand"),"Apple");
//        Assert.assertEquals(productInfoMap.get("Availability"),"In Stock");
//        Assert.assertEquals(productInfoMap.get("Ex Tax"),"$2,000.00");
//        Assert.assertEquals(productInfoMap.get("Product Price"),"$2,000.00");
//        Assert.assertEquals(productInfoMap.get("Reward Points"),"800");

        // Soft Assertions are non-statics, that's why we need to create objects of it.
        // NOTE: Mandatory use of softAssert.assertAll(); at the end

        //softAssert.assertEquals(productInfoMap.get("Brand"),"AppleWRONG");
        //softAssert.assertEquals(productInfoMap.get("Availability"),"In StockWRONG");
        softAssert.assertEquals(productInfoMap.get("Ex Tax"),"$2,000.00");
        softAssert.assertEquals(productInfoMap.get("Product Price"),"$2,000.00");
        softAssert.assertEquals(productInfoMap.get("Reward Points"),"800");

        // It will let us know if "ALL" softAssert passed. If any sortAsser fails, it will be
        // displayed witch one failed, and other softAssert will run. However, the @Test will be
        // reported as Failed. The softAssert doesn't abort the entire @Test.
        // If we don't use softAssert.asserAll(); the @Test will pass even if wrong.
        softAssert.assertAll();
    }
}
