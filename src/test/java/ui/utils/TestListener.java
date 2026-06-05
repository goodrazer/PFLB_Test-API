package ui.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.time.Duration;

public class TestListener implements ITestListener {

    private static final Logger log = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult iTestResult) {
        log.info("==================================== STARTING TEST {} ======================================",
                iTestResult.getName());
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        log.info("============================= FINISHED TEST {} Duration: {} ================================",
                iTestResult.getName(), getExecutionTime(iTestResult));
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        log.info("============================ FAILED TEST {} Duration: {} ===================================",
                iTestResult.getName(), getExecutionTime(iTestResult));
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        log.info("=================================== SKIPPING TEST {} =======================================",
                iTestResult.getName());
    }

    private long getExecutionTime(ITestResult iTestResult) {
        return Duration.ofMillis(iTestResult.getEndMillis() - iTestResult.getStartMillis()).toSeconds();
    }
}