package org.selenium.utils;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ThreadGuard;
import org.selenium.data.Time;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DriverManager {

    private DriverManager() {}

    private static final String HEADLESS = "--headless=new";

    private static final DriverManager instance = new DriverManager();
    public static DriverManager getInstance() {
        return instance;
    }

    ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public void setDriver(String browser) throws MalformedURLException {
        driver.set(ThreadGuard.protect(setUpDriver(browser)));
    }

    public WebDriver getDriver() {
        return driver.get();
    }

    /**
     * Set up web driver for given browser.
     * Supported browsers are chrome, edge and firefox
     *
     * @param browser {String} browser to set up
     * @return {Path} return path to screenshot
     */
    private WebDriver setUpDriver(String browser) throws MalformedURLException {

        WebDriver webDriver = null;

        if (PropertiesManager.getEnvironment().equalsIgnoreCase("remote")) {

            webDriver =  setRemoteWebdriver(browser);

        } else if (PropertiesManager.getEnvironment().equalsIgnoreCase("local")) {

            switch (browser) {
                case "chrome" -> webDriver = new ChromeDriver(setChromeOptions());

                case "edge" -> webDriver = new EdgeDriver(setEdgeOptions());

                case "firefox" -> webDriver = new FirefoxDriver(setEFirefoxOptions());

                default -> throw new WebDriverException(String.format("Given browser %s, is not supported", browser));
            }
        }

        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Time.IMPLICIT_TIMEOUT));
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Time.PAGE_LOAD_TIMEOUT));
        webDriver.manage().window().maximize();
        webDriver.get(PropertiesManager.getBaseUrl());

        return webDriver;
    }

    private WebDriver setRemoteWebdriver(String browser) throws MalformedURLException {

        Capabilities capabilities;

        switch (browser) {
            case "chrome" -> capabilities = setChromeOptions();

            case "edge" -> capabilities = setEdgeOptions();

            case "firefox" -> capabilities = setEFirefoxOptions();

            default -> throw new WebDriverException(String.format("Given browser %s, is not supported", browser));
        }

        return new RemoteWebDriver(new URL(PropertiesManager.getSeleniumServer()), capabilities);
    }


    /**
     * Quit browser and remove it from thread local
     */
    public void closeBrowser() {

        if (getDriver() != null){
            getDriver().quit();
        }

        driver.remove();
    }

    /**
     * Set up Chrome browser options
     * HEADLESS - to run in headless mode set common property headless to true
     * @return {ChromeOptions}
     */
    private ChromeOptions setChromeOptions() {

        ChromeOptions chromeOptions = new ChromeOptions();
        if (PropertiesManager.getHeadless()) {
            chromeOptions.addArguments(HEADLESS);
        }
        return chromeOptions;
    }

    /**
     * Set up Edge browser options
     * HEADLESS - to run in headless mode set common property headless to true
     * @return {ChromeOptions}
     */
    private EdgeOptions setEdgeOptions() {

        EdgeOptions edgeOptions = new EdgeOptions();
        if (PropertiesManager.getHeadless()){
            edgeOptions.addArguments(HEADLESS);
        }
        return edgeOptions;
    }

    /**
     * Set up Firefox browser options
     * HEADLESS - to run in headless mode set common property headless to true
     * @return {ChromeOptions}
     */
    private FirefoxOptions setEFirefoxOptions() {

        FirefoxOptions firefoxOptions = new FirefoxOptions();
        if (PropertiesManager.getHeadless()){
            firefoxOptions.addArguments(HEADLESS);
        }
        return firefoxOptions;
    }
}
