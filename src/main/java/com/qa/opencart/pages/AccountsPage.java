package com.qa.opencart.pages;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class AccountsPage {
    private WebDriver driver;
    private ElementUtil elementUtil;
    //1. Constructor of the class
    public AccountsPage(WebDriver driver){
        this.driver = driver;
        elementUtil = new ElementUtil(this.driver);
    }
    //2. Private By locators
    private By logout = By.linkText("Logout");
    private By myAccount  = By.linkText("My Account");
    private By accHeaders = By.cssSelector("div#content h2");
    private By search = By.name("search");
    private By searchIcon = By.cssSelector("div#search button");

    //3. Public page actions/methods
    public String getAccPageTitle(){
        return elementUtil.waitForTitleIsAndCapture(AppConstants.ACCOUNTS_PAGE_TITLE_VALUE,AppConstants.SHORT_DEFAULT_TIME);
    }
    public boolean isLogoutLinkExist(){
        return elementUtil.checkIfElementIsDisplayed(logout);
    }
    public boolean isMyAccountLinkExist() {
        return elementUtil.checkIfElementIsDisplayed(myAccount);
    }

    public List<String> getAccountPageHeadersList(){
        List<WebElement> headersList = elementUtil.waitForElementsVisible(accHeaders,AppConstants.MEDIUM_DEFAULT_TIME);
        List<String> headersValueList = new ArrayList<>();
        for (WebElement e : headersList) {
            String text = e.getText();
            headersValueList.add(text);
        }
        return headersValueList;
    }
    @Step("Performing search of {0}")
    public ResultsPage doSearch(String searchTerm){
        elementUtil.waitForElementVisible(search,AppConstants.MEDIUM_DEFAULT_TIME);
        elementUtil.doSendKeys(search,searchTerm);
        elementUtil.doClick(searchIcon);
        return new ResultsPage(driver);
    }

}
