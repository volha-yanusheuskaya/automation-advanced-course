package com.epam.automation.tests.unit.common.core.config;

import com.epam.automation.common.core.config.ConfigurationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("ConfigurationException Unit Tests")
class ConfigurationExceptionTest {

    @Test
    @DisplayName("Should create exception with message only")
    void constructorWithMessageCreatesException() {
        String message = "Configuration file not found";

        ConfigurationException exception = new ConfigurationException(message);

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("Should store message correctly")
    void getMessageReturnsConstructorMessage() {
        String message = "Failed to load config.properties";

        ConfigurationException exception = new ConfigurationException(message);

        assertThat(exception.getMessage()).isEqualTo("Failed to load config.properties");
    }

    @Test
    @DisplayName("Should create exception with message and cause")
    void constructorWithMessageAndCauseCreatesException() {
        String message = "Configuration error occurred";
        IOException cause = new IOException("File read error");

        ConfigurationException exception = new ConfigurationException(message, cause);

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo(message);
        assertThat(exception.getCause()).isEqualTo(cause);
    }

    @Test
    @DisplayName("Should preserve cause exception")
    void getCauseReturnsProvidedCause() {
        IOException cause = new IOException("File not found");

        ConfigurationException exception = new ConfigurationException("Config error", cause);

        assertThat(exception.getCause()).isInstanceOf(IOException.class);
        assertThat(exception.getCause().getMessage()).isEqualTo("File not found");
    }

    @Test
    @DisplayName("Should extend RuntimeException")
    void configurationExceptionExtendsRuntimeException() {
        ConfigurationException exception = new ConfigurationException("Test");

        assertThat(exception).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Should be throwable and catchable")
    void exceptionCanBeThrownAndCaught() {
        assertThatThrownBy(() -> {
            throw new ConfigurationException("Test exception");
        })
        .isInstanceOf(ConfigurationException.class)
        .hasMessage("Test exception");
    }

    @Test
    @DisplayName("Should be catchable as RuntimeException")
    void exceptionCatchableAsRuntimeException() {
        assertThatThrownBy(() -> {
            throw new ConfigurationException("Config error");
        })
        .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Should handle null message")
    void constructorWithNullMessageHandlesGracefully() {
        ConfigurationException exception = new ConfigurationException(null);

        assertThat(exception.getMessage()).isNull();
    }

    @Test
    @DisplayName("Should handle empty message")
    void constructorWithEmptyMessageHandlesGracefully() {
        ConfigurationException exception = new ConfigurationException("");

        assertThat(exception.getMessage()).isEmpty();
    }

    @Test
    @DisplayName("Should preserve cause with null message")
    void constructorWithNullMessageAndCausePreservesCause() {
        IOException cause = new IOException("Root cause");

        ConfigurationException exception = new ConfigurationException(null, cause);

        assertThat(exception.getMessage()).isNull();
        assertThat(exception.getCause()).isEqualTo(cause);
    }

    @Test
    @DisplayName("Should preserve null cause when explicitly passed")
    void constructorWithMessageAndNullCauseHandlesGracefully() {
        ConfigurationException exception = new ConfigurationException("Message", null);

        assertThat(exception.getMessage()).isEqualTo("Message");
        assertThat(exception.getCause()).isNull();
    }

    @Test
    @DisplayName("Should maintain message through exception chaining")
    void messagePreservedThroughExceptionChaining() {
        IOException ioException = new IOException("IO Error");

        ConfigurationException configException = new ConfigurationException(
                "Failed to load config.properties",
                ioException
        );

        assertThat(configException.getMessage()).isEqualTo("Failed to load config.properties");
        assertThat(configException.getCause()).isEqualTo(ioException);
    }

    @Test
    @DisplayName("Should support exception with special characters in message")
    void constructorWithSpecialCharactersInMessage() {
        String message = "Error: [config.properties] not found at path '/home/config'";

        ConfigurationException exception = new ConfigurationException(message);

        assertThat(exception.getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("Should create exception with multiple levels of causes")
    void exceptionChainWithMultipleCauses() {
        RuntimeException rootCause = new RuntimeException("Root error");
        IOException intermediateException = new IOException("File operation failed", rootCause);

        ConfigurationException exception = new ConfigurationException(
                "Configuration loading failed",
                intermediateException
        );

        assertThat(exception.getMessage()).isEqualTo("Configuration loading failed");
        assertThat(exception.getCause()).isInstanceOf(IOException.class);
        assertThat(exception.getCause().getCause()).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Should support unchecked exception usage without try-catch")
    void uncheckedExceptionDoesNotRequireTryCatch() {
        assertThatThrownBy(this::throwUnchecked)
                .isInstanceOf(ConfigurationException.class);
    }

    private void throwUnchecked() {
        throw new ConfigurationException("Test unchecked exception");
    }
}
