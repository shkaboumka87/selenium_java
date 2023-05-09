package org.selenium.pages;

import org.openqa.selenium.By;

public class BurgerMenu extends BasePage {


    private final By burgerMenuIcon = By.className("bm-burger-button");

    private final By logoutOption = By.id("logout_sidebar_link");

    public void openBurgerMenu() {

        log.info("Click on Burger menu");
        click(burgerMenuIcon);
    }

    public void clickLogout() {

        log.info("Click on Logout button");
        click(logoutOption);
    }
}
