package com.epam.automation.api.business.model.managers;

import com.epam.automation.api.business.model.domain.AnalyzeLaunch;

import java.util.List;

public class AnalyzeLaunchManager {

    public static AnalyzeLaunch getAnalyzeLaunchById(int launchId) {
        return AnalyzeLaunch.builder()
                .id(launchId)
                .analyzerTypeName("autoAnalyzer")
                .analyzerMode("ALL")
                .analyzeItemsMode(List.of("TO_INVESTIGATE"))
                .build();
    }
}
