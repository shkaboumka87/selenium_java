package org.selenium.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Path;

public class ScreenShotManager {

    private ScreenShotManager() {}

    /**
     * Take a screenshot and return path
     *
     * @param driver {WebDriver} web driver instance
     * @return {Path} return path to screenshot
     */
    public static Path takeScreenShot(WebDriver driver) {

        // cast driver to screenshot object
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        // virtual screenshot object
        File virtualScreenShot = screenshot.getScreenshotAs(OutputType.FILE);

        return virtualScreenShot.toPath();
    }

}
