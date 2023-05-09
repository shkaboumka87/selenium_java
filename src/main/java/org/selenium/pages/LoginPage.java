package org.selenium.pages;

import org.openqa.selenium.By;
import org.selenium.data.Time;

public class LoginPage extends BasePage {


    private final By usernameField = By.id("user-name");

    private final By passwordField = By.id("password");

    private final By loginButton = By.id("login-button");

    private final By loginErrorMessage= By.xpath("//div[@class='error-message-container error']");

    public static final String WRONG_USERNAME_AND_PASSWORD_ERROR_MESSAGE_TEXT =
            "Epic sadface: Username and password do not match any user in this service";


    public void enterUsername(String username) {

        log.info("Enter username");

        typeText(usernameField, username);
    }

    public void enterPassword(String password) {

        log.info("Enter password");

        typeText(passwordField, password);
    }

    public void clickLoginButton() {

        log.info("Click login button");

        click(loginButton);
    }

    public void loginUser(String username, String password) {

        log.info("Login user");

        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public boolean isLoginErrorMessageVisible() {

        log.info("Check if Login Error Message is visible");

        return isElementVisible(loginErrorMessage, Time.TIME_SHORT);
    }

    public boolean isLoginButtonVisible() {

        log.info("Check if Login button is visible");

        return isElementVisible(loginButton, Time.TIME_SHORT);
    }

    public String getLoginErrorMessageText() {

        log.info("Fetching login error text message");

        return getElementText(loginErrorMessage);
    }

}
