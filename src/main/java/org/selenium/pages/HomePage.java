package org.selenium.pages;


public class HomePage extends BasePage {
    public static final String HOME_PAGE_URL = "inventory.html";

    public void openHomePage() {

        log.info("Open Home page");

        goToPage(HOME_PAGE_URL);
    }

    public boolean isHomePageURLCorrect() {

        log.info(String.format("Check if Home page URL is correct. Expected value is %s", BASE_ULR + HOME_PAGE_URL));

        return getPageURL().contains(HOME_PAGE_URL);
    }

}

