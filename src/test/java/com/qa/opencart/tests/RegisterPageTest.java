package com.qa.opencart.tests;

import com.qa.opencart.base.BaseTest;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ExcelUtil;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RegisterPageTest extends BaseTest {

    @BeforeClass
    public void registerPageSetup() {
        registerPage = loginPage.navigateToRegisterPage();
    }

    public String getRandomEmailID() {
        return "testautomation"+System.currentTimeMillis()+"@gmail.com";
        //return "testautomation" + UUID.randomUUID()+"@gmail.com";
    }


//*********************Implementation With simple DataProvider**********************

//    @DataProvider(name = "regData")
//    public Object[][] getUserRegTestData() {
//        return new Object[][] {
//                {"abhi", "anand", "9876545678", "abhi@123", "yes"},
//                {"robinson", "matinez", "9876545600", "robin@123", "no"},
//                {"amber", "automation", "9876545998", "amber@123", "yes"},
//        };
//    }

//    @Test(dataProvider = "regData")
//    public void userRegisterTest(String firstName, String lastName, String telephone, String password, String subscribe) {
//        String actRegSuccMessg =
//                registerPage.registerUser(firstName, lastName, getRandomEmailID(), telephone, password, subscribe);
//        Assert.assertEquals(actRegSuccMessg, AppConstants.USER_REG_SUCCESS_MSG);
//    }

//*****************Implementation With POI external excel (.xlsx) file ****************

    @DataProvider(name = "regExcelData")
    public Object [][] getRegExcelTestData(){
        Object regData [][] = ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
        return regData;
    }

    @Test(dataProvider = "regExcelData")
    public void userRegisterTest(String firstName, String lastName, String telephone, String password, String subscribe) {
        String actRegSuccMessg =
                registerPage.registerUser(firstName, lastName, getRandomEmailID(), telephone, password, subscribe);
        Assert.assertEquals(actRegSuccMessg, AppConstants.USER_REG_SUCCESS_MSG);
    }

}
