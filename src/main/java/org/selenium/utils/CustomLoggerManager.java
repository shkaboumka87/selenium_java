package org.selenium.utils;

import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomLoggerManager {
    Logger log = LogManager.getLogger();

    boolean createReporter = PropertiesManager.getCreateReporter();

    /**
     * Wrapper around INFO logs of both log4j and extent reporter.
     * Extent reporter will log if createReporter property is set to True.
     *
     * @param message {String} log message
     */
    public void info(String message){

        log.info(message);

        if (!createReporter) {
            ExtentReportsManager.getExtentTest().log(Status.INFO, message);
        }
    }


    /**
     * Wrapper around ERROR logs of both log4j and extent reporter.
     * Extent reporter will log if createReporter property is set to True.
     *
     * @param message {String} log message
     */
    public void error(String message){

        log.info(message);

        if (!createReporter) {
            ExtentReportsManager.getExtentTest().fail(message);
        }
    }

}
