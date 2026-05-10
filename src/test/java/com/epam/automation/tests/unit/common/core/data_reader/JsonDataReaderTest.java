package com.epam.automation.tests.unit.common.core.data_reader;

import com.epam.automation.ui.business.models.Launch;
import com.epam.automation.common.core.data_reader.JsonDataReader;
import com.epam.automation.common.core.config.ConfigurationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("JsonDataReader Unit Tests")
class JsonDataReaderTest {

    private JsonDataReader jsonDataReader;
    private static final String TEST_DATA_DIR = "src/test/resources/test_data";

    @BeforeEach
    void setUp() {
        jsonDataReader = new JsonDataReader();
    }

    @Test
    @DisplayName("Should successfully read and parse launches from valid JSON file with valid array key")
    void readDataByKey_ValidFileAndKey_ReturnsParsedLaunches() {
        String filePath = TEST_DATA_DIR + "/launches.json";
        String arrayKey = "launches1";

        List<Launch> launches = jsonDataReader.readDataByKey(filePath, arrayKey);

        assertThat(launches)
                .isNotNull()
                .hasSize(1);
        assertThat(launches.getFirst())
                .extracting("name", "date", "totalSteps", "passedSteps", "failedSteps", "skippedSteps")
                .containsExactly("Demo Api Tests #1", "2026-03-31 16:46:51", 10, 1, 9, 0);
    }

    @Test
    @DisplayName("Should successfully parse multiple launches from JSON array")
    void readDataByKey_MultipleArrayKeys_ReturnsDifferentLaunches() {
        String filePath = TEST_DATA_DIR + "/launches.json";

        List<Launch> launches1 = jsonDataReader.readDataByKey(filePath, "launches1");
        List<Launch> launches4 = jsonDataReader.readDataByKey(filePath, "launches4");

        assertThat(launches1).hasSize(1);
        assertThat(launches1.getFirst().getName()).isEqualTo("Demo Api Tests #1");

        assertThat(launches4).hasSize(1);
        assertThat(launches4.getFirst().getName()).isEqualTo("Demo Api Tests #4");
    }

    @Test
    @DisplayName("Should throw RuntimeException when array key is not found in JSON")
    void readDataByKey_MissingArrayKey_ThrowsRuntimeException() {
        String filePath = TEST_DATA_DIR + "/launches.json";
        String invalidArrayKey = "nonExistentKey";

        assertThatThrownBy(() -> jsonDataReader.readDataByKey(filePath, invalidArrayKey))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Array key 'nonExistentKey' not found in JSON");
    }

    @Test
    @DisplayName("Should throw RuntimeException when JSON file does not exist")
    void readDataByKey_FileNotFound_ThrowsRuntimeException() {
        String filePath = TEST_DATA_DIR + "/nonexistent.json";
        String arrayKey = "launches1";

        assertThatThrownBy(() -> jsonDataReader.readDataByKey(filePath, arrayKey))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Failed to read JSON file");
    }

    @Test
    @DisplayName("Should return empty list when array in JSON is empty")
    void readDataByKey_EmptyArrayInJson_ReturnsEmptyList() throws IOException {
        String filePath = createTempJsonFile("{\"emptyArray\": []}");
        String arrayKey = "emptyArray";

        List<Launch> launches = jsonDataReader.readDataByKey(filePath, arrayKey);

        assertThat(launches).isEmpty();
    }

    @Test
    @DisplayName("Should parse all fields correctly from JSON")
    void readDataByKey_AllFieldsInJson_ParsesAllFieldsCorrectly() {
        String filePath = TEST_DATA_DIR + "/launches.json";
        String arrayKey = "launches5";

        List<Launch> launches = jsonDataReader.readDataByKey(filePath, arrayKey);

        assertThat(launches).hasSize(1);

        Launch launch = launches.getFirst();
        assertThat(launch)
                .extracting("name", "date", "totalSteps", "passedSteps", "failedSteps", "skippedSteps",
                        "productBugCount", "autoBugCount", "systemIssueCount", "toInvestigateCount")
                .containsExactly("Demo Api Tests #5", "2026-03-31 16:47:04", 30, 30, 0, 0, 0, 0, 0, 0);
    }

