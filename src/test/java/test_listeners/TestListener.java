package test_listeners;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.openqa.selenium.WebDriver;
import org.selenium.utils.*;
import org.testng.*;

import java.nio.file.Path;
import java.util.Map;

public class TestListener implements ITestListener, ISuiteListener {

    static final CustomLoggerManager log = new CustomLoggerManager();


    /**
     * Function to be triggered before suite execution starts (once per run).
     */
    @Override
    public void onStart(ISuite suite) {

       PropertiesManager.loadPropertiesFile();

        //get params from suite xml file
        Map<String, String> suiteParameters = suite.getXmlSuite().getAllParameters();


        ExtentReportsManager.initializeExtentReporter(suite.getName(), suiteParameters);
    }

    /**
     * Function to be triggered after suite execution completes (once per run).
     */
    @Override
    public void onFinish(ISuite suite) {
        ExtentReportsManager.createExtentReport();
    }

    /**
     * Function to be triggered before each test.
     */
    @Override
    public void onTestStart(ITestResult result) {

        ExtentReportsManager.createTest(getTestName(result), getTestGroups(result));
        ExtentReportsManager.getExtentTest().info(MarkupHelper.createLabel(
                String.format("Test Description : %s", getTestDescription(result)), ExtentColor.INDIGO));

        log.info("***** TEST STARTED *****");
    }

    /**
     * Function to be triggered after each successful test run.
     */
    @Override
    public void onTestSuccess(ITestResult result) {

        if ((result.wasRetried())) {
            ExtentReportsManager.getExtentTest().pass(
                    MarkupHelper.createLabel("***** SUCCESSFUL TEST RE-RUN *****", ExtentColor.GREEN));
        }

        ExtentReportsManager.getExtentTest().pass(
                MarkupHelper.createLabel("***** TEST PASSED *****", ExtentColor.GREEN));
        ExtentReportsManager.unload();
    }

    /**
     * Function to be triggered after each unsuccessful test run.
     */
    @Override
    public void onTestFailure(ITestResult result) {

        WebDriver driver = DriverManager.getInstance().getDriver();

        if (driver != null) {

            Path screenshotPath = ScreenShotManager.takeScreenShot(driver);

            ExtentReportsManager.attachScreenShotToTest(screenshotPath.toString());
        }

        ExtentReportsManager.getExtentTest().fail(
                MarkupHelper.createLabel("***** TEST FAILED *****", ExtentColor.RED));
        ExtentReportsManager.getExtentTest().fail(result.getThrowable().getMessage());
        ExtentReportsManager.getExtentTest().fail(result.getThrowable());

        ExtentReportsManager.unload();
    }

    /**
     * Function to be triggered after each skipped test run.
     */
    @Override
    public void onTestSkipped(ITestResult result) {

        if ((result.wasRetried())) {
            ExtentReportsManager.getExtentTest().skip(
                    MarkupHelper.createLabel(
                            "***** UNSUCCESSFULLY TEST RE-RUN. SEE RUN MARKED AS FAILED FOR MORE INFORMATION *****"
                            , ExtentColor.ORANGE));
        }

        ExtentReportsManager.getExtentTest().skip(
                MarkupHelper.createLabel("***** TEST SKIPPED *****", ExtentColor.ORANGE));


            ExtentReportsManager.unload();
    }

    /**
     * Return test name including the test parameters (if any)
     * @param result {ITestResult}
     * @return {String}
     */
    private String getTestName(ITestResult result) {

        StringBuilder testName = new StringBuilder(result.getMethod().getMethodName());
        testName.append("(");

        Object[] params = result.getParameters();

        for (int i = 0; i < params.length; i++) {
            Object param = i == 0 ? params[i] : ", " + params[i];
            testName.append(param);
        }
        testName.append(")");

        return testName.toString();
    }

    /**
     * Return test description defined as part of the test annotation.
     * @param result {ITestResult}
     * @return {String}
     */
    private String getTestDescription(ITestResult result) {

        return result.getMethod().getDescription();
    }

    /**
     * Return list of groups test belongs to. Groups are defined as part of the test annotation.
     * @param result {ITestResult}
     * @return {String[]}
     */
    private String[] getTestGroups(ITestResult result) {
        return result.getMethod().getGroups();
    }

}
