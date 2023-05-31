package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.AppConstants;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Epic 100: Login Page Design")
@Story("US 101: design login page for open cart app with title, url, forgot pwd links, user is able to login")
public class LoginPageTest extends BaseTest {
    @Severity(SeverityLevel.MINOR)
    @Description("Checking login page title")
    @Feature("Title test")
    @Test
    public void loginPageTitleTest(){
        String actTitle = loginPage.getLoginPageTitle();
        Assert.assertEquals(actTitle,AppConstants.LOGIN_PAGE_TITLE_VALUE);
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Checking login page url test...")
    @Feature("url test")
    @Test
    public void loginPageUrlTest(){
        String actUrl = loginPage.getLoginPageURL();
        Assert.assertTrue(actUrl.contains(AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE));
    }

    @Severity(SeverityLevel.CRITICAL)
    @Description("Checking forgot pwd link test...")
    @Feature("forgot pwd test")
    @Test
    public void forgotPwdLinkExistTest(){
        Assert.assertTrue(loginPage.isForgotPwdLinkExist());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Description("Checking user is able to login with correct username/password test...")
    @Feature("login test")
    @Test
    public void loginTest(){
        accountsPage = loginPage.doLogin(properties.getProperty("username"),properties.getProperty("password"));
        Assert.assertTrue(accountsPage.isLogoutLinkExist());
        Assert.assertEquals(accountsPage.getAccPageTitle(), AppConstants.ACCOUNTS_PAGE_TITLE_VALUE);
    }

}
