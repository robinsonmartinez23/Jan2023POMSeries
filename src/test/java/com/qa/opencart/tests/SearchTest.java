package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.dataproviders.ProductDataProvider;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SearchTest extends BaseTest {
    @BeforeClass
    public void searchSetup(){
        accountsPage = loginPage.doLogin(properties.getProperty("username"),properties.getProperty("password"));
    }

    @Test (dataProvider = "productDataWithSearchKey", dataProviderClass = ProductDataProvider.class)
    public void searchProductResultCountTest(String searchKey){
        resultsPage = accountsPage.doSearch(searchKey);
        Assert.assertTrue(resultsPage.getProductResultsCount()>0);
    }

    @Test (dataProvider = "productDataWithSearchKey", dataProviderClass = ProductDataProvider.class)
    public void searchPageTitleTest(String searchKey){
        resultsPage = accountsPage.doSearch(searchKey);
        String actualTitle = resultsPage.getResultPageTitle(searchKey);
        System.out.println("actualTitle = " + actualTitle);
        Assert.assertEquals(actualTitle, "Search - "+searchKey);
    }

    @Test(dataProvider = "productDataWithName", dataProviderClass = ProductDataProvider.class )
    public void selectProductTest(String searchKey, String productName){
        resultsPage = accountsPage.doSearch(searchKey);
        productInfoPage = resultsPage.selectProduct(productName);
        String actualProductHeaderName = productInfoPage.getProductHeaderName();
        System.out.println("actualProductHeaderName = " + actualProductHeaderName);
        Assert.assertEquals(actualProductHeaderName,productName);
    }

    @Test(dataProvider = "productDataWithImage", dataProviderClass = ProductDataProvider.class )
    public void productImagesTest(String searchKey,String productName,int expectImagesCount){
        resultsPage = accountsPage.doSearch(searchKey);
        productInfoPage = resultsPage.selectProduct(productName);
        int actualProductImagesCount = productInfoPage.getProductImagesCount();
        System.out.println("actualProductImagesCount = " + actualProductImagesCount);
        Assert.assertEquals(actualProductImagesCount, expectImagesCount);
    }
}
