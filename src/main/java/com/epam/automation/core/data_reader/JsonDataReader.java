package com.epam.automation.core.data_reader;

import com.epam.automation.business.models.Launch;
import com.epam.automation.core.logger.ILogger;
import com.epam.automation.core.logger.LoggerFactory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class JsonDataReader implements IDataReader<Launch> {
    private static final ILogger logger = LoggerFactory.getLogger(JsonDataReader.class);
    private static final Gson gson = new Gson();

    @Override
    public List<Launch> readDataByKey(String filePath, String arrayKey) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            JsonObject json = gson.fromJson(content, JsonObject.class);

            if (!json.has(arrayKey)) {
                throw new RuntimeException("Array key '" + arrayKey + "' not found in JSON");
            }

            Launch[] launches = gson.fromJson(json.get(arrayKey), Launch[].class);
            logger.info("Parsed {} launches from '{}' array", launches.length, arrayKey);
            return Arrays.asList(launches);
        } catch (IOException e) {
            logger.error("Failed to read JSON file: {}", e.getMessage());
            throw new RuntimeException("Failed to read JSON file: " + filePath, e);
        }
    }
}
