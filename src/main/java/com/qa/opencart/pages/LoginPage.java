package com.qa.opencart.pages;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class LoginPage {
    private WebDriver driver;
    private ElementUtil elementUtil;

    /**
     *  Credentials:
     *  username: janautomation@gmail.com
     *  pwd: Selenium@12345
     *  TDD: Test Driven Development
     */

    //1. Constructor of the class
    public LoginPage(WebDriver driver){
        this.driver = driver;
        elementUtil = new ElementUtil(this.driver);
    }
    //2. Private Static By locators
    private By emailId = By.id("input-email");
    private By password = By.id("input-password");
    private By loginBtn = By.xpath("//input[@value='Login']");
    private By forgotPwdLink = By.linkText("Forgotten Password11");
    private By footerLinks = By.xpath("//footer//a");
    private By loginErrorMsg = By.cssSelector("div.alert.alert-danger.alert-dismissible");
    private By registerLink = By.linkText("Register");

    //3. Public page actions/methods
    @Step("Getting login page title")
    public String getLoginPageTitle(){
        return elementUtil.waitForTitleIsAndCapture(AppConstants.LOGIN_PAGE_TITLE_VALUE,AppConstants.SHORT_DEFAULT_TIME);
    }
    @Step("Getting login page url")
    public String getLoginPageURL() {
        return elementUtil.waitForURLContainsAndCapture(AppConstants.LOGIN_PAGE_URL_FRACTION_VALUE, AppConstants.SHORT_DEFAULT_TIME);
    }
    @Step("Checking forgot pwd link exist on the login page")
    public boolean isForgotPwdLinkExist(){
        return elementUtil.checkIfElementIsDisplayed(forgotPwdLink);
    }
    @Step("Getting footer links")
    public List<String> getFooterLinkList(){
        List<WebElement> footerLinkList = elementUtil.waitForElementsVisible(footerLinks,AppConstants.MEDIUM_DEFAULT_TIME);
        List<String> footerTextList = new ArrayList<>();
        for (WebElement e : footerLinkList) {
            String text = e.getText();
            footerTextList.add(text);
        }
        return footerTextList;
    }
    @Step("Login with username {0} and password {1} ")
    public AccountsPage doLogin(String userName, String pwd){
        System.out.println("Correct credentials are: "+userName+" : "+pwd);
        elementUtil.waitForElementVisible(emailId,AppConstants.MEDIUM_DEFAULT_TIME);
        elementUtil.doSendKeys(emailId,userName);
        elementUtil.doSendKeys(password,pwd);
        elementUtil.doClick(loginBtn);
        // return the next landing page ->AccountsPage. This pattern is known as "chaining model".
        return new AccountsPage(driver);
    }
    @Step("Login with wrong username {0} and password {1} ")
    public boolean doLoginWithWrongCredentials(String userName, String pwd){
        System.out.println("Wrong credentials are: "+userName+" : "+pwd);
        elementUtil.waitForElementVisible(emailId,AppConstants.MEDIUM_DEFAULT_TIME);
        elementUtil.doSendKeys(emailId,userName);
        elementUtil.doSendKeys(password,pwd);
        elementUtil.doClick(loginBtn);
        String errorMsg = elementUtil.doGetElementText(loginErrorMsg);
        if (errorMsg.contains(AppConstants.LOGIN_ERROR_MESSAGE)){
            return true;
        }
        return false;
    }

    public RegisterPage navigateToRegisterPage() {
        elementUtil.doClick(registerLink);
        return new RegisterPage(driver);
    }

}
