package com.epam.automation.api.business.request;

import com.epam.automation.common.core.config.ConfigurationReader;

public class ProjectPath {

    private ProjectPath() {

    }

    public static String demoProjectPath(String path) {
        return String.format("/%s/%s", ConfigurationReader.getDemoProjectName(), path);
    }
}
