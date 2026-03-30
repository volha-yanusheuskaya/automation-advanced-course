package com.epam.automation.core.logger;

public class LoggerFactory {

    public static ILogger getLogger(Class<?> clazz) {
        return new Log4j2Logger(clazz);
    }
}

