package com.epam.automation.common.core.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4j2Logger implements ILogger {

    private final Logger logger;

    public Log4j2Logger(Class<?> clazz) {
        this.logger = LogManager.getLogger(clazz);
    }

    @Override
    public void debug(String message, Object... params) {
        logger.debug(message, params);
    }

    @Override
    public void info(String message, Object... params) {
        logger.info(message, params);
    }

    @Override
    public void warn(String message, Object... params) {
        logger.warn(message, params);
    }

    @Override
    public void error(String message, Object... params) {
        logger.error(message, params);
    }

    @Override
    public void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    @Override
    public void fatal(String message, Object... params) {
        logger.fatal(message, params);
    }

    @Override
    public void logStep(String stepDescription) {
        logger.info("STEP: {}", stepDescription);
    }

    @Override
    public void logTestStart(String testName) {
        logger.info("Test started: {}", testName);
    }

    @Override
    public void logTestEnd(String testName, String status) {
        logger.info("Test {} : {}", status::toUpperCase, () -> testName);
    }

    @Override
    public String getLoggerName() {
        return logger.getName();
    }
}
