package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginPageNegativeTest extends BaseTest {
    @DataProvider
    public Object[][] incorrectLoginTestData() {
        return new Object[][] {
                {"auto123@gmaill.com", "123456"},
                {"test@@gmaill.com", "123456"},
                {"auto", "test"},
                {"#@$@#$@#$@", "@#!@#!@#!"}
        };

    }
    @Test(dataProvider = "incorrectLoginTestData")
    public void loginWithWrongCredentialsTest(String userName, String password){
        Assert.assertTrue(loginPage.doLoginWithWrongCredentials(userName, password));
    }
}
