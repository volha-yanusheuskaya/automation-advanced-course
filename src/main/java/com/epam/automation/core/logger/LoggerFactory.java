package com.epam.automation.core.logger;

import java.io.File;

public class LoggerFactory {

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

