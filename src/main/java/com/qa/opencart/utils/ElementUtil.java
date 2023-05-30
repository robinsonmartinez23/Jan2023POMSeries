package com.qa.opencart.utils;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.framework_exception.FrameworkException;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ElementUtil {
    private WebDriver driver;
    private JavaScriptUtil javaScriptUtil;
    public final int DEFAULT_TIME_OUT = 5;
    public ElementUtil(WebDriver driver) {
        this.driver = driver;
        javaScriptUtil = new JavaScriptUtil(this.driver);
    }


    public WebElement getElement(By locator, int timeOut) {
        WebElement element =  waitForElementVisible(locator,timeOut);
        if(Boolean.parseBoolean(DriverFactory.highlightElement)){
            javaScriptUtil.flash(element);
        }
        return element;
    }

    public WebElement getElement(By locator) {
        WebElement element;  // null by default
        try {
            element = driver.findElement(locator);
            System.out.println("Element has been found with locator " + locator);
        } catch (NoSuchElementException e) {
            System.out.println("Element hasn't been found using the locator " + locator);
            element = waitForElementVisible(locator,DEFAULT_TIME_OUT);
        }
        if(Boolean.parseBoolean(DriverFactory.highlightElement)){
            javaScriptUtil.flash(element);
        }
        return element;
    }

    public void doSendKeys(By locator, String value) {
        if (value == null) {
            System.out.println("Null values are not allowed");
            throw new FrameworkException("IllegalArgumentException - ValueCanNotBeNull");
        }
        WebElement element = getElement(locator);
        element.clear();
        element.sendKeys(value);
    }
    public void doClear(By locator) {
        getElement(locator).clear();
    }

    public void doClick(By locator) {
        getElement(locator).click();
    }
    public void doClick(By locator, int timeOut) {
        checkElementClickable(locator,timeOut);
    }

    public String doGetElementText(By locator) {
        return getElement(locator).getText();
    }

    public boolean checkIfElementIsDisplayed(By locator) {
        return getElement(locator).isDisplayed();
    }

    public String doGetAttributeValue(By locator, String attributeName) {
        return getElement(locator).getAttribute(attributeName);
    }

    // WebElements

    /**
     * Find the web elements based on locator provided
     * @param locator of web elements
     * @return List of web elements
     */
    public List<WebElement> getElements(By locator) {
        return driver.findElements(locator);
    }

    /**
     * Find the number of web elements found by getElements
     * @param locator of the web elements
     * @return number of web elements in the list
     */
    public int getElementsCount(By locator) {
        return getElements(locator).size();
    }

    /**
     * Find a specific attribute of a list of web elements
     * @param locator of web elements to be found by getElements
     * @param attributeName attribute required
     * @return List of the attribute required from the web elements
     */


    public List<String> getElementsAttributeValue(By locator, String attributeName) {
        List<WebElement> elementList = getElements(locator);
        List<String> elementAttributeList = new ArrayList<>();
        for (WebElement each : elementList) {
            String value = each.getAttribute(attributeName);
            System.out.println(value);
            elementAttributeList.add(value);
        }
        return elementAttributeList;
    }

    /**
     * Get the text of a list of web elements
     * @param locator of web elements that contains text
     * @return List of the text of all web elements
     */
    public List<String> getElementsTextList(By locator){
        List<WebElement>elementsLinksList = getElements(locator);
        List<String> elementsTextList = new ArrayList<>();

        for (WebElement each : elementsLinksList) {
            String text = each.getText();
        }
        return elementsTextList;
    }

    /**
     * Find specific element in a section DOM section and click on it
     * @param locator of web elements in a section
     * @param elementText element to find and click in that section
     */
    public void clickElementPageFromSection(By locator,String elementText){
        List<WebElement> elementList = getElements(locator);
        for(WebElement each : elementList){
            String text = each.getText();
            //System.out.println(text);
            if(text.equals(elementText)){
                each.click();
                break;
            }
        }
    }
    public void search(String searchKey, By searchLocator, String suggestionName, By suggestions) throws InterruptedException {
        doSendKeys(searchLocator,searchKey);
        Thread.sleep(3000);
        List<WebElement> suggestionList = getElements(suggestions);
        System.out.println("Total suggestions = " + suggestionList.size());
        if (suggestionList.size() > 0) {
            for (WebElement e : suggestionList) {
                String text = e.getText();
                if (text.length() > 0) {
                    System.out.println(text);
                    if (text.contains(suggestionName)) {
                        e.click();
                        break;
                    }else{
                        System.out.println("Suggestion in not present");
                    }
                }else {
                    System.out.println("Blank values");
                }
            }
        }else{
            System.out.println("No search suggestions found");
        }
    }





//    Another variation of checkIfElementIsDisplayed
//    public boolean checkIfElementIsDisplayed(By locator) {
//        List<WebElement> elementList = getElements(locator);
//        if (elementList.size() > 0){
//            System.out.println("Element is present on the page");
//            return true;
//        }else{
//            return false;
//        }
//    }



    //******************* Drop Down Utilities *******************


    public void doSelectDropDownByIndex(By locator, int index){
        Select select = new Select(getElement(locator));
        select.selectByIndex(index);
    }
    public void doSelectDropDownByVisibleText(By locator, String text){
        Select select = new Select(getElement(locator));
        select.selectByVisibleText(text);
    }
    public void doSelectDropDownByValueAttribute(By locator, String value ){
        Select select = new Select(getElement(locator));
        select.selectByValue(value);
    }
    public void doDeselectDropDownByIndex(By locator, int index){
        Select select = new Select(getElement(locator));
        select.selectByIndex(index);
    }
    public void doDeselectDropDownByVisibleText(By locator, String text){
        Select select = new Select(getElement(locator));
        select.selectByVisibleText(text);
    }
    public void doDeselectDropDownByValueAttribute(By locator, String value ){
        Select select = new Select(getElement(locator));
        select.selectByValue(value);
    }
    public int getDropDownValueCount(By locator){
        return getAllDropDownOption(locator).size();
    }

    public List<String> getAllDropDownOption(By locator){
        Select select = new Select(getElement(locator));
        List<WebElement> optionsList = select.getOptions();
        List<String> optionsValueList = new ArrayList<>();
        System.out.println("Total values: "+optionsList.size());

        for (WebElement e : optionsList) {
            String text = e.getText();
            System.out.println(text);
            optionsValueList.add(text);
        }
        return optionsValueList;
    }
    public boolean doSelectDropDownValue(By locator, String value){
        boolean isSelected = false;
        Select select = new Select(getElement(locator));
        List<WebElement> optionsList = select.getOptions();
        System.out.println("Total values: "+optionsList.size());

        for (WebElement e : optionsList) {
            String text = e.getText();
            System.out.println(text);
            if(text.equals(value)){
                isSelected = true;
                e.click();
                break;
            }
        }
        if(!isSelected){ // isSelected == false
            System.out.println("Drop Down value "+value+" is not present in "+locator);
        }
        return isSelected;
    }

    public boolean doSelectValueFromDropDownWithoutSelect(By locator, String value){
        boolean isSelected = false;
        List<WebElement> optionsList = getElements(locator);
        System.out.println("Total values: "+optionsList.size());
        for (WebElement e : optionsList) {
            String text = e.getText();
            if(text.equals(value)){
                isSelected = true;
                e.click();
                break;
            }
        }
        if(!isSelected){ // isSelected == false
            System.out.println("Drop Down value "+value+" is not present in "+locator);
        }
        return isSelected;
    }

    //******************* Action Class Utilities *******************

    public void doActionsSendKeys(By locator, String value) {
        Actions actions = new Actions(driver);
        actions.sendKeys(getElement(locator), value).build().perform();
    }


    public void doActionsClick(By locator) {
        Actions actions = new Actions(driver);
        actions.click(getElement(locator)).build().perform();
    }
    public void doActionsClick(By locator, int timeOut) {
        Actions actions = new Actions(driver);
        actions.click(checkElementClickable(locator, timeOut)).build().perform();
    }

    public void doDragAndDrop (By sourceLocator, By targetLocator){
        Actions actions = new Actions(driver);
        actions.dragAndDrop(getElement(sourceLocator),getElement(targetLocator)).build().perform();
    }

    public void doContextClick(By locator){
        Actions actions = new Actions(driver);
        actions.contextClick(getElement(locator)).build().perform();
    }

    public void doMoveToElement(By locator){
        Actions actions = new Actions(driver);
        actions.moveToElement(getElement(locator)).build().perform();
    }
    public  void handleTwoLevelMenu(By parentMenu, By childMenu) throws InterruptedException {
        doMoveToElement(parentMenu);
        Thread.sleep(2000);
        doClick(childMenu);
    }

    public void handleTwoLevelMenu(By parentMenu, String childMenuLinkText) throws InterruptedException {
        doMoveToElement(parentMenu);
        Thread.sleep(2000);
        doClick(By.linkText(childMenuLinkText));
    }

    public void multiLevelMenuChildMenuHandle(By parentMenuLocator, String level2LinkText, String level3LinkText,
                                              String level4LinkText) throws InterruptedException {

        WebElement level1 = getElement(parentMenuLocator);

        Actions actions = new Actions(driver);

        actions.moveToElement(level1).build().perform();
        Thread.sleep(1000);

        WebElement level2 = getElement(By.linkText(level2LinkText));
        actions.moveToElement(level2).build().perform();
        Thread.sleep(1000);

        WebElement level3 = getElement(By.linkText(level3LinkText));
        actions.moveToElement(level3).build().perform();
        Thread.sleep(1000);

        doClick(By.linkText(level4LinkText));

    }
    //***************************Wait Utils*****************************

    /**
     *  JavaScript Wait Alerts
     */

    public Alert waitForAlertJsPopUpWithFluentWait(int timeOut, int pollingTime) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(timeOut))
                .ignoring(NoAlertPresentException.class)
                .pollingEvery(Duration.ofSeconds(pollingTime))
                .withMessage("------time out is done...Alert is not found.....");
        return wait.until(ExpectedConditions.alertIsPresent());
    }
    public  Alert waitForAlertJsPopUp(int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    public String alertJSGetText(int timeOut) {
        return waitForAlertJsPopUp(timeOut).getText();
    }

    public  void alertAccept(int timeOut) {
        waitForAlertJsPopUp(timeOut).accept();
    }

    public  void alertDismiss(int timeOut) {
        waitForAlertJsPopUp(timeOut).dismiss();
    }

    public  void EnterAlertValue(int timeOut, String value) {
        waitForAlertJsPopUp(timeOut).sendKeys(value);
    }

    /**
     * An expectation for checking that an element is present on the DOM of a page.
     * This does not necessarily mean that the element is visible.
     * @param locator
     * @param timeOut
     * @return
     */
    public WebElement waitForElementPresence(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));

    }

    /**
     * An expectation for checking that an element is present on the DOM of a page
     * and visible on the page. Visibility means that the element is not only
     * displayed but also has a height and width that is greater than 0.
     *
     * @param locator
     * @param timeOut
     */
    public WebElement waitForElementVisible(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        if(Boolean.parseBoolean(DriverFactory.highlightElement)){
            javaScriptUtil.flash(element);
        }
        return element;
    }

    /**
     * An expectation for checking that there is at least one element present on the web page
     * @param locator
     * @param timeOut
     * @return
     */
    public List<WebElement> waitForElementPresences(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    /**
     * An expectation for checking that all elements present on the web page that match the locator are visible.
     * Visibility means that the elements are not only displayed but also have a height and width that is greater than 0.
     * @param locator
     * @param timeOut
     * @return
     */
    public List<WebElement> waitForElementsVisible(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    // Default timeOut = intervalTime
    public List<WebElement> waitForElementsVisible(By locator, int timeOut, int intervalTime) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut),Duration.ofSeconds(intervalTime));
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    /**
     * An expectation for checking and element is visible and enable such that you can click it
     * @param locator
     * @param timeOut
     */
    public void clickElementWhenReady(By locator, int timeOut){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }
    /**
     * An expectation for checking and element is visible and enable such that you can click it
     * @param locator
     * @param timeOut
     */
    public WebElement checkElementClickable(By locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     *  Wait and Get Title or Url
     */
    public String waitForURLContainsAndCapture(String urlFraction, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        if (wait.until(ExpectedConditions.urlContains(urlFraction))) {
            String url = driver.getCurrentUrl();
            return url;
        } else {
            System.out.println("url is not present within the given timeout : " + timeOut);
            return null;
        }
    }

    public String waitForURLAndCapture(String urlValue, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        if (wait.until(ExpectedConditions.urlToBe(urlValue))) {
            String url = driver.getCurrentUrl();
            return url;
        } else {
            System.out.println("url is not present within the given timeout : " + timeOut);
            return null;
        }
    }

    public String waitForTitleIsAndCapture(String titleFraction, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        if (wait.until(ExpectedConditions.titleContains(titleFraction))) {
            String title = driver.getTitle();
            return title;
        } else {
            System.out.println("title is not present within the given timeout : " + timeOut);
            return null;
        }
    }

    public String waitForFullTitleAndCapture(String titleVal, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        if (wait.until(ExpectedConditions.titleIs(titleVal))) {
            String title = driver.getTitle();
            return title;
        } else {
            System.out.println("title is not present within the given timeout : " + timeOut);
            return null;
        }
    }

    public boolean waitForTotalWindows(int totalWindowsToBe, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        return wait.until(ExpectedConditions.numberOfWindowsToBe(totalWindowsToBe));
    }

    /**
     *  Wait for Frames
     */

    public void waitForFrameAndSwitchToItWithFluentWait(int timeOut, int pollingTime, String idOrName) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(timeOut))
                .ignoring(NoSuchFrameException.class)
                .pollingEvery(Duration.ofSeconds(pollingTime))
                .withMessage("------time out is done...frame is not found.....");
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(idOrName));
    }

    public void waitForFrameAndSwitchToItByIDOrName(int timeOut, String idOrName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(idOrName));
    }

    public void waitForFrameAndSwitchToItByIndex(int timeOut, int frameIndex) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
    }

    public void waitForFrameAndSwitchToItByFrameElement(int timeOut, WebElement frameElement) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
    }

    public void waitForFrameAndSwitchToItByFrameLocator(int timeOut, By frameLocator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
    }

    public WebElement waitForElementVisibleWithFluentWait(By locator, int timeOut, int pollingTime) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(timeOut))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(ElementNotInteractableException.class)
                .pollingEvery(Duration.ofSeconds(pollingTime))
                .withMessage("------time out is done...element is not found.....");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public WebElement waitForElementPresenceWithFluentWait(By locator, int timeOut, int pollingTime) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(timeOut))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(ElementNotInteractableException.class)
                .pollingEvery(Duration.ofSeconds(pollingTime))
                .withMessage("------time out is done...element is not found.....");
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    // Custom Wait

    public WebElement retryingElement(By locator, int timeOut) {

        WebElement element = null;
        int attempts = 0;

        while (attempts < timeOut) {
            try {
                element = getElement(locator);
                System.out.println("element is found...." + locator + " in attempt : " + attempts);
                break;
            } catch (NoSuchElementException e) {
                System.out.println("element is not found..." + locator + " in attempt : " + attempts);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            attempts++;
        }

        if (element == null) {
            System.out.println(
                    "element is not found...tried for " + timeOut + " secs " + " with the interval of 500 millisecons");
        }
        return element;

    }

    public WebElement retryingElement(By locator, int timeOut, int pollingTime) {

        WebElement element = null;
        int attempts = 0;

        while (attempts < timeOut) {
            try {
                element = getElement(locator);
                System.out.println("element is found...." + locator + " in attempt : " + attempts);
                break;
            } catch (NoSuchElementException e) {
                System.out.println("element is not found..." + locator + " in attempt : " + attempts);
                try {
                    Thread.sleep(pollingTime);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            attempts++;
        }

        if (element == null) {
            System.out.println(
                    "element is not found...tried for " + timeOut + " secs " + " with the interval of "+pollingTime+" millisecons");
        }
        return element;
    }

    // Page Loading
    public Boolean isPageLoaded(){
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        String flag = wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == \'complete\'")).toString();
        return Boolean.parseBoolean(flag);
    }
    public void waitForPageLoad(int timeOut){
        long endTime = System.currentTimeMillis()+ timeOut;
        while (System.currentTimeMillis() < endTime){
            JavascriptExecutor js = (JavascriptExecutor)driver;
            String pageState = js.executeScript("return document.readyState").toString();
            //document.readyState -> In JS
            if(pageState.equals("complete")){
                System.out.println("Page DOM is fully loaded now..");
                break;
            }else{
                System.out.println("Page is not loaded");
            }
        }
    }
}
