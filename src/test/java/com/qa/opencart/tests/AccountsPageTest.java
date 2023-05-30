package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.utils.AppConstants;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class AccountsPageTest extends BaseTest {

    @BeforeClass
    public void accountPageSetup(){
        accountsPage = loginPage.doLogin(properties.getProperty("username"),properties.getProperty("password"));
    }
    @Test
    public void accPageTitleTest(){
        String actTitle = accountsPage.getAccPageTitle();
        Assert.assertEquals(actTitle, AppConstants.ACCOUNTS_PAGE_TITLE_VALUE);
    }
    @Test
    public void isLogoutLinkExistTest(){
        Assert.assertTrue(accountsPage.isLogoutLinkExist());
    }
    @Test
    public void isMyAccountLinkExistTest(){
        Assert.assertTrue(accountsPage.isMyAccountLinkExist());
    }
    @Test
    public void accPageHeadersCountTest(){
        List<String> actualAccHeadersList = accountsPage.getAccountPageHeadersList();
        Assert.assertEquals(actualAccHeadersList.size(),4);
    }
    @Test
    public void accPageHeadersTest(){
        List<String> actualAccHeadersList = accountsPage.getAccountPageHeadersList();
        Assert.assertEquals(actualAccHeadersList,AppConstants.EXPECTED_ACCOUNTS_HEADERS_LIST);
    }

}
