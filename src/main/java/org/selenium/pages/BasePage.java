package org.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.selenium.utils.CustomLoggerManager;
import org.selenium.utils.DriverManager;
import org.selenium.utils.PropertiesManager;

import java.time.Duration;

public abstract class BasePage {

    static final CustomLoggerManager log = new CustomLoggerManager();

    protected WebDriver driver = DriverManager.getInstance().getDriver();

    protected static final String BASE_ULR = PropertiesManager.getBaseUrl();


    /**
     * Navigate to given page
     *
     * @param pageURL {String} partial page url
     */
    protected void goToPage(String pageURL) {

        log.info(String.format("-> -> Go to page %s", BASE_ULR + pageURL));

        driver.get(BASE_ULR + pageURL);

    }

    /**
     * Return current page URL
     *
     * @return {String} page URL
     */
    protected String getPageURL() {

        String url = driver.getCurrentUrl();
        log.info(String.format("-> -> URL is: %s", url));

        return url;
    }

    /**
     * Find element based on locator
     *
     * @param locator {By} element locator
     * @return {WebElement} return located web element
     */
    protected WebElement getElement(By locator) {
        log.info("-> -> Finding element");
        return driver.findElement(locator);
    }

    /**
     * Click on web element
     *
     * @param locator {By} element locator
     */
    protected void click(By locator){

        getElement(locator).click();
        log.info("-> -> Clicking");
    }

    /**
     * Type on web element
     *
     * @param locator {By} element locator
     * @param text {String} text to type
     */
    protected void typeText(By locator, String text) {

        getElement(locator).sendKeys(text);
        log.info(String.format("-> -> Typing:  %s", text));
    }

    /**
     * Get element text
     *
     * @param locator {By} element locator
     * @return {String} return text value of located element
     */
    protected String getElementText(By locator) {

        String text = getElement(locator).getText();
        log.info(String.format("-> -> Given element has text: %s", text));

        return text;
    }

    /**
     * Check is element is visible on a page
     *
     * @param locator {By} element locator
     * @param timeout {int} max timeout wait for element to become visible
     * @return {boolean} return True if element is visible False if not
     */
    protected boolean isElementVisible(By locator, int timeout) {

        try {
            WebElement webElement = waitForElementToBeVisible(locator, timeout);

            if (webElement != null) {
                log.info("-> -> Element is visible on a page");
                return true;
            } else {
                log.info("-> -> Element is not visible on a page");
                return false;
            }
        } catch (TimeoutException e) {
            log.info("-> -> Element is not visible on a page");
            return false;
        }
    }

    /**
     * Check if element is visible on a page
     *
     * @param locator {By} element locator
     * @param timeout {int} max timeout wait for element to become visible
     * @return {boolean} return True if element is visible False if not
     */
    protected WebElement waitForElementToBeVisible(By locator, int timeout) {

        log.info(String.format("-> -> Waiting up to %s seconds for element to become visible", timeout));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

    }

}
