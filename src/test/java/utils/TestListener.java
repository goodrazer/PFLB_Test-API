package utils;

import lombok.extern.log4j.Log4j2;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.time.Duration;

@Log4j2
public class TestListener implements ITestListener {

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
