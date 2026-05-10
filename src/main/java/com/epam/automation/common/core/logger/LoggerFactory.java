package com.epam.automation.common.core.logger;

import java.io.File;

public class LoggerFactory {

    private LoggerFactory() {
    }

    static {
        File logsDir = new File("logs");
        if (!logsDir.exists()) {
            logsDir.mkdirs();
        }
    }

    public static ILogger getLogger(Class<?> clazz) {
        return new Log4j2Logger(clazz);
    }
}

