package org.selenium.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

public class ExtentReportsManager {

    static Logger log = LogManager.getLogger(ExtentReportsManager.class.getName());

    private ExtentReportsManager() {

    }
    private static ExtentReports reporter;
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    public static ExtentTest getExtentTest() {return extentTest.get();}
    public static void setExtentTest(ExtentTest test) {extentTest.set(test);}

    /**
     * Initialize extent reporter and populate environment table
     *
     * @param filename {String} extent reporter filename
     * @param params {Map<String, String>} map of parameters
     */
    public static void initializeExtentReporter(String filename, Map<String, String> params) {

        reporter = new ExtentReports();
        reporter.attachReporter(createSparkReporter(filename));
        reporter.setSystemInfo("OS", System.getProperty("os.name"));

        String environment = PropertiesManager.getEnvironment();

        params.put("Environment", environment);

        if (environment.equalsIgnoreCase("remote")) {
            params.put("Selenium server address", PropertiesManager.getSeleniumServer());
        }

        params.put("Base URL", PropertiesManager.getBaseUrl());
        params.put("Browser", PropertiesManager.getBrowser());

        params.forEach((k, v) -> reporter.setSystemInfo(k, v));

    }

    /**
     * Configure extent reporter
     *
     * @param reporterName {String} reporter name
     */
    private static ExtentSparkReporter createSparkReporter(String reporterName) {

        String path = System.getProperty("user.dir") + File.separator +
                PropertiesManager.getExtentReportsFolder() + File.separator + reporterName();

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(path).viewConfigurer().viewOrder().as(
                new ViewName[]{ViewName.DASHBOARD, ViewName.CATEGORY, ViewName.TEST}).apply();

        sparkReporter.config().setReportName(reporterName);
        sparkReporter.config().setDocumentTitle("Automation test results");
        // press l to switch between the themes
        sparkReporter.config().setTheme(Theme.DARK);

        return sparkReporter;
    }

    /**
     * Create extent reporter
     */
    public static void createExtentReport() {reporter.flush();}

    /**
     * Return reporter name (.html)
     */
    private static String reporterName() {

        return String.format("test_reporter_%s.html", CommonMethods.getTime());
    }

    /**
     * Create single test in reporter
     *
     * @param testName {String}
     * @param testGroups {String[]}
     */
    public static synchronized void createTest(String testName, String[] testGroups) {

        ExtentTest test = reporter.createTest(testName);

        for (String group : testGroups) {
            test.assignCategory(group);
        }

        setExtentTest(test);
    }


    /**
     * Attach base64 img to reporter
     *
     * @param screenshot {String} screenshot path
     */
    public static void attachScreenShotToTest(String screenshot) {
        try{
            byte[] fileContent = FileUtils.readFileToByteArray(new File(screenshot));
            String encodedString = Base64.getEncoder().encodeToString(fileContent);
            ExtentReportsManager.getExtentTest().warning(MarkupHelper.createLabel(
                            "Screenshot taken. Click below, on base64 img, to enlarge.", ExtentColor.INDIGO));
            ExtentReportsManager.getExtentTest().warning(
                    MediaEntityBuilder.createScreenCaptureFromBase64String(encodedString).build());

        }catch (IOException error) {
            log.error(error.getMessage());
            ExtentReportsManager.getExtentTest().warning("Failed to attach screenshot to extent report.");
        }
    }

    /**
     * Destroy single test reference
     */
    public static void unload() {extentTest.remove();}
}


