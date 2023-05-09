package test_listeners;

import org.selenium.utils.PropertiesManager;
import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class TestRetryListener implements IRetryAnalyzer, IAnnotationTransformer {

    int counter = 0;
   final static int MAX_RETRY = PropertiesManager.getMaxRetry();

    /**
     * Retry counter for failed tests. Max retry is defined in properties file as maxRetry property.
     */
    @Override
    public boolean retry(ITestResult iTestResult) {

        if (counter < MAX_RETRY) {

            counter++;
            return true;
        }
        return false;
    }

    /**
     * Defining test listener. Listener should be called from suite.xml file.
     */
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(TestRetryListener.class);
    }
}
