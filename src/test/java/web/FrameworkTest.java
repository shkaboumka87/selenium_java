package web;


import org.selenium.annotations.Jira;
import org.selenium.pages.LoginPage;
import org.testng.annotations.Test;
import test_data.DataProviders;
import test_data.TestGroups;

import static org.testng.Assert.*;

public class FrameworkTest extends BaseTestClass {

    @Jira(jiraID = "1234", owner = "ABCD EFG")
    @Test (description = "This is the 1st test",
            groups = {TestGroups.UI, TestGroups.REGRESSION})
    public void firstTest() {

        loginPage.loginUser(username, password);

        assertTrue(homePage.isHomePageURLCorrect(), "Home page URL is not correct");

        burgerMenu.openBurgerMenu();

        fail("FAILED ON PURPOSE");

        burgerMenu.clickLogout();

        assertTrue(loginPage.isLoginButtonVisible(),
                "Login button is not visible. Check if user is properly logged out.");
    }

    @Test(description = "This is the 2nd test",
            groups = {TestGroups.UI, TestGroups.REGRESSION},
            dataProviderClass = DataProviders.class, dataProvider = "invalidCredentials")
    public void secondTest(String username, String password) {

        loginPage.loginUser(username, password);

        assertTrue(loginPage.isLoginErrorMessageVisible(), "Error Message is not visible");

        assertEquals(loginPage.getLoginErrorMessageText(), LoginPage.WRONG_USERNAME_AND_PASSWORD_ERROR_MESSAGE_TEXT,
                "Error message text is not correct.");

    }
}
