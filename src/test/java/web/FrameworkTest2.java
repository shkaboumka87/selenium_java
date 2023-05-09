package web;

import org.selenium.pages.LoginPage;
import org.testng.annotations.Test;
import test_data.TestGroups;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class FrameworkTest2 extends BaseTestClass {


    @Test (description = "This is the 3rd test",
            groups = {TestGroups.UI, TestGroups.REGRESSION})
    public void thirdTest() {


        loginPage.loginUser("standard_user", "secret_sauce");

        assertTrue(homePage.isHomePageURLCorrect(), "Home page URL is not correct");

        burgerMenu.openBurgerMenu();

        burgerMenu.clickLogout();

        assertTrue(loginPage.isLoginButtonVisible(),
                "Login button is not visible. Check if user is properly logged out.");
    }

    @Test (description = "This is the 4rd test",
    groups = {TestGroups.UI, TestGroups.REGRESSION})
    public void fourthTest() {

        loginPage.loginUser("standard_userrrr", "secret_sauce");

        assertTrue(loginPage.isLoginErrorMessageVisible(), "Error Message is not visible");

        assertEquals(loginPage.getLoginErrorMessageText(), LoginPage.WRONG_USERNAME_AND_PASSWORD_ERROR_MESSAGE_TEXT,
                "Error message text is not correct.");
    }
}
