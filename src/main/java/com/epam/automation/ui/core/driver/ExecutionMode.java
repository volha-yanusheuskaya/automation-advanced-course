package com.epam.automation.ui.core.driver;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ExecutionMode {
    LOCAL("local"),
    GRID("grid"),
    SELENOID("selenoid"),
    BROWSERSTACK("browserstack");

    private final String mode;

    ExecutionMode(String mode) {
        this.mode = mode;
    }

    public static ExecutionMode fromString(String value) {
        if (value == null || value.isBlank()) {
            return LOCAL;
        }
        return Arrays.stream(values())
                .filter(m -> m.mode.equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown execution mode: " + value));
    }
}
