package com.qa.opencart.dataproviders;

import com.qa.opencart.pojo.Product;
import org.testng.annotations.DataProvider;

public class ProductDataProvider {

    /*
     *******************Static Data using DataProvider**************************
     */



    // This DataProvider uses Pojo class to create new "Product" objects with 3 parameters
    // following the concept of constructor. The data will be returned as 2 dimension array. It means,
    // An array of arrays of type Object. { {object 1}, {object 2},{object 3},{object 4}}.
    // Note: Object[] can contain multiple types of variables. Then...
    // {{String str, int num},{String str, int num},{String str, int num} }

    @DataProvider(name = "productData")
    public Object[][] getProductTestData() {
        return new Object[][] {
                {new Product("Macbook", "MacBook Pro", 4)},
                {new Product("iMac", "iMac", 3)},
                {new Product("Samsung", "Samsung Galaxy Tab 10.1", 7)},
                {new Product("Samsung", "Samsung SyncMaster 941BW", 1)}
        };
    }

    // These Data providers are not creating an object Product. Here we define DataProvider name, and
    // as above we create the array of objects, here we define the number of variables according to
    // our needs because it is not attached to the creation of a specific object with specific arguments.


    // CONCLUSION: We can create an object with as many arguments we want and access to their values by
    // getters and setters passing to the @Test a reference of an object, on this case Product product.
    // e.g. public void nameOfTest(Product product) the test will receive a reference of Product called
    // product and, we can say product.getMethod or product.setMethod to access the variables
    //
    // Or, we don't create an object, we just pass the arguments of the Data provider and the @Test
    // will receive the arguments with the type and name of variable that we decide, according to
    // our DataProvider structure. In this case, the first argument will be String searchKey,
    // second String productName and 3rd int expectedImageCount. Names are on us, again, depends on
    // the structure of our own DataProvider.

    @DataProvider(name = "productDataWithImage")
    public Object[][] getProductImagesTestData() {
        return new Object[][] {
                {"Macbook", "MacBook Pro", 4},
                {"iMac", "iMac", 3},
                {"Samsung", "Samsung SyncMaster 941BW", 1},
                {"Samsung", "Samsung Galaxy Tab 10.1", 7},
        };
    }

    @DataProvider(name = "productDataWithName")
    public Object[][] getProductData() {
        return new Object[][] {

                {"Macbook", "MacBook Pro"},
                {"iMac", "iMac"},
                {"Samsung", "Samsung SyncMaster 941BW"},
                {"Samsung", "Samsung Galaxy Tab 10.1"},
        };
    }

    @DataProvider(name = "productDataWithSearchKey")
    public Object[][] getProductSearchKeyData() {
        return new Object[][] {
                {"Macbook"},
                {"iMac"},
                {"Samsung"}

        };
    }
}
