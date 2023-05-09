package web;

import org.selenium.pages.BurgerMenu;
import org.selenium.pages.HomePage;
import org.selenium.pages.LoginPage;
import org.selenium.utils.DriverManager;
import org.selenium.utils.PropertiesManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.net.MalformedURLException;


public class BaseTestClass {

    LoginPage loginPage;
    HomePage homePage;
    BurgerMenu burgerMenu;

    String username;
    String password;

    /**
     * Function to be triggered before each test method (and after onTestStart function from Test listener)
     * Actions:
     * set driver instance for each test
     */
    @BeforeMethod
    public void setUp() throws MalformedURLException {


        DriverManager.getInstance().setDriver(PropertiesManager.getBrowser());
        username = PropertiesManager.getUsername();
        password = PropertiesManager.getPassword();


        loginPage = new LoginPage();
        homePage = new HomePage();
        burgerMenu = new BurgerMenu();
    }

    /**
     * Function to be triggered after each test method (and after equivalent functions from Test listener)
     * Actions:
     * close browser instance after each test
     * Method will always run.
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        DriverManager.getInstance().closeBrowser();
    }
}
