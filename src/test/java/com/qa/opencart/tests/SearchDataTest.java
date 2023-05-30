package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.dataproviders.ProductDataProvider;
import com.qa.opencart.pojo.Product;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SearchDataTest extends BaseTest {

    // Approach of SearchTest using POJO class

    @BeforeClass
    public void searchSetup(){
        accountsPage = loginPage.doLogin(properties.getProperty("username"),properties.getProperty("password"));
    }

    /*
     * Note: The DataProvider can be moved to the package dataproviders under ProductDataProvider class
     * then we can use the dataProviderClass annotation as follows:
     *
     * * @Test(dataProvider = "productData", dataProviderClass = ProductDataProvider.class)
     */

//    @DataProvider(name = "productData")
//    public Object[][] getProductTestData(){
//        return new Object[][]{
//                {new Product ("Macbook", "MacBook Pro",4)},
//                {new Product("iMac", "iMac",3)},
//                {new Product("Samsung", "Samsung SyncMaster 941BW",1)},
//                {new Product("Samsung", "Samsung Galaxy Tab 10.1",7)},
//        };
//    }

    @Test(dataProvider = "productData", dataProviderClass = ProductDataProvider.class)
    public void searchProductResultCountTest(Product product){
        resultsPage = accountsPage.doSearch(product.getSearchKey());
        Assert.assertTrue(resultsPage.getProductResultsCount()>0);
    }

    @Test(dataProvider = "productData", dataProviderClass = ProductDataProvider.class)
    public void searchPageTitleTest(Product product){
        resultsPage = accountsPage.doSearch(product.getSearchKey());
        String actualTitle = resultsPage.getResultPageTitle(product.getSearchKey());
        System.out.println("actualTitle = " + actualTitle);
        Assert.assertEquals(actualTitle, "Search - "+product.getSearchKey());
    }

    @Test(dataProvider = "productData", dataProviderClass = ProductDataProvider.class)
    public void selectProductTest(Product product){
        resultsPage = accountsPage.doSearch(product.getSearchKey());
        productInfoPage = resultsPage.selectProduct(product.getProductName());
        String actualProductHeaderName = productInfoPage.getProductHeaderName();
        System.out.println("actualProductHeaderName = " + actualProductHeaderName);
        Assert.assertEquals(actualProductHeaderName,product.getProductName());
    }

    @Test(dataProvider = "productData",dataProviderClass = ProductDataProvider.class )
    public void productImagesTest(Product product){
        resultsPage = accountsPage.doSearch(product.getSearchKey());
        productInfoPage = resultsPage.selectProduct(product.getProductName());
        int actualProductImagesCount = productInfoPage.getProductImagesCount();
        System.out.println("actualProductImagesCount = " + actualProductImagesCount);
        Assert.assertEquals(actualProductImagesCount, product.getProductImages());
    }
}
