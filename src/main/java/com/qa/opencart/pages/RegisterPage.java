package com.qa.opencart.pages;

import com.qa.opencart.utils.AppConstants;
import com.qa.opencart.utils.ElementUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage {

    private WebDriver driver;
    private ElementUtil elementUtil;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        elementUtil = new ElementUtil(this.driver);
    }

    private By firstName = By.id("input-firstname");
    private By lastName = By.id("input-lastname");
    private By email = By.id("input-email");
    private By telephone = By.id("input-telephone");
    private By password = By.id("input-password");
    private By confirmpassword = By.id("input-confirm");

    private By agreeCheckBox = By.name("agree");
    private By continueButton = By.xpath("//input[@type='submit' and @value='Continue']");

    private By subscribeYes = By.xpath("(//label[@class='radio-inline'])[1]/input");
    private By subscribeNo = By.xpath("(//label[@class='radio-inline'])[2]/input");

    private By userRegSuccMessg = By.cssSelector("div#content h1");
    private By logoutLink = By.linkText("Logout");
    private By registerLink = By.linkText("Register");


    public String registerUser(String firstName, String lastName,
                               String email, String telephone, String password, String subscribe) {

        elementUtil.waitForElementVisible(this.firstName, AppConstants.MEDIUM_DEFAULT_TIME);
        elementUtil.doSendKeys(this.firstName, firstName);
        elementUtil.doSendKeys(this.lastName, lastName);
        elementUtil.doSendKeys(this.email, email);
        elementUtil.doSendKeys(this.telephone, telephone);
        elementUtil.doSendKeys(this.password, password);
        elementUtil.doSendKeys(this.confirmpassword, password);

        doSubscribe(subscribe);

        elementUtil.doClick(agreeCheckBox);
        elementUtil.doClick(continueButton);

        String userRegSuccessMsg =
                elementUtil.waitForElementVisible(userRegSuccMessg, AppConstants.MEDIUM_DEFAULT_TIME).getText();
        System.out.println(userRegSuccessMsg);

        elementUtil.doClick(logoutLink);
        elementUtil.doClick(registerLink);

        return userRegSuccessMsg;
    }

    private void doSubscribe(String subscribe) {
        if(subscribe.equalsIgnoreCase("yes")) {
            elementUtil.doClick(subscribeYes);
        }
        else {
            elementUtil.doClick(subscribeNo);
        }
    }
}