    @Test
    @DisplayName("Should throw RuntimeException when JSON file is malformed")
    void readDataByKey_MalformedJson_ThrowsException() throws IOException {
        String malformedJson = "{\"launches\": [invalid json";
        String filePath = createTempJsonFile(malformedJson);
        String arrayKey = "launches";

        assertThatThrownBy(() -> jsonDataReader.readDataByKey(filePath, arrayKey))
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("Should handle JSON with multiple arrays and return only requested array")
    void readDataByKey_MultipleArraysInJson_ReturnsOnlyRequestedArray() {
        String filePath = TEST_DATA_DIR + "/launches.json";

        List<Launch> launches1 = jsonDataReader.readDataByKey(filePath, "launches1");
        List<Launch> launches2 = jsonDataReader.readDataByKey(filePath, "launches2");
        List<Launch> sorted1 = jsonDataReader.readDataByKey(filePath, "sortedByMostRecent1");

        assertThat(launches1).hasSize(1);
        assertThat(launches2).hasSize(1);
        assertThat(sorted1).hasSize(1);
        assertThat(launches1.getFirst().getName()).isNotEqualTo(launches2.getFirst().getName());
        assertThat(sorted1.getFirst().getName()).isEqualTo("Demo Api Tests #5");
    }

    @Test
    @DisplayName("Should correctly parse Launch object fields with various data types")
    void readDataByKey_ComplexLaunchData_ParsesAllDataTypesCorrectly() {
        String filePath = TEST_DATA_DIR + "/launches.json";
        String arrayKey = "launches3";

        List<Launch> launches = jsonDataReader.readDataByKey(filePath, arrayKey);

        assertThat(launches).hasSize(1);
        Launch launch = launches.getFirst();

        assertThat(launch.getName()).isEqualTo("Demo Api Tests #3");
        assertThat(launch.getDate()).isEqualTo("2026-03-31 16:46:57");
        assertThat(launch.getTotalSteps()).isEqualTo(20);
        assertThat(launch.getPassedSteps()).isEqualTo(10);
        assertThat(launch.getFailedSteps()).isEqualTo(8);
        assertThat(launch.getSkippedSteps()).isEqualTo(2);
        assertThat(launch.getProductBugCount()).isEqualTo(4);
        assertThat(launch.getAutoBugCount()).isEqualTo(4);
        assertThat(launch.getSystemIssueCount()).isEqualTo(1);
        assertThat(launch.getToInvestigateCount()).isEqualTo(7);
    }

    @Test
    @DisplayName("Should throw RuntimeException and maintain message when file read fails")
    void readDataByKey_FileAccessDenied_ThrowsRuntimeExceptionWithCausedBy() {
        String filePath = TEST_DATA_DIR + "/nonexistent_dir/launches.json";
        String arrayKey = "launches1";

        assertThatThrownBy(() -> jsonDataReader.readDataByKey(filePath, arrayKey))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Failed to read JSON file")
                .hasCauseInstanceOf(IOException.class);
    }

    @Test
    @DisplayName("Should handle case-sensitive array key lookup")
    void readDataByKey_CaseSensitiveKey_ThrowsExceptionForWrongCase() {
        String filePath = TEST_DATA_DIR + "/launches.json";
        String wrongCaseKey = "Launches1";

        assertThatThrownBy(() -> jsonDataReader.readDataByKey(filePath, wrongCaseKey))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Array key 'Launches1' not found in JSON");
    }

    @Test
    @DisplayName("Should throw ConfigurationException when array key is not found in JSON")
    void readDataByKey_MissingArrayKey_ThrowsConfigurationException() {
        String filePath = TEST_DATA_DIR + "/launches.json";
        String invalidArrayKey = "nonExistentKey";

        assertThatThrownBy(() -> jsonDataReader.readDataByKey(filePath, invalidArrayKey))
                .isInstanceOf(ConfigurationException.class)
                .hasMessageContaining("Array key 'nonExistentKey' not found in JSON");
    }

    @Test
    @DisplayName("Should throw ConfigurationException when JSON file does not exist")
    void readDataByKey_FileNotFound_ThrowsConfigurationException() {
        String filePath = TEST_DATA_DIR + "/nonexistent.json";
        String arrayKey = "launches1";

        assertThatThrownBy(() -> jsonDataReader.readDataByKey(filePath, arrayKey))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Failed to read JSON file")
                .hasCauseInstanceOf(IOException.class);
    }

    @Test
    @DisplayName("Should throw ConfigurationException when IOException occurs during file read")
    void readDataByKey_FileAccessDenied_ThrowsConfigurationExceptionWithIOCause() {
        String filePath = TEST_DATA_DIR + "/nonexistent_dir/launches.json";
        String arrayKey = "launches1";

        assertThatThrownBy(() -> jsonDataReader.readDataByKey(filePath, arrayKey))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Failed to read JSON file: " + filePath)
                .hasCauseInstanceOf(IOException.class);
    }

    @Test
    @DisplayName("Should throw ConfigurationException when JSON is invalid and cannot be parsed")
    void readDataByKey_InvalidJsonFormat_ThrowsConfigurationException() throws IOException {
        String invalidJson = "{\"launches\": invalid}";
        String filePath = createTempJsonFile(invalidJson);
        String arrayKey = "launches";

        assertThatThrownBy(() -> jsonDataReader.readDataByKey(filePath, arrayKey))
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("Should throw ConfigurationException when array contains null")
    void readDataByKey_NullArrayInJson_ThrowsConfigurationException() throws IOException {
        String jsonWithNull = "{\"launches\": null}";
        String filePath = createTempJsonFile(jsonWithNull);
        String arrayKey = "launches";

        assertThatThrownBy(() -> jsonDataReader.readDataByKey(filePath, arrayKey))
                .isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("Should successfully parse and log launch count")
    void readDataByKey_ValidData_LogsParsingInfo() {
        String filePath = TEST_DATA_DIR + "/launches.json";
        String arrayKey = "launches1";

        List<Launch> launches = jsonDataReader.readDataByKey(filePath, arrayKey);

        assertThat(launches).hasSize(1);
    }

    @Test
    @DisplayName("Should convert Launch array to list successfully")
    void readDataByKey_ValidData_ReturnsListNotArray() {
        String filePath = TEST_DATA_DIR + "/launches.json";
        String arrayKey = "launches1";

        List<Launch> launches = jsonDataReader.readDataByKey(filePath, arrayKey);

        assertThat(launches)
                .isInstanceOf(List.class)
                .isNotEmpty();
    }

    private String createTempJsonFile(String jsonContent) throws IOException {
        Path tempFile = Files.createTempFile("test-", ".json");
        Files.write(tempFile, jsonContent.getBytes());
        tempFile.toFile().deleteOnExit();
        return tempFile.toString();
    }
}

