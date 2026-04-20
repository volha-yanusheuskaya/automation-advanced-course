package com.epam.automation.common.core.logger;

public interface ILogger {

    void debug(String message, Object... params);

    void info(String message, Object... params);

    void warn(String message, Object... params);

    void error(String message, Object... params);

    void error(String message, Throwable throwable);

    void fatal(String message, Object... params);

    void logStep(String stepDescription);

    void logTestStart(String testName);

    void logTestEnd(String testName, String status);

    String getLoggerName();
}
