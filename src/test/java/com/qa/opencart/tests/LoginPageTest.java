package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.AppConstants;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPageTest extends BaseTest {

    @Test
    public void loginPageTitleTest(){
        String actTitle = loginPage.getLoginPageTitle();
        Assert.assertEquals(actTitle,AppConstants.LOGIN_PAGE_TITLE_VALUE);
    }
    @Test
    public void loginPageUrlTest(){
        String actUrl = loginPage.getLoginPageURL();
        Assert.assertTrue(actUrl.contains(AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE));
    }
    @Test
    public void forgotPwdLinkExistTest(){
        Assert.assertTrue(loginPage.isForgotPwdLinkExist());
    }
    @Test
    public void loginTest(){
        accountsPage = loginPage.doLogin(properties.getProperty("username"),properties.getProperty("password"));
        Assert.assertTrue(accountsPage.isLogoutLinkExist());
        Assert.assertEquals(accountsPage.getAccPageTitle(), AppConstants.ACCOUNTS_PAGE_TITLE_VALUE);
    }

}
