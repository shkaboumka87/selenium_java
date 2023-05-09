package org.selenium.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;


public class PropertiesManager {
    
    private PropertiesManager() {}

    private static final String PROPERTIES_PATH = "common.properties";

    private static final Properties properties = loadPropertiesFile();
    public static synchronized Properties loadPropertiesFile() {
        return loadPropertiesFile(PROPERTIES_PATH);
    }

    /**
     * Load properties file
     *
     * @param filePath {String}
     * @return {Properties} return loaded properties file
     */
    public static Properties loadPropertiesFile(String filePath) {

        Properties properties;

        try (InputStream inputStream = PropertiesManager.class.getClassLoader().getResourceAsStream(filePath)) {
            properties = new Properties();

                properties.load(inputStream);

        } catch (IOException error){
            throw new RuntimeException(error.getMessage());
        }
        return properties;
    }

    /**
     * Get property form properties file
     *
     * @param property {String}
     * @return {String}
     */
    private static String getProperty(String property) {

       return properties.getProperty(property);

    }

    /**
     * Return environment property.
     * environment value from properties file.
     *
     * @return {String}
     */
    public static String getEnvironment(){
        return getProperty("environment");
    }

    /**
     * Return base URL property.
     * baseUrl value from properties file.
     *
     * @return {String}
     */
    public static String getBaseUrl() {
        return getProperty("baseUrl");
    }

    /**
     * Return selenium server(grid) address.
     * seleniumServer value from properties file.
     *
     * @return {String}
     */
    public static String getSeleniumServer() {
        return getProperty("seleniumServer");
    }

    /**
     * Return browser property.
     * browser value from properties file.
     *
     * @return {String}
     */
    public static String getBrowser() {
        return getProperty("browser");
    }

    /**
     * Return maximum retry of failed test property. If property is not defined it will return 0.
     * maxRetry value from properties file.
     *
     * @return {int}
     */
    public static int getMaxRetry() {

        String maxRetry = getProperty("maxRetry");

        if (maxRetry != null) {
            return  Integer.parseInt(maxRetry);
        } else {
            return 0;
        }
    }

    /**
     * Return headless property, to run browser in headless mode.
     * headless value from properties file.
     *
     * @return {boolean}
     */
    public static boolean getHeadless() {
        return Boolean.getBoolean(getProperty("headless"));
    }

    /**
     * Return test user username property.
     * username value from properties file.
     *
     * @return {String}
     */
    public static String getUsername() {
        return getProperty("username");
    }

    /**
     * Return test user password property.
     * password value from properties file.
     *
     * @return {String}
     */
    public static String getPassword() {
        return getProperty("password");
    }

    /**
     * Return screenshot folder path. Default value is results/screenshots
     * screenshotsFolder value from properties file.
     *
     * @return {String}
     */
    public static String getScreenshotsFolder() {

        String path = getProperty("screenshotsFolder");
        return Objects.requireNonNullElseGet(path, () -> "results" + File.separator + "screenshots");
    }

    /**
     * Define if extent reporter should be created
     * createReporter value from properties file.
     *
     * @return {boolean}
     */
    public static boolean getCreateReporter() {

        return Boolean.getBoolean(getProperty("createReporter"));
    }

    /**
     * Get extent reporter location. Default value is results/extent_reports
     * extentReportsFolder value from properties file.
     *
     * @return {boolean}
     */
    public static String getExtentReportsFolder() {

        String path = getProperty("extentReportsFolder");
        return Objects.requireNonNullElseGet(path, () -> "results" + File.separator + "extent_reports");
    }


}
